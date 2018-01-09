package renderEngine.OpenGL;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderEngine.OpenGL.Enums.BufferBindingTargetType;
import renderEngine.OpenGL.Enums.BufferUsageType;
import renderEngine.OpenGL.Enums.DataType;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * Class made to simplify the usage of OPENGL and group it's multiple ID's.
 * The LWJGL.OpenGL docs lack information on opengl usage, names will remane 1-1 for lookup, but parameters will be
 * replaced with their enum quivilant
 */
public class OpenGLHelper {

    //OpenGL15
    //--------
    public final static int glGenVertexArrays(){
        return GL30.glGenVertexArrays();
    }
    public final static int glGenBuffers(){
        return GL15.glGenBuffers();
    }


    public final static void glBindVertexArray(int id){
        GL30.glBindVertexArray(id);
    }
    public final static void glBindBuffer(BufferBindingTargetType type, int id){
        GL15.glBindBuffer(type.getValue(), id);
    }


    /***
     * Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
     * @param type
     * @param buffer = int[], float[], IntBuffer, FloatBuffer
     * @param usage
     */
    public final static void glBufferData(BufferBindingTargetType type, float[] buffer, BufferUsageType usage){
        glBufferData(type, convertToFloatBuffer(buffer),usage); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
    }
    public final static void glBufferData(BufferBindingTargetType type, int[] buffer, BufferUsageType usage){
        glBufferData(type, convertToIntBuffer(buffer),usage); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
    }
    public final static void glBufferData(BufferBindingTargetType type, FloatBuffer buffer, BufferUsageType usage){
        GL15.glBufferData(type.getValue(), buffer,usage.getValue()); //Store Buffer Data: GL Static Draw = Non-Editable once Stored in VBO
    }
    public final static void glBufferData(BufferBindingTargetType type, IntBuffer buffer, BufferUsageType usage){
        GL15.glBufferData(type.getValue(), buffer,usage.getValue());
    }
    //========

    public final static void glVertexAttribPointer(int attributeNumber, int size, DataType dataType, boolean isNormalized, int stride, int offset)
    {
        GL20.glVertexAttribPointer(attributeNumber,size, dataType.getValue(),false,0,0);
    }
    public final static void glDeleteTextures(int textureID)
    {
        GL11.glDeleteTextures(textureID);
    }

    public final static void glDeleteBuffers(int bufferID)
    {
        GL15.glDeleteBuffers(bufferID);
    }
    public final static void glDeleteVertexArrays(int arrayID)
    {
        GL30.glDeleteVertexArrays(arrayID);
    }

    //Converters
    public final static FloatBuffer convertToFloatBuffer(float[] data)
    {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Finished writing, ready to read from
        return buffer;
    }
    public final static IntBuffer convertToIntBuffer(int[] data)
    {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip(); //Finished writing, ready to read from
        return buffer;
    }



}
