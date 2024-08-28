#version 150

#moj_import <fog.glsl>

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    vec2 uv = texCoord0.xy;
    vec2 offset1 = vec2(cos(GameTime * 1000 / 2.0) * 0.4, sin(GameTime * 1000 / 3.0) * 0.2);
    vec2 offset2 = vec2(cos(GameTime * 1000 / 2.0 + 10.) * 0.2, sin(GameTime * 1000 / 1.5 + 10.) * 0.2);
    vec3 lightColor = vec3(0.9, 0.65, 0.5);
    float light = smoothstep(0.1, 0.0, abs(length(uv - vec2(0.5)) - 0.4));
    light *= smoothstep(0.5, 0.0, abs(length(uv + offset1 - vec2(0.5)) - 0.5));
    light *= smoothstep(0.5, 0.0, abs(length(uv + offset2 - vec2(0.5)) - 0.4));
    fragColor = vec4((light + 0.5) * lightColor, light) * ColorModulator * linear_fog_fade(vertexDistance, FogStart, FogEnd);
}
