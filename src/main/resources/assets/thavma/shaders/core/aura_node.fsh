#version 150

in vec4 vertexColor;
in vec3 fragPosition;
flat in vec3 fragCenter;
flat in float radius;

uniform mat4 ModelViewMat;
uniform float GameTime;

out vec4 fragColor;

vec2 random2(vec2 st) {
    st = vec2(dot(st, vec2(127.1, 311.7)),
    dot(st, vec2(269.5, 183.3)));
    return -1.0 + 2.0 * fract(sin(st) * 43758.5453123);
}

// Gradient Noise by Inigo Quilez - iq/2013
// https://www.shadertoy.com/view/XdXGW8
float noise(vec2 st) {
    vec2 i = floor(st);
    vec2 f = fract(st);

    vec2 u = f * f * (3.0 - 2.0 * f);

    return mix(mix(dot(random2(i + vec2(0.0, 0.0)), f - vec2(0.0, 0.0)),
                   dot(random2(i + vec2(1.0, 0.0)), f - vec2(1.0, 0.0)), u.x),
               mix(dot(random2(i + vec2(0.0, 1.0)), f - vec2(0.0, 1.0)),
                   dot(random2(i + vec2(1.0, 1.0)), f - vec2(1.0, 1.0)), u.x), u.y);
}

void main() {
    vec3 diff = fragCenter - fragPosition;
    vec4 cameraDiff = ModelViewMat * vec4(diff, 1.0);
    float angle = atan(cameraDiff.y, cameraDiff.x);
    // TODO scale time (its mod 24k)
    float phase = noise(vec2(radius, GameTime)) * 2 * 3.14159;
    // wavy patterns only for nodes with a decent radius
    if (length(diff) > radius * (1 - 0.2 * (sin(angle * 16 + phase) + 1)) && radius > 0.2) discard;
    // smaller ones are circular
    if (length(diff) > radius) discard;
    fragColor = vertexColor;
}
