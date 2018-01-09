package renderEngine.Shaders;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public class DefaultTexturedShader extends ShaderProgram {

    private static final String VERTEX_FILE = "D:\\Projects\\KawaiiKaiju\\RoboSurvivalSim\\shaders\\DefaultTextured\\DefaultTextured.vert";
    private static final String FRAGMENT_FILE = "D:\\Projects\\KawaiiKaiju\\RoboSurvivalSim\\shaders\\DefaultTextured\\DefaultTextured.frag";

    public DefaultTexturedShader()
    {
        super(VERTEX_FILE,FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
        super.bindAttribute(1,"textureCoords");
    }
}
