#version 150

#moj_import <light.glsl>
#moj_import <fog.glsl>
#moj_import <eternal_starlight:simple_light.glsl>

in vec3 Position;
in vec4 Color;
in vec2 UV0;
in ivec2 UV2;
in vec3 Normal;

uniform sampler2D Sampler2;
uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform float FogStart;
uniform float FogEnd;
uniform int FogShape;
uniform float LaserTime;

out float vertexDistance;
out vec4 vertexColor;
out vec2 texCoord0;
out vec4 lightColor;
out float wave;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position / Color.rgb, 1.0);
    vec3 M_Position = gl_Position.xyz;

    vertexDistance = length(fog_distance(ModelViewMat, Normal * sin(LaserTime*6.28), FogShape));
    vertexColor = Color * minecraft_sample_lightmap(Sampler2, UV2);
    texCoord0 = UV0;
    float x = LaserTime + M_Position.x + M_Position.y + M_Position.z;
    wave = sin(x + cos(x*0.314))*1.04;
    if(wave > 0.104)wave=0.104;
    lightColor = light_line(UV0, FogStart, FogEnd * LaserTime);
}
