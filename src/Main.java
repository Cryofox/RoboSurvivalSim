/**
 * Created by Ryder Stancescu on 1/8/2018.
 */

import org.lwjgl.Version;
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.RawModel;
import renderEngine.Renderer;

public class Main {

    public static void main(String[] args) {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");

        DisplayManager.createDisplay();
        Loader loader = new Loader();
        Renderer renderer = new Renderer();

        float[] vertices = {
                //Left Bot Triangle
                -0.5f, 0.5f, 0f,
                -0.5f, -0.5f, 0f,
                0.5f, -0.5f, 0f,
                //Right Top Triangle
                0.5f, -0.5f, 0f,
                0.5f, 0.5f, 0f,
                -0.5f, 0.5f, 0f
        };

        RawModel model = loader.loadToVAO(vertices);

        while(!DisplayManager.isCloseRequested())
        {
            DisplayManager.pollInput();
            DisplayManager.clearDisplay();

            //Render everything In OpenGL
            renderer.render(model);
            //Poll Events, Clear Screen and Blit to Window
            DisplayManager.updateDisplay(); //Clears screen and swaps buffers
        }

        loader.purge();
        DisplayManager.closeDisplay();
    }
}
