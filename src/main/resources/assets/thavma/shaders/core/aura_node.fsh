#version 150

in vec4 vertexColor;
in float distanceFromCenter;
flat in float radius;
in float angle;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;

    if(distanceFromCenter>radius+(sin(angle*16)-1)*radius/8) discard;
    fragColor = vec4(1, 1, 1, 0.5);
}
