package renderEngine;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<Integer>();
    private List<Integer> vbos = new ArrayList<Integer>();


    public RawModel loadToVAO(float[] positions)
    {
        int vaoID = createVAO();
        storeDataInAttributeList(0,positions);
        unbindVAO();
        return new RawModel(vaoID,positions.length/3); //1 Vertex = (x,y,z)
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

    private void storeDataInAttributeList(int attributeNumder, float[] data)
    {
        //Create and Set VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID); //Bind VBO
        FloatBuffer buffer = storeDatainFloatBuffer(data); //Convert to Buffer
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
        GL20.glVertexAttribPointer(attributeNumder,3, GL11.GL_FLOAT,false,0,0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0); //Unbind VBO
    }
    private void unbindVAO()
    {
        GL30.glBindVertexArray(0); //Unbind VAO by passing 0 as ID
    }

    private FloatBuffer storeDatainFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Finished writing, ready to read from
        return buffer;
    }


}
