#version 330 core

in vec3 Position;
in vec3 Center;


uniform mat4 ModelViewMat;
uniform mat4 ProjMat;

out vec3 center;


void main() {
   gl_Position = ProjMat * ModelViewMat * vec4(Position, 1.0);
   center = Center;
}
