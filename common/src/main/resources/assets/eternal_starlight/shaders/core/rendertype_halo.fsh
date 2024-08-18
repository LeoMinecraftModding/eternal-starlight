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
    vec2 st = texCoord0;

    vec2 pos = vec2(0.5) - st;
     float r = length(pos) * 2.4;
    float a = atan(pos.y, pos.x);

    vec3 color1 = vec3(0.1569, 1.0, 0.9569);
    vec3 color2 = vec3(1.0, 0.6549, 0.102);
    vec3 color3 = vec3(0.702, 0.5529, 1.0);


    float layer1 = abs(sin(a * 4.0 * cos(932.0)));
    layer1 *= fract(layer1);
    float layer2 = abs(cos(-a * 6.0 * sin(31.4)));
    float layer3 = sin(a * 7.0 * 0.5 + (sin(GameTime * 628.0)));

    float shape = 0.24 + abs(0.64 + sin(GameTime * 1570.0)) - length(pos) * 1.2;
    shape += smoothstep(layer1, layer1 + 0.5, length(pos) * 1.5);
    shape *= 0.7 - length(pos) * 1.45;

    vec4 out_layer = vec4(layer1 * color1 * layer2  * color1 * layer3 * color3, shape);   
    
    vec4 color = vec4(color2, distance(st, vec2(0.5)));
    color = vec4(out_layer + color * ColorModulator * linear_fog_fade(vertexDistance, FogStart, FogEnd));
    color *= smoothstep(color - vec4(0.003), color + vec4(0.003), vec4(dot(color, color)));

    float centre = 1.0 - smoothstep(r - r * 0.005, r + r * 0.005, dot(pos, pos) * 18.9);

    fragColor = vec4(color.rgb, shape * shape * (shape * 1.8 - centre) * 3.0);
}
