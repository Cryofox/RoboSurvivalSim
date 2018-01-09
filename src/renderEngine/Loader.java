package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import renderEngine.Models.RawModel;
import renderEngine.Models.TexturedModel;
import renderEngine.OpenGL.Enums.BufferBindingTargetType;
import renderEngine.OpenGL.Enums.BufferUsageType;
import renderEngine.OpenGL.Enums.DataType;
import renderEngine.OpenGL.OpenGLHelper;
import renderEngine.Textures.ModelTexture;
import renderEngine.Textures.Texture;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.GL_CLAMP_TO_BORDER;
import static org.lwjgl.opengl.GL30.glGenerateMipmap;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryStack.stackPush;

import static org.lwjgl.BufferUtils.*;
//import static renderEngine.IOUtil.ioResourceToByteBuffer;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();

    private List<Integer> textures = new ArrayList<>();

    public RawModel loadToVAO(float[] positions,float[] textureCoords, int[] indices)
    {
        int vaoID = createVAO();
        bindIndexBuffer(indices);
        bindVertexPositionBuffer(0,positions);
        bindTextureCoordBuffer(1,textureCoords);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    public RawModel loadToVAO(float[] positions, int[] indices)
    {
        int vaoID = createVAO();
        bindIndexBuffer(indices);
        bindVertexPositionBuffer(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length);
    }

    //
    public int loadTexture(String fileName){


        int textureID = -1;
        //Acceptable Types: JPEG, PNG, TGA, BMP, PSD, GIF, HDR, PIC and PNM
        String filePath= Config.resourceDir+"/"+fileName;
        textureID = Texture.loadTexture(filePath).getId();
        textures.add(textureID);
        //Absolute FileName
        /*try ( MemoryStack stack = stackPush() ) {
            File file = new File(filePath);
            if(!file.exists()) throw new IOException();

            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);
            IntBuffer comp = stack.mallocInt(1);
            stbi_set_flip_vertically_on_load(true); //Origin = Bottom Left

            //ByteBuffer buffer = stbi_load(filePath, w, h, comp, 4);
            ByteBuffer imageBuffer = ioResourceToByteBuffer(filePath, 4*256);
            // Use info to read image metadata without decoding the entire image.
            // We don't need this for this demo, just testing the API.
            ByteBuffer image = stbi_load(imageBuffer, w, h, comp,4);
            if(image == null) {
                throw new RuntimeException("Failed to read image information: " + stbi_failure_reason());
            }

            int width = w.get();
            int height = h.get();

            //See Tutorial: https://github.com/SilverTiger/lwjgl3-tutorial/wiki/Texture
            textureID = GL11.glGenTextures();
            textures.add(textureID);
            //Store Image on GPU
            GL11.glBindTexture(GL_TEXTURE_2D,textureID);


            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_BORDER);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_BORDER);

            //Filter Types = Nearest / Linear
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
            GL11.glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, image);
            //glGenerateMipmap(GL_TEXTURE_2D);

           // buffer.clear();
            System.out.println("W:"+ width);
            System.out.println("H:"+height);
            GL11.glBindTexture(GL_TEXTURE_2D,0); //Unbind the Texture

        } // the stack frame is popped automatically
        catch (IOException e) {
            e.printStackTrace();
        }
*/
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
        textures.clear();
    }



    private int createVAO(){
        int vaoID = OpenGLHelper.glGenVertexArrays();
        vaos.add(vaoID);
        OpenGLHelper.glBindVertexArray(vaoID);
        return vaoID;
    }


    private void bindVertexPositionBuffer(int attributeNumber, float[] data)
    {
        bindDataToVBO(attributeNumber,data,3);
    }
    private void bindTextureCoordBuffer(int attributeNumber, float[] data)
    {
        bindDataToVBO(attributeNumber,data,2);
    }

    private void bindDataToVBO(int attributeNumber, float[] data, int tupleSize)
    {
        //Create and Set VBO
        int vboID = OpenGLHelper.glGenBuffers();
        vbos.add(vboID);
        OpenGLHelper.glBindBuffer(BufferBindingTargetType.GL_ARRAY_BUFFER,vboID); //Bind VertexBuffer
        OpenGLHelper.glBufferData(BufferBindingTargetType.GL_ARRAY_BUFFER,data, BufferUsageType.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
        OpenGLHelper.glVertexAttribPointer(attributeNumber,tupleSize, DataType.GL_FLOAT,false,0,0);

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
