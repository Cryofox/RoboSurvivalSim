#version 150 core

in vec3 position;
in vec2 textureCoords;

out vec2 pass_TextureCoord;

void main(void){
    gl_Position = vec4(position.x,position.y,position.z,1.0);
    pass_TextureCoord = textureCoords;
}
