#version 330 core

in vec3 center;

out vec4 out_color;

#define MARKER 500


void main() {
    out_color = vec4(0.0);

    vec3 p1 = center;
    vec3 p2 = vec3(0, 0, 0); // player pos
    vec3 p3 = gl_FragCoord.xyz;

    vec3 p12 = p2 - p1;
    vec3 p13 = p3 - p1;

    float d = dot(p12, p13) / length(p12);
    vec3  p4 = p1 + normalize(p12) * d;
    if (length(p4 - p3) < 50) {
        out_color = vec4(1.0, 0.0, 1.0, 1.0);
    }

}