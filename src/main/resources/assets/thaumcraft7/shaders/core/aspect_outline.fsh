#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;

in vec2 texCoord0;
in vec4 vertexColor;

out vec4 fragColor;

const float offset = 1.0 / 512.0;

void main() {
    vec4 outlineColor = vec4(vertexColor.rgb * 0.25, 1.0);
    vec4 color = texture(Sampler0, texCoord0) * vertexColor;

    float adjacentAlpha = texture(Sampler0, vec2(texCoord0.x + offset, texCoord0.y)).a +
            texture(Sampler0, vec2(texCoord0.x, texCoord0.y - offset)).a +
            texture(Sampler0, vec2(texCoord0.x - offset, texCoord0.y)).a +
            texture(Sampler0, vec2(texCoord0.x, texCoord0.y + offset)).a;

    if (color.a < 0.1 && adjacentAlpha > 0.0)
        fragColor = outlineColor * ColorModulator;
    else
        fragColor = color * ColorModulator;
}
