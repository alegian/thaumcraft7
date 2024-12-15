#version 150

in vec3 Position;
in vec4 Color;
in vec3 Center;
in float Scale;
in float NextScale;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec4 vertexColor;
out vec3 fragPosition;
flat out vec3 fragCenter;
flat out float radius;
flat out float scale;
flat out float nextScale;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;
    fragPosition = Position;
    fragCenter = Center;
    scale = Scale;
    nextScale = NextScale;
    radius = distance(Center, Position) / 2;
}
