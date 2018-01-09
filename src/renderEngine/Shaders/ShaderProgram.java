package renderEngine.Shaders;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public abstract class ShaderProgram {
    private int programId ;
    private int vertexShaderId;
    private int fragmentShaderId;

    public ShaderProgram(String vertexFile,String fragmentFile)
    {
        vertexShaderId = loadShader(vertexFile,GL20.GL_VERTEX_SHADER);
        fragmentShaderId = loadShader(fragmentFile,GL20.GL_FRAGMENT_SHADER);

        programId = GL20.glCreateProgram();
        GL20.glAttachShader(programId, vertexShaderId);
        GL20.glAttachShader(programId, fragmentShaderId);
        bindAttributes();
        GL20.glLinkProgram(programId); //Links the AttachedShader via ID's
        GL20.glValidateProgram(programId); //Ensures the Program is Executable
    }

    public void start(){

        GL20.glUseProgram(programId);
    }
    public void stop(){
        GL20.glUseProgram(0);
    }

    public void dispose(){
        stop();
        GL20.glDetachShader(programId,vertexShaderId);
        GL20.glDetachShader(programId,fragmentShaderId);
        GL20.glDeleteShader(vertexShaderId);
        GL20.glDeleteShader(fragmentShaderId);
        GL20.glDeleteProgram(programId);
    }

    protected abstract void bindAttributes();

    protected void bindAttribute(int attribute, String variableName){
        GL20.glBindAttribLocation(programId,attribute,variableName);
    }

    /**
     * Parses a provided shader file's source, attempts a compilation and returns the ID of the compiled Program
     * @param file
     * @param type
     * @return
     */
    private static int loadShader(String file, int type) {
        StringBuilder shaderSource = new StringBuilder();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                shaderSource.append(line).append("//\n");
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
        int shaderID = GL20.glCreateShader(type);
        GL20.glShaderSource(shaderID, shaderSource);
        GL20.glCompileShader(shaderID);
        if (GL20.glGetShaderi(shaderID, GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE) {
            System.out.println(GL20.glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader!");
            System.exit(-1);
        }
        return shaderID;
    }
}
