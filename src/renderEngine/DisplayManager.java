package renderEngine;


import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;

import static org.lwjgl.opengl.GL11.*;


import org.lwjgl.*;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import java.nio.*;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class DisplayManager {

    private static final int WINDOW_WIDTH = 1280; //Pixels
    private static final int WINDOW_HEIGHT = 720; //Pixels
    private static final int FPS_CAP = 120;
    private static long window ;
    private  static  void createWindow()
    {
        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        GLFW.glfwWindowHint( GLFW.GLFW_RESIZABLE,  GL_TRUE);
        GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
        GLFW.glfwWindowHint( GLFW.GLFW_CONTEXT_VERSION_MINOR, 3);
        GLFW.glfwWindowHint( GLFW.GLFW_OPENGL_PROFILE,  GLFW.GLFW_OPENGL_CORE_PROFILE);
       // GLFW.glfwDefaultWindowHints();
        int fullscreen = 0; //False
        int sharedOpenGLContexts = 0;
        window = GLFW.glfwCreateWindow(WINDOW_WIDTH, WINDOW_HEIGHT, "RoboSurvival", fullscreen, sharedOpenGLContexts);
        if(window == MemoryUtil.NULL ) {
            throw new RuntimeException("Failed to create window");
        }

        setupKeyCallbacks();

        GLFW.glfwMakeContextCurrent(window); //Assign Window Context
        GLFW.glfwShowWindow(window);
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();  //Create based off Settings
        Color clearColor = Color.ebonyClay;

        glClearColor(clearColor.getR(),clearColor.getG(),clearColor.getB(), 0.0f);
    }


    // Setup a key callback. It will be called every time a key is pressed, repeated or released.
    private  static void setupKeyCallbacks()
    {
        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
            if ( key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE )
                glfwSetWindowShouldClose(window, true); // We will detect this in the rendering loop
        });

    }

    public static void createDisplay()
    {
        createWindow();
        GLFW.glfwShowWindow(window);
    }



    public static void updateDisplay()
    {
        GLFW.glfwPollEvents();

        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
        GLFW.glfwSwapBuffers(window);
    }

    public static void closeDisplay()
    {
        GLFW.glfwDestroyWindow(window);
    }

    public static boolean isCloseRequested()
    {
        return (GLFW.glfwWindowShouldClose(window));
    }
}
