package renderEngine.Shaders;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public class DefaultShader extends ShaderProgram {

    private static final String VERTEX_FILE = "../shaders/Default/Default.vert";
    private static final String FRAGMENT_FILE = "../shaders/Default/Default.frag";

    public DefaultShader()
    {
        super(VERTEX_FILE,FRAGMENT_FILE);
    }

    @Override
    protected void bindAttributes() {
        super.bindAttribute(0,"position");
    }
}
