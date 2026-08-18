#version 150

uniform sampler2D DiffuseSampler;

in vec2 texCoord;

out vec4 fragColor;

void main() {
    vec3 sceneColor = texture(DiffuseSampler, texCoord).rgb;

    // the final screen framebuffer must be treated as opaque, so transparent
    // sections of the sky do not disappear during the post-processing copy
    fragColor = vec4(sceneColor, 1.0);
}
