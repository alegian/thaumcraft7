#version 150

in vec3 Position;
in vec4 Color;
in vec3 Center;

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 ScreenSize;

out vec4 vertexColor;
out vec2 centerFragCoord;
out float angle;

void main() {
    gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);

    vertexColor = Color;
    angle = atan(Position.x, Position.z);

    vec4 centerGLPos = ProjMat * ModelViewMat * vec4(Center, 1.0);
    vec2 ndcPos = centerGLPos.xy / centerGLPos.w;
    centerFragCoord = ScreenSize * (ndcPos*0.5 + 0.5);
}
