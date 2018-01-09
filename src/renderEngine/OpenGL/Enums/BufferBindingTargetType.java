package renderEngine.OpenGL.Enums;

import org.lwjgl.opengl.GL15;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public enum BufferBindingTargetType {    //Enums
    GL_ARRAY_BUFFER(GL15.GL_ARRAY_BUFFER),
    GL_ELEMENT_ARRAY_BUFFER(GL15.GL_ELEMENT_ARRAY_BUFFER);


    //Accessors
    private int value;
    private BufferBindingTargetType(int value) {this.value = value;}
    public int getValue() {
        return value;
    }
}