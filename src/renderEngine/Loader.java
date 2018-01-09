package renderEngine;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import renderEngine.OpenGL.Enums.BufferBindingTargetType;
import renderEngine.OpenGL.Enums.BufferUsageType;
import renderEngine.OpenGL.Enums.DataType;
import renderEngine.OpenGL.OpenGLHelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();

    private List<Integer> textures = new ArrayList<>();
    public RawModel loadToVAO(float[] positions, int[] indices)
    {
        int vaoID = createVAO();
        bindIndexBuffer(indices);
        bindVertexBuffer(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length); //1 Vertex = (x,y,z)
    }

    public int loadTexture(String fileName){
        Texture texture=null;
        try {
            texture = TextureLoader.getTexture("PNG",new FileInputStream("res/"+fileName+".png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }



    public void dispose(){
        for(int vao:vaos){
            OpenGLHelper.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos)
        {
            OpenGLHelper.glDeleteBuffers(vbo);
        }
        for(int texture:textures)
        {
            OpenGLHelper.glDeleteTextures(texture);
        }
        vaos.clear();
        vbos.clear();
    }



    private int createVAO(){
        int vaoID = OpenGLHelper.glGenVertexArrays();
        vaos.add(vaoID);
        OpenGLHelper.glBindVertexArray(vaoID);
        return vaoID;
    }


    private void bindVertexBuffer(int attributeNumder, float[] data)
    {
        //Create and Set VBO
        int vboID = OpenGLHelper.glGenBuffers();
        vbos.add(vboID);
        OpenGLHelper.glBindBuffer(BufferBindingTargetType.GL_ARRAY_BUFFER,vboID); //Bind VertexBuffer
        OpenGLHelper.glBufferData(BufferBindingTargetType.GL_ARRAY_BUFFER,data, BufferUsageType.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
        OpenGLHelper.glVertexAttribPointer(attributeNumder,3, DataType.GL_FLOAT,false,0,0);

        OpenGLHelper.glBindBuffer(BufferBindingTargetType.GL_ARRAY_BUFFER,0); //Unbind VBO
    }

    //Do not unbing IndexBuffer, each VAO has 1 slot dedicated for an index buffer, unbinding removes it from dedicated slot
    private void bindIndexBuffer(int[] indices)
    {
        //Create and Set VBO
        int vboID = OpenGLHelper.glGenBuffers();
        vbos.add(vboID);
        OpenGLHelper.glBindBuffer(BufferBindingTargetType.GL_ELEMENT_ARRAY_BUFFER,vboID); //Bind VBO
        OpenGLHelper.glBufferData(BufferBindingTargetType.GL_ELEMENT_ARRAY_BUFFER,indices,BufferUsageType.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
    }

    private void unbindVAO()
    {
        OpenGLHelper.glBindVertexArray(0); //Unbind VAO by passing 0 as ID
    }



}
