#version 150

in vec4 vertexColor;
in float radius;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;
    if (color.a == 0.0) {
        discard;
    }
    fragColor = vec4(vec3(radius), 1);
}
