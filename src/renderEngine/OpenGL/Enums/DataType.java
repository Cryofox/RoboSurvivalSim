package renderEngine.OpenGL.Enums;

import org.lwjgl.opengl.GL11;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public enum DataType {
    GL_FLOAT(GL11.GL_FLOAT),
    GL_INT(GL11.GL_INT),
    GL_DOUBLE(GL11.GL_DOUBLE);


    //Accessors
    private int value;
    private DataType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}