package renderEngine;

import de.matthiasmann.twl.utils.PNGDecoder;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
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
import static org.lwjgl.opengl.GL13.GL_TEXTURE0;
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



        //Acceptable Types: JPEG, PNG, TGA, BMP, PSD, GIF, HDR, PIC and PNM
        String filePath= Config.resourceDir+"/"+fileName;
        //Absolute FileName
        ByteBuffer buf = null;
        int tWidth = 0;
        int tHeight = 0;

        try {
            // Open the PNG file as an InputStream
            InputStream in = new FileInputStream(filePath);
            // Link the PNG decoder to this stream
            PNGDecoder decoder = new PNGDecoder(in);

            // Get the width and height of the texture
            tWidth = decoder.getWidth();
            tHeight = decoder.getHeight();


            // Decode the PNG file in a ByteBuffer
            buf = ByteBuffer.allocateDirect(
                    4 * decoder.getWidth() * decoder.getHeight());
            decoder.decode(buf, decoder.getWidth() * 4, PNGDecoder.Format.RGBA);
            buf.flip();
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }

        // Create a new texture object in memory and bind it
        int texId = GL11.glGenTextures();
        textures.add(texId);
        GL13.glActiveTexture(GL_TEXTURE0);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texId);

        // All RGB bytes are aligned to each other and each component is 1 byte
        GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);

        // Upload the texture data and generate mip maps (for scaling)
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, tWidth, tHeight, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buf);

        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);
        GL30.glGenerateMipmap(GL11.GL_TEXTURE_2D);

        // Setup the ST coordinate system
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL11.GL_REPEAT);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL11.GL_REPEAT);

        // Setup what to do when the texture has to be scaled
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
                GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
                GL11.GL_LINEAR_MIPMAP_LINEAR);

        this.exitOnGLError("loadPNGTexture");

        return texId;
    }

    private void exitOnGLError(String errorMessage) {
        int errorValue = GL11.glGetError();

        if (errorValue != GL11.GL_NO_ERROR) {
            //String errorString = GLU.gluErrorString(errorValue);
            String errorString = ""+errorValue;
            System.err.println("ERROR - " + errorMessage + ": " + errorString);

            //if (Display.isCreated()) Display.destroy();
            DisplayManager.closeDisplay();
            System.exit(-1);
        }
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
