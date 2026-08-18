#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D DepthSampler;

uniform vec2 OutSize;

uniform mat4 uViewProj;
uniform mat4 uViewProjInv;
uniform vec3 uCameraPosition;

uniform float uEffectPositions[192];
uniform float uEffectRadii[64];
uniform float uEffectIntensities[64];
uniform float uEffectAges[64];
uniform float uEffectDurations[64];
uniform int uEffectCount;

uniform float uShockwaveParams[256];

in vec2 texCoord;

out vec4 fragColor;

const int MAX_EFFECTS = 64;
const float PI = 3.14159265358979323846;
const float EPSILON = 0.0001;

vec3 getEffectPosition(int index) {
    return vec3(uEffectPositions[index * 3], uEffectPositions[index * 3 + 1], uEffectPositions[index * 3 + 2]);
}

vec4 getShockwaveParams(int index) {
    return vec4(uShockwaveParams[index * 4], uShockwaveParams[index * 4 + 1], uShockwaveParams[index * 4 + 2], uShockwaveParams[index * 4 + 3]);
}

// reconstructs the world position of the rendered surface at the given uv from the depth buffer
vec3 reconstructWorld(vec2 uv, float depth) {
    vec4 clipPos = vec4(uv * 2.0 - 1.0, depth * 2.0 - 1.0, 1.0);
    vec4 viewPos = uViewProjInv * clipPos;
    viewPos /= viewPos.w;
    return viewPos.xyz + uCameraPosition;
}

// projects a world position back to screen uv, used to convert world-space offsets into uv offsets
vec2 projectToScreen(vec3 worldPos) {
    vec4 clipPos = uViewProj * vec4(worldPos - uCameraPosition, 1.0);
    return clipPos.xy / clipPos.w * 0.5 + 0.5;
}

// expanding spherical shockwave shell: refractive ring + subtle tint
void addShockwave(int index, vec3 worldPos, inout vec3 totalOffset, inout float totalDarken, inout vec3 totalTint) {
    vec3 center = getEffectPosition(index);
    float radius = max(uEffectRadii[index], 0.1);
    float intensity = uEffectIntensities[index];
    float age = uEffectAges[index];
    float duration = max(uEffectDurations[index], 1.0);
    vec4 params = getShockwaveParams(index);
    float frequency = max(params.x, 0.1);
    float falloff = max(params.y, 0.1);
    float thickness = max(params.z, 0.1);

    vec3 delta = worldPos - center;
    float dist = length(delta);
    if (dist > radius + 4.0) {
        return;
    }

    float progress = clamp(age / duration, 0.0, 1.0);
    float fadeIn = smoothstep(0.0, 0.15, progress);
    float fadeOut = 1.0 - smoothstep(0.55, 1.0, progress);
    float strength = intensity * fadeIn * fadeOut * smoothstep(0.5, 2.0, distance(worldPos, uCameraPosition));
    if (strength <= EPSILON) {
        return;
    }

    float waveRadius = progress * radius;
    float shellDist = dist - waveRadius;
    float wave = sin(shellDist * frequency * 2.0 * PI) * exp(-abs(shellDist) * falloff);
    float mask = exp(-shellDist * shellDist * thickness);

    if (dist > EPSILON) {
        totalOffset += (delta / dist) * wave * mask * strength * 0.25;
    }
    totalDarken += mask * strength * 0.1;
    totalTint += vec3(0.05, 0.15, 0.2) * mask * strength * 0.2;
}

void main() {
    float depth = texture(DepthSampler, texCoord).r;
    vec3 worldPos = reconstructWorld(texCoord, depth);

    vec3 totalOffset = vec3(0.0);
    float totalDarken = 0.0;
    vec3 totalTint = vec3(0.0);

    for (int i = 0; i < MAX_EFFECTS; i++) {
        if (i >= uEffectCount) {
            break;
        }
        addShockwave(i, worldPos, totalOffset, totalDarken, totalTint);
    }

    vec2 shiftedUv = texCoord;
    if (length(totalOffset) > EPSILON) {
        shiftedUv = projectToScreen(worldPos + totalOffset);
    }

    // clamp the displacement so the effect never tears the image apart
    vec2 pixelSize = 1.0 / max(OutSize, vec2(1.0));
    vec2 textureMargin = pixelSize * 2.0;
    vec2 maxDisplacement = pixelSize * 12.0;
    vec2 uvOffset = clamp(shiftedUv - texCoord, -maxDisplacement, maxDisplacement);
    shiftedUv = clamp(texCoord + uvOffset, textureMargin, vec2(1.0) - textureMargin);

    vec3 color = texture(DiffuseSampler, shiftedUv).rgb;
    color *= 1.0 - clamp(totalDarken, 0.0, 0.9);
    color += totalTint * clamp(totalDarken, 0.0, 1.0);

    fragColor = vec4(clamp(color, vec3(0.0), vec3(1.0)), 1.0);
}
