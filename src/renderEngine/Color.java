package renderEngine;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class Color {

    private float r =0.0f;
    private float b =0.0f;
    private float g =0.0f;



    //http://www.flatuicolorpicker.com/
    public static final Color ebonyClay = new Color(34,49,63);

    //Constructor using 0->255 color range
    public Color(int r, int g, int b)
    {
        this( ((float)r)/255f,((float)g) /255f,((float)b)/255f );
    }

    public Color(float r, float g, float b)
    {
        this.r = Math.min(1f,Math.max(0.0f,r));
        this.g = Math.min(1f,Math.max(0.0f,g));
        this.b = Math.min(1f,Math.max(0.0f,b));
    }

    //Getters
    //--------
    public float getR() {
        return r;
    }
    public float getG() {
        return g;
    }
    public float getB() {
        return b;
    }
    //========

}
