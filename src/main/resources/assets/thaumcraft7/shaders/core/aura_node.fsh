#version 150

in vec4 vertexColor;
in float distanceFromCenter;
in float angle;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;

    if(distanceFromCenter>0.5) discard;
    fragColor = vec4(1, 1, 1, 0.5);
}
