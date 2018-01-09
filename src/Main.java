/**
 * Created by Ryder Stancescu on 1/8/2018.
 */

import org.lwjgl.Version;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;
import renderEngine.Shaders.DefaultShader;

public class Main {

    public static void main(String[] args) {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();
        DefaultShader defaultShader = new DefaultShader();

        //Hardcoded Model
        float[] vertices = {
                -0.5f, 0.5f, 0f, //V0
                -0.5f, -0.5f, 0f, //V1
                0.5f, -0.5f, 0f, //V2
                0.5f, 0.5f, 0f, //V3
        };
        int[] indices={
                0,1,3,
                3,1,2
        };
        RawModel model = loader.loadToVAO(vertices, indices);

        while(!DisplayManager.isCloseRequested())
        {
            DisplayManager.pollInput();
            DisplayManager.clearDisplay();
            defaultShader.start();
            //Render everything In OpenGL
            renderer.render(model);
            defaultShader.stop();
            //Poll Events, Clear Screen and Blit to Window
            DisplayManager.updateDisplay(); //Clears screen and swaps buffers
        }
        defaultShader.dispose();

        loader.dispose();
        DisplayManager.closeDisplay();
    }
}
