#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float GameTime;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    float animation = GameTime * 6000.0;
    float sinAnim = sin(animation);
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator;
    if (color.a < 0.1) {
        discard;
    }
    vec2 texCoord = texCoord0 * 16.0;
    color.a *= smoothstep(0.8 + sinAnim * 0.2, 0.0, abs(texCoord.y - 0.5));
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
