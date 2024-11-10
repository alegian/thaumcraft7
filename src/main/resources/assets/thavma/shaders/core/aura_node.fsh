#version 150

in vec4 vertexColor;
in vec3 fragPosition;
flat in vec3 fragCenter;
flat in float radius;
in float angle;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;

    float dist = distance(fragPosition, fragCenter);

    if(dist>0.3) discard;
    fragColor = vertexColor;
}
