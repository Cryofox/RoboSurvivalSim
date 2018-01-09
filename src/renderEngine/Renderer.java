package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

import static org.lwjgl.opengl.GL11.GL_COLOR_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.GL_DEPTH_BUFFER_BIT;
import static org.lwjgl.opengl.GL11.glClear;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Renderer {


    public  void render(RawModel model)
    {
        GL30.glBindVertexArray((model.getVaoId()));
        GL20.glEnableVertexAttribArray(0);
        GL11.glDrawArrays(GL11.GL_TRIANGLES,0,model.getVertexCount());
        //Mode, Vertex, Type of Element DataSet (Indices = unsigned int), offsetStart = 0
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(),GL11.GL_UNSIGNED_INT,0);
        GL20.glDisableVertexAttribArray(0);
        GL30.glBindVertexArray(0);//Unbind VertexArray
    }
}
