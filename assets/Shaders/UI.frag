uniform sampler2D m_ColorMap;
 

varying vec2 texCoord;

void main() {
    vec2 uv = texCoord;
 
    vec4 baseColor = texture2D(m_ColorMap, uv);
    gl_FragColor = baseColor;
}
