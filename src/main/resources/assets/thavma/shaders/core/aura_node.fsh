#version 150

in vec4 vertexColor;
in vec3 fragPosition;
flat in vec3 fragCenter;
flat in float radius;

uniform mat4 ModelViewMat;

out vec4 fragColor;

void main() {
    vec3 diff = fragCenter - fragPosition;
    vec4 cameraDiff = ModelViewMat * vec4(diff, 1.0);
    float angle = atan(cameraDiff.y, cameraDiff.x);

    if (length(diff) > radius * (1 - 0.1 * (sin(angle * 8) + 1))) discard;
    fragColor = vertexColor;
}
