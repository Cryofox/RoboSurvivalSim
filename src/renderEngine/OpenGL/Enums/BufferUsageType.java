package renderEngine.OpenGL.Enums;

import org.lwjgl.opengl.GL15;

/**
 https://www.khronos.org/registry/OpenGL-Refpages/gl4/html/glBufferData.xhtml

 STREAM
 The data store contents will be modified once and used at most a few times.

 STATIC
 The data store contents will be modified once and used many times.

 DYNAMIC
 The data store contents will be modified repeatedly and used many times.

 The nature of access may be one of these:

 DRAW
 The data store contents are modified by the application, and used as the source for GL drawing and image specification commands.

 READ
 The data store contents are modified by reading data from the GL, and used to return that data when queried by the application.

 COPY
 The data store contents are modified by reading data from the GL, and used as the source for GL drawing and image specification commands.
 */
public enum BufferUsageType {
    GL_STREAM_DRAW(GL15.GL_STREAM_DRAW),
    GL_STREAM_READ(GL15.GL_STREAM_READ),
    GL_STREAM_COPY(GL15.GL_STREAM_COPY),

    GL_STATIC_DRAW(GL15.GL_STATIC_DRAW),
    GL_STATIC_READ(GL15.GL_STATIC_READ),
    GL_STATIC_COPY(GL15.GL_STATIC_COPY),

    GL_DYNAMIC_DRAW(GL15.GL_DYNAMIC_DRAW),
    GL_DYNAMIC_READ(GL15.GL_DYNAMIC_READ),
    GL_DYNAMIC_COPY(GL15.GL_DYNAMIC_COPY);

    //Accessors
    private int value;
    private BufferUsageType(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
