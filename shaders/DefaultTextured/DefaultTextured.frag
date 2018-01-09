#version 150 core

in vec2 pass_TextureCoord;

out vec4 out_color;

uniform sampler2D texture_diffuse;

void main(void){
    out_color = texture(texture_diffuse, pass_TextureCoord);
}