#version 150

uniform mat4 ModelViewMat;
uniform mat4 ProjMat;
uniform vec2 ScreenSize;

in vec3 Position;
in vec2 UV0;
in vec4 Color;

out vec2 texCoord0;
out vec4 vertexColor;

const float VIEW_SHRINK = 1.0 - (1.0 / 256.0);
const mat4 VIEW_SCALE = mat4(
VIEW_SHRINK, 0.0, 0.0, 0.0,
0.0, VIEW_SHRINK, 0.0, 0.0,
0.0, 0.0, VIEW_SHRINK, 0.0,
0.0, 0.0, 0.0, 1.0
);

void main() {
    vec4 linePosStart = ProjMat * VIEW_SCALE * ModelViewMat * vec4(Position, 1.0);
    vec4 linePosEnd = ProjMat * VIEW_SCALE * ModelViewMat * vec4(Position, 1.0);

    vec3 ndc1 = linePosStart.xyz / linePosStart.w;
    vec3 ndc2 = linePosEnd.xyz / linePosEnd.w;

    vec2 lineScreenDirection = normalize((ndc2.xy - ndc1.xy) * ScreenSize);
    vec2 lineOffset = vec2(-lineScreenDirection.y, lineScreenDirection.x) / ScreenSize;

    if (lineOffset.x < 0.0) {
        lineOffset *= -1.0;
    }

    gl_Position = vec4((ndc1 - vec3(lineOffset, 0.0)) * linePosStart.w, linePosStart.w);
    texCoord0 = UV0;
    vertexColor = Color;
}
