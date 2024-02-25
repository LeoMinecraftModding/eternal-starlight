#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;
uniform vec4 ColorModulator;
uniform float GameTime;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

void main() {
    float i = 5.4 + abs(sin(GameTime*6000));
    vec4 color = texture(Sampler0, texCoord0) * vertexColor * ColorModulator * i / 2;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
