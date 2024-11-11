#version 150

in vec3 Position;
in vec4 Color;
in vec3 Center;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;
out vec3 fragPosition;
flat out vec3 fragCenter;
flat out float radius;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;
    fragPosition = Position;
    fragCenter = Center;
    radius = distance(Center, Position) / 2;
}
