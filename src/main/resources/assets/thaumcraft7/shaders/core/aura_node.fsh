#version 150

in vec4 vertexColor;
in vec2 centerFragCoord;
in float angle;

uniform vec4 ColorModulator;

out vec4 fragColor;

void main() {
    vec4 color = vertexColor;

    float radius = distance(centerFragCoord, gl_FragCoord.xy);

    if(radius>64) discard;
    fragColor = vec4(1, 1, 1, 0.5);
}
