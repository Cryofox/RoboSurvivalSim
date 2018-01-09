/**
 * Created by Ryder Stancescu on 1/8/2018.
 */

import org.lwjgl.Version;
import renderEngine.DisplayManager;

public class Main {

    public static void main(String[] args) {
        System.out.println("LWJGL Version " + Version.getVersion() + " is working.");
        DisplayManager.createDisplay();

        while(!DisplayManager.isCloseRequested())
        {
            DisplayManager.updateDisplay();


        }


        DisplayManager.closeDisplay();
    }
}
