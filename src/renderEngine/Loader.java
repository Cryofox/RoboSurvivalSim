package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();


    public RawModel loadToVAO(float[] positions, int[] indices)
    {
        int vaoID = createVAO();
        bindIndexBuffer(indices);
        bindVertexBuffer(0,positions);
        unbindVAO();
        return new RawModel(vaoID,indices.length); //1 Vertex = (x,y,z)
    }

    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    public void purge(){
        for(int vao:vaos){
            GL30.glDeleteVertexArrays(vao);
        }
        for(int vbo:vbos)
        {
            GL15.glDeleteBuffers(vbo);
        }
        vaos.clear();
        vbos.clear();
    }

    private void bindVertexBuffer(int attributeNumder, float[] data)
    {
        //Create and Set VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Bind VBO
        FloatBuffer buffer = convertToFloatBuffer(data); //Convert to Buffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
        GL20.glVertexAttribPointer(attributeNumder,3, GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind VBO
    }

    //Do not unbing IndexBuffer, each VAO has 1 slot dedicated for an index buffer, unbinding removes it from dedicated slot
    private void bindIndexBuffer(int[] indices)
    {
        //Create and Set VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID); //Bind VBO
        IntBuffer buffer = convertToIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO

    }

    private void unbindVAO()
    {
        GL30.glBindVertexArray(0); //Unbind VAO by passing 0 as ID
    }

    private FloatBuffer convertToFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Finished writing, ready to read from
        return buffer;
    }
    private IntBuffer convertToIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Finished writing, ready to read from
        return buffer;
    }

}
