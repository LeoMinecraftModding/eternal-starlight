#version 150

vec4 light_line(vec2 st, float i, float m) {
    vec2 _st = vec2(i, m) / st;
    float a = abs(cos(i));
    float b = -abs(cos(m));
    return vec4(st, a, b);
}