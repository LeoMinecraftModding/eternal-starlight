#version 150

uniform sampler2D DiffuseSampler;

uniform vec2 ScreenSize;
uniform float uTime;
uniform float uFade;

uniform int uStarCount;
uniform int uHoverIndex;
uniform float uStarPositions[128];
uniform float uStarRadii[64];
uniform float uStarColors[192];
uniform float uStarAlphas[64];
uniform float uStarPhases[64];
uniform float uStarRotations[64];
uniform float uStarRayCounts[64];
uniform float uStarRayLengths[64];

in vec2 texCoord;

out vec4 fragColor;

vec4 _permute(vec4 x) { return mod(((x * 34.0) + 1.0) * x, 289.0); }
vec4 _taylorInvSqrt(vec4 r) { return 1.79284291400159 - 0.85373472095314 * r; }

float gln_simplex(vec3 v) {
    const vec2 C = vec2(1.0 / 6.0, 1.0 / 3.0);
    const vec4 D = vec4(0.0, 0.5, 1.0, 2.0);

    vec3 i = floor(v + dot(v, C.yyy));
    vec3 x0 = v - i + dot(i, C.xxx);

    vec3 g = step(x0.yzx, x0.xyz);
    vec3 l = 1.0 - g;
    vec3 i1 = min(g.xyz, l.zxy);
    vec3 i2 = max(g.xyz, l.zxy);

    vec3 x1 = x0 - i1 + 1.0 * C.xxx;
    vec3 x2 = x0 - i2 + 2.0 * C.xxx;
    vec3 x3 = x0 - 1.0 + 3.0 * C.xxx;

    i = mod(i, 289.0);
    vec4 p = _permute(_permute(_permute(i.z + vec4(0.0, i1.z, i2.z, 1.0)) + i.y + vec4(0.0, i1.y, i2.y, 1.0)) + i.x + vec4(0.0, i1.x, i2.x, 1.0));

    float n_ = 1.0 / 7.0;
    vec3 ns = n_ * D.wyz - D.xzx;

    vec4 j = p - 49.0 * floor(p * ns.z * ns.z);

    vec4 x_ = floor(j * ns.z);
    vec4 y_ = floor(j - 7.0 * x_);

    vec4 x = x_ * ns.x + ns.yyyy;
    vec4 y = y_ * ns.x + ns.yyyy;
    vec4 h = 1.0 - abs(x) - abs(y);

    vec4 b0 = vec4(x.xy, y.xy);
    vec4 b1 = vec4(x.zw, y.zw);

    vec4 s0 = floor(b0) * 2.0 + 1.0;
    vec4 s1 = floor(b1) * 2.0 + 1.0;
    vec4 sh = -step(h, vec4(0.0));

    vec4 a0 = b0.xzyw + s0.xzyw * sh.xxyy;
    vec4 a1 = b1.xzyw + s1.xzyw * sh.zzww;

    vec3 p0 = vec3(a0.xy, h.x);
    vec3 p1 = vec3(a0.zw, h.y);
    vec3 p2 = vec3(a1.xy, h.z);
    vec3 p3 = vec3(a1.zw, h.w);

    vec4 norm = _taylorInvSqrt(vec4(dot(p0, p0), dot(p1, p1), dot(p2, p2), dot(p3, p3)));
    p0 *= norm.x;
    p1 *= norm.y;
    p2 *= norm.z;
    p3 *= norm.w;

    vec4 m = max(0.6 - vec4(dot(x0, x0), dot(x1, x1), dot(x2, x2), dot(x3, x3)), 0.0);
    m = m * m;
    return 42.0 * dot(m * m, vec4(dot(p0, x0), dot(p1, x1), dot(p2, x2), dot(p3, x3)));
}

void addStar(int index, float t, inout vec3 totalColor) {
    vec2 pos = vec2(uStarPositions[index * 2], uStarPositions[index * 2 + 1]);
    float radius = max(uStarRadii[index], 1.0);
    vec3 baseColor = vec3(uStarColors[index * 3], uStarColors[index * 3 + 1], uStarColors[index * 3 + 2]);
    float starAlpha = uStarAlphas[index];
    float phase = uStarPhases[index];
    float rotation = uStarRotations[index];

    vec2 delta = gl_FragCoord.xy - pos;
    float dist = length(delta) / radius;
    if (dist > 4.5) {
        return;
    }

    float angle = atan(delta.y, delta.x) + rotation;

    // gentle flicker, unique per star
    float flicker = 0.72 + 0.28 * sin(t * (1.2 + phase) + phase * 6.2831);
    // subtle breathing scale
    float pulse = 1.0 + 0.08 * sin(t * (0.7 + phase) + phase * 3.0);

    // thin, sharp rays whose count and length vary subtly per star
    float rayCount = max(uStarRayCounts[index], 2.0);
    float rays = pow(abs(cos(rayCount * 0.5 * angle)), 9.0) * exp(-dist * uStarRayLengths[index]);
    // bright compact core
    float core = exp(-dist * dist * 16.0);
    // wide soft halo
    float halo = exp(-dist * dist * 1.2) * 0.30;

    // fade the glow smoothly to nothing before the hard cutoff so no circle edge is visible
    float cutoff = smoothstep(4.5, 3.2, dist);

    float intensity = (core * 1.4 + rays * 1.15 + halo) * flicker * pulse * starAlpha * cutoff;
    totalColor += baseColor * intensity;

    if (uHoverIndex >= 0 && index == uHoverIndex) {
        float ringDist = abs(dist - 1.3);
        float ring = smoothstep(0.22, 0.0, ringDist);
        ring *= 0.7 + 0.3 * sin(t * 8.0);
        totalColor += vec3(1.0) * ring * 0.9 * starAlpha * cutoff;
        // brighten the halo while hovering
        totalColor += baseColor * halo * 0.45 * starAlpha * cutoff;
    }
}

void main() {
    vec3 scene = texture(DiffuseSampler, texCoord).rgb;
    float t = uTime;

    vec2 uv = gl_FragCoord.xy / ScreenSize;
    vec2 centered = uv - 0.5;
    // correct the aspect ratio so the noise clouds stay round on any screen
    vec2 aspectCorrected = centered * vec2(ScreenSize.x / ScreenSize.y, 1.0);

    // slow rotation of the whole field
    float angle = t * 0.03;
    float ca = cos(angle);
    float sa = sin(angle);
    vec2 rotated = vec2(aspectCorrected.x * ca - aspectCorrected.y * sa, aspectCorrected.x * sa + aspectCorrected.y * ca);

    // domain warping: bend the clouds into curvy filaments
    float warpA = gln_simplex(vec3(rotated * 1.1, t * 0.04));
    float warpB = gln_simplex(vec3(rotated * 1.1 + 7.7, t * 0.04 + 3.1));
    vec2 warped = rotated + vec2(warpA, warpB) * 0.22;

    // anisotropic stretch: elongated wispy streaks along the rotation
    vec2 stretched = vec2(warped.x, warped.y * 0.6);

    // layered density with fine mottled detail so the clouds are not smooth
    float n = gln_simplex(vec3(stretched * 1.3, t * 0.05));
    n += 0.5 * gln_simplex(vec3(stretched * 2.6 + 5.2, t * 0.07 + 4.0));
    n += 0.25 * gln_simplex(vec3(stretched * 5.2 - 3.1, t * 0.09 + 13.7));
    n = n * 0.5 + 0.5;
    float detail = gln_simplex(vec3(warped * 3.8, t * 0.12 + 8.0));
    detail = detail * 0.5 + 0.5;
    n = mix(n, detail, 0.25);

    vec3 deep = vec3(0.015, 0.02, 0.07);
    vec3 blue = vec3(0.10, 0.17, 0.36);
    vec3 purple = vec3(0.26, 0.13, 0.38);
    vec3 teal = vec3(0.06, 0.17, 0.20);
    vec3 color = mix(deep, blue, smoothstep(0.25, 0.65, n));
    color = mix(color, purple, smoothstep(0.5, 0.9, n) * 0.7);
    // faint teal band through the mid densities, like a dusted emission zone
    color = mix(color, teal, smoothstep(0.4, 0.62, n) * (1.0 - smoothstep(0.62, 0.82, n)) * 0.35);

    // gentle desaturation so the clouds stay moody rather than vivid
    float gray = dot(color, vec3(0.299, 0.587, 0.114));
    color = mix(vec3(gray), color, 0.78);

    // dark dust lanes through the densest regions
    float dust = pow(clamp(n * 1.15 - 0.15, 0.0, 1.0), 2.5);
    color *= 1.0 - dust * 0.55;

    // mostly opaque in the center, fading only near the edges of the screen
    float dist = length(centered) * 2.0;
    float alpha = pow(1.0 - smoothstep(0.5, 1.25, dist), 1.6) * 0.92 * uFade;

    vec3 result = mix(scene, color, alpha);

    vec3 stars = vec3(0.0);
    for (int i = 0; i < 64; i++) {
        if (i >= uStarCount) {
            break;
        }
        addStar(i, t, stars);
    }
    result += stars * uFade;

    fragColor = vec4(result, 1.0);
}
