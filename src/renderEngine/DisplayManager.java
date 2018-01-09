package renderEngine;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.GL_TRUE;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class DisplayManager {

    private static final int WINDOW_WIDTH = 1280; //Pixels
    private static final int WINDOW_HEIGHT = 720; //Pixels
    private static final int FPS_CAP = 120;
    private static long window =0;
    private  static  void createWindow()
    {
        GLFW.glfwWindowHint( GLFW.GLFW_RESIZABLE,  GL_TRUE);
        GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint( GLFW.GLFW_OPENGL_PROFILE,  GLFW.GLFW_OPENGL_CORE_PROFILE);
        int fullscreen = 0; //False
        int sharedOpenGLContexts = 0;
        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "Pong - LWJGL3", fullscreen, sharedOpenGLContexts);
        if(window == 0) {
            throw new RuntimeException("Failed to create window");
        }

        GLFW.glfwMakeContextCurrent(window); //Assign Window Context
        GL.createCapabilities();  //Create based off Settings
        GLFW.glfwShowWindow(window);
    }

    public static void createDisplay()
    {
        createWindow();
        GLFW.glfwShowWindow(window);
    }



    public static void updateDisplay()
    {
        GLFW.glfwPollEvents();
        GLFW.glfwSwapBuffers(window);
    }

    public static void closeDisplay()
    {
        GLFW.glfwDestroyWindow(window);
    }
}
