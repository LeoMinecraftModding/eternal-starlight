#version 150

in vec2 texCoord0;

uniform vec4 ColorModulator;
uniform float RandomFloat;
uniform float GameTime;

out vec4 fragColor;

void main() {
    fragColor = vec4(1.0) * 100.0;
}