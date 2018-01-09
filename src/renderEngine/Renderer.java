package renderEngine;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import renderEngine.Models.RawModel;
import renderEngine.Models.TexturedModel;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Renderer {

    public  void render(TexturedModel texturedModel)
    {
        RawModel model = texturedModel.getRawModel();
        GL30.glBindVertexArray((model.getVaoId()));
        GL20.glEnableVertexAttribArray(0);
        GL20.glEnableVertexAttribArray(1);
        GL13.glActiveTexture(GL13.GL_TEXTURE0); //Sampler2D uses TextureBank0 by Default
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, texturedModel.getTexture().getID());
        //Mode, Vertex, Type of Element DataSet (Indices = unsigned int), offsetStart = 0
        GL11.glDrawElements(GL11.GL_TRIANGLES, model.getVertexCount(),GL11.GL_UNSIGNED_INT,0);
        GL20.glDisableVertexAttribArray(0);
        GL20.glDisableVertexAttribArray(1);
        GL30.glBindVertexArray(0);//Unbind VertexArray
    }

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
