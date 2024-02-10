#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform mat4 ProjMat;
uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;
uniform float LaserTime;

in vec4 lightColor;
in vec4 vertexColor;
in vec2 texCoord0;
in float vertexDistance;
in float wave;

out vec4 fragColor;

void main() {
    vec4 defaultColor = texture(Sampler0, texCoord0) * vertexColor;
    float time = LaserTime * 1600 * wave;
    float anim = sin(time) + 1;
    vec4 color = vec4(0.2, anim * 0.3 + 0.5, 0.0, defaultColor.a);
    fragColor = color * ColorModulator;
//    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, vec4(lightColor.r * 0.2, lightColor.g * 0.6, lightColor.b * 1.3, lightColor.a * 0.75));
    fragColor.r = fragColor.r * 0.35;
    fragColor.b = fragColor.b + abs(cos(LaserTime * 3.14)) * 2.0;
    fragColor.a = fragColor.a * (LaserTime + 0.2) / 1.5 ;
    //    fragColor.g = fragColor.g + wave*(fract(abs(cos(LaserTime*10229138994.5)*sin(LaserTime*102892832737.0))));
}
