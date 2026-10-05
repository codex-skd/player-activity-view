#version 150

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform vec2 resolution;
uniform float radius;
uniform float blurLevel;
// x1, y1, x2, y2 of the drawn screen panel inside this target, in pixels. Lets the soft mask hug
// the panel instead of a circle inscribed in the square target, which used to slice the top of
// wide, short panels (the pause menu) into an arch.
uniform vec4 contentRect;

in vec2 texCoord0;

out vec4 fragColor;

// Signed distance to a rounded box centred on the origin.
float sdRoundBox(vec2 p, vec2 b, float r) {
    r = min(r, min(b.x, b.y));
    vec2 q = abs(p) - b + r;
    return min(max(q.x, q.y), 0.0) + length(max(q, vec2(0.0))) - r;
}

void main() {

    vec2 tex_offset = 1.0 / textureSize(Sampler0, 0); // size of a single texel
    vec4 result = texture(Sampler0, texCoord0);
    if (blurLevel != 0) {
        result.rgb = vec3(0);
        float weights[5];
        if (blurLevel == 1) {
            weights = float[](0.45, 0.1, 0.1, 0.05, 0.02);
        } else {
            weights = float[](0.227027, 0.1945946, 0.1216216, 0.054054, 0.016216);
        }
        int blurRange = 4;
        for (int i = -blurRange; i <= blurRange; ++i) {
            result.rgb += texture(Sampler0, texCoord0 + vec2(0.0, tex_offset.y * float(i))).rgb * weights[abs(i)];
        }
    }

    vec2 pixelCoord = gl_FragCoord.xy;
    float feather = 6.0;

    vec2 lo = contentRect.xy;
    vec2 hi = contentRect.zw;
    if (hi.x - lo.x < 1.0 || hi.y - lo.y < 1.0) {
        lo = vec2(0.0);
        hi = resolution;
    }

    vec2 centre = (lo + hi) * 0.5;
    vec2 halfSize = max((hi - lo) * 0.5, vec2(1.0));
    vec2 inner = halfSize;

    if (radius != -1) {
        // Cap the rounding to a fraction of the smaller half-extent. Without this a large value
        // degenerates the shape into an oval: on the 176x166 inventory panel a radius of 83 against
        // half-extents of 88/83 leaves only ~10px of straight top and bottom edge, which reads as an
        // arch. The cap also absorbs configs written when this option was a circle radius (default
        // 112), so existing server configs self-heal instead of needing a manual edit.
        float maxCorner = 0.25 * min(inner.x, inner.y);
        float cornerR = clamp(radius, 0.0, maxCorner);
        // Negative distance is inside the panel rect, so no panel pixel is ever faded: the mask only
        // softens the empty margin around it and rounds the corners.
        float dist = sdRoundBox(pixelCoord - centre, inner, cornerR);
        if (dist > 0.0) {
            if (dist > feather) {
                result.a = 0.0;
            } else {
                result.a = min(result.a, 1.0 - dist / feather);
            }
        }
    }

    fragColor = result;
}