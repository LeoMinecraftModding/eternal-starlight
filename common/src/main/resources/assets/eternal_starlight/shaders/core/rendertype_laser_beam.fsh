#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform float GameTime;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;

out vec4 fragColor;

void main() {
    float animation = GameTime * 6000.0;
    float sinAnim = sin(animation);
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;
    vec2 texCoord = texCoord0 * 16.0;
    color.a *= smoothstep(0.7 + sinAnim * 0.3, 0.0, abs(texCoord.x - 0.5));
    fragColor = color * ColorModulator * linear_fog_fade(vertexDistance, FogStart, FogEnd) * 100.0;
}
