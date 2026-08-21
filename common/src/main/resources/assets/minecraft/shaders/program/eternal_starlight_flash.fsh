#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform vec2 OutSize;

uniform mat4 uViewProjInv;
uniform vec3 uCameraPosition;

uniform float uEffectPositions[192];
uniform float uEffectRadii[64];
uniform float uEffectIntensities[64];
uniform float uEffectAges[64];
uniform float uEffectDurations[64];
uniform int uEffectCount;

uniform float uFlashParams[320];

in vec2 texCoord;

out vec4 fragColor;

const int MAX_EFFECTS = 64;
const float EPSILON = 0.0001;

vec3 getEffectPosition(int index) {
    return vec3(uEffectPositions[index * 3], uEffectPositions[index * 3 + 1], uEffectPositions[index * 3 + 2]);
}

float getFlashFadeIn(int index) {
    return uFlashParams[index * 5];
}

float getFlashFadeOut(int index) {
    return uFlashParams[index * 5 + 1];
}

vec3 getFlashColor(int index) {
    return vec3(uFlashParams[index * 5 + 2], uFlashParams[index * 5 + 3], uFlashParams[index * 5 + 4]);
}

// reconstructs the world position of the rendered surface at the given uv from the depth buffer
vec3 reconstructWorld(vec2 uv, float depth) {
    vec4 clipPos = vec4(uv * 2.0 - 1.0, depth * 2.0 - 1.0, 1.0);
    vec4 viewPos = uViewProjInv * clipPos;
    viewPos /= viewPos.w;
    return viewPos.xyz + uCameraPosition;
}

// radial additive glare with a temporal fade in/out envelope, anchored at the effect position
void addFlash(int index, vec3 worldPos, inout vec3 totalFlash) {
    vec3 center = getEffectPosition(index);
    float radius = max(uEffectRadii[index], 0.1);
    float intensity = uEffectIntensities[index];
    float age = uEffectAges[index];
    float duration = max(uEffectDurations[index], 1.0);
    float fadeIn = max(getFlashFadeIn(index), 0.0);
    float fadeOut = max(getFlashFadeOut(index), 0.0);
    vec3 flashColor = getFlashColor(index);

    vec3 delta = worldPos - center;
    float dist = length(delta);
    if (dist > radius) {
        return;
    }

    float envelope = 1.0;
    if (age < fadeIn) {
        envelope = age / max(fadeIn, 0.001);
    }
    if (age > duration - fadeOut) {
        envelope = min(envelope, (duration - age) / max(fadeOut, 0.001));
    }
    envelope = clamp(envelope, 0.0, 1.0);

    float spatial = smoothstep(radius, 0.0, dist);
    float strength = intensity * envelope * spatial;
    if (strength <= EPSILON) {
        return;
    }

    totalFlash += flashColor * strength;
}

void main() {
    float depth = texture(DepthSampler, texCoord).r;
    vec3 worldPos = reconstructWorld(texCoord, depth);

    vec3 totalFlash = vec3(0.0);

    for (int i = 0; i < MAX_EFFECTS; i++) {
        if (i >= uEffectCount) {
            break;
        }
        addFlash(i, worldPos, totalFlash);
    }

    vec3 color = texture(DiffuseSampler, texCoord).rgb;

    if (length(totalFlash) > EPSILON) {
        // soft 5-tap cross blur sampled from the same frame, brightened like a glare
        vec2 pixelSize = 1.0 / max(OutSize, vec2(1.0));
        float soft = 0.25 * (1.0 - length(totalFlash) / 3.0);
        soft = clamp(soft, 0.0, 0.4);
        vec3 blur = texture(DiffuseSampler, texCoord + vec2(pixelSize.x, 0.0)).rgb;
        blur += texture(DiffuseSampler, texCoord - vec2(pixelSize.x, 0.0)).rgb;
        blur += texture(DiffuseSampler, texCoord + vec2(0.0, pixelSize.y)).rgb;
        blur += texture(DiffuseSampler, texCoord - vec2(0.0, pixelSize.y)).rgb;
        blur *= 0.25;
        color += blur * length(totalFlash) * soft;
        color += totalFlash;
    }

    fragColor = vec4(clamp(color, vec3(0.0), vec3(1.0)), 1.0);
}
