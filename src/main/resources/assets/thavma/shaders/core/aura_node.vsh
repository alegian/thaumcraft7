#version 150

in vec3 Position;
in vec4 Color;
in vec3 Center;
in float Angle;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 ScreenSize;

out vec4 vertexColor;
out float distanceFromCenter;
flat out float radius;
out float angle;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;

    angle = Angle;

    distanceFromCenter = distance(Center, Position);
    radius = distanceFromCenter;
}
