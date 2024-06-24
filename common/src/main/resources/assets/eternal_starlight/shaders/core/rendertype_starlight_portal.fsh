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
    float anim0 = sin(GameTime * 1000) + 1.;
    float anim1 = sin(-GameTime * 1000 / 2.) + 2.;
    float anim2 = sin(GameTime * 1000 + 2.);
    float anim3 = sin(-GameTime * 1000 + 2.);

    float shape = 0.;

    vec2 pos = (vec2(0.5) - st) * 1.5;

    float r = length(pos) * 3.;
    float a = atan(pos.y, pos.x);

    float layer0 = cos(a * anim0 * 2.);
    float layer1 = cos(a * anim1 * 3.);
    float layer2 = smoothstep(-1.5, 1., cos(a * anim2 * 10.)) * 0.2 + 0.5;
    float layer3 = smoothstep(-1.5, 1., cos(a * anim3 * 10.)) * 0.2 + 0.5;

    shape += 1. -smoothstep(layer0, layer0 + 0.5, r);
    shape += 1. -smoothstep(layer1, layer1 + 0.5, r);
    shape += 1. -smoothstep(layer2, layer2 + 0.5, r);
    shape += 1. -smoothstep(layer3, layer3 + 0.5, r);
    shape += smoothstep(0.65, 0., length(pos));

    vec3 color = vec3(0.1, 0.1, 0.1);
    vec3 curveColor = vec3(0.1, 0.1, 0.325);

    color += curveColor * (smoothstep(1., 0., length(pos)) - smoothstep(0.65, 0., length(pos)));
    color += curveColor * (smoothstep(anim0, 0., length(pos)) - smoothstep(0.65, 0., length(pos)));

    fragColor = vec4(color, shape) * ColorModulator * linear_fog_fade(vertexDistance, FogStart, FogEnd);
}
