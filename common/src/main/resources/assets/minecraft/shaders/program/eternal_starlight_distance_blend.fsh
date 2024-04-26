#version 150

uniform sampler2D DiffuseSampler;
uniform sampler2D BlendSampler;

uniform mat4 ProjMat;
uniform vec2 ScreenSize;

in vec2 texCoord;
in vec2 scaledCoord;

out vec4 fragColor;

void main() {
    vec4 diffuse = texture(DiffuseSampler, texCoord);
    vec4 blend = texture(BlendSampler, texCoord);
    float distance = length(vec3(1., (2. * texCoord - 1.) * vec2(ScreenSize.x / ScreenSize.y, 1.) * tan(radians(70 / 2.))));
    vec4 result = diffuse - blend + blend * distance * distance;
    fragColor = vec4(result.rgb, 1.0);
}