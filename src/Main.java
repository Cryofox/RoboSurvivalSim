/**
 * Created by Ryder Stancescu on 1/8/2018.
 */

import com.sun.org.apache.xerces.internal.impl.xs.opti.DefaultText;
import org.lwjgl.Version;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.Models.RawModel;
import renderEngine.Models.TexturedModel;
import renderEngine.Renderer;
import renderEngine.Shaders.DefaultShader;
import renderEngine.Shaders.DefaultTexturedShader;
import renderEngine.Textures.ModelTexture;

public class Main {

    public static void main(String[] args) {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        DefaultShader defaultShader = new DefaultShader();
        DefaultTexturedShader defaultTexturedShader = new DefaultTexturedShader();
        //Hardcoded Model
        float[] vertices = {
                -0.5f, 0.5f, 0f, //V0
                -0.5f, -0.5f, 0f, //V1
                0.5f, -0.5f, 0f, //V2
                0.5f, 0.5f, 0f, //V3
        };
        int[] indices = {
                0, 1, 2,
                2, 3, 0
        };
        float[] textureCoords={
                0,0, //V0
                0,1,  //V1
                1,1, //V2
                1,0, //V3
        };

        RawModel model = loader.loadToVAO(vertices,textureCoords, indices);
        ModelTexture texture = new ModelTexture(loader.loadTexture("uvgrid01.png"));
        TexturedModel texturedModel = new TexturedModel(model,texture);

        while(!DisplayManager.isCloseRequested())
        {
            DisplayManager.pollInput();
            DisplayManager.clearDisplay();
            defaultTexturedShader.start();
            //Render everything In OpenGL
            renderer.render(texturedModel);
            defaultTexturedShader.stop();
            //Poll Events, Clear Screen and Blit to Window
            DisplayManager.updateDisplay(); //Clears screen and swaps buffers
        }
        defaultTexturedShader.dispose();
        defaultShader.dispose();
        loader.dispose();
        DisplayManager.closeDisplay();
    }
}
