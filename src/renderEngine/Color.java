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

    //Core Colors
    public static final Color red = new Color(255,0,0);
    public static final Color blue = new Color(0,0,255);
    public static final Color green = new Color(0,255,0);
    public static final Color yellow = new Color(255,255,0);
    public static final Color cyan = new Color(0,255,255);
    public static final Color black = new Color(0,0,0);
    public static final Color white = new Color(255,255,255);

    public static final Color[] heatmap = { black,blue,cyan,green,yellow,red};

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


    /***
     *
     * @param percentage: Value from 0 -> 1
     *                    0 = Low Heat (Blue)
     *                    1 = High Heat (Red)
     * @return
     */


    public static Color heatmapValue(float percentage)
    {
        Color heatmapColor;
        int heatmapColors = heatmap.length;
        if(percentage < 0 )
        {
            heatmapColor = heatmap[0];
        }
        else if(percentage >1f)
        {
            heatmapColor = heatmap[heatmapColors-1];
        }
        else
        {
            percentage = percentage * ((float)(heatmapColors-1));
            int color1Index = (int)percentage;
            int color2Index = color1Index+1;
            percentage = percentage - ((float)color1Index); // Percentage is now the p% between color1 & color 2

            float red = heatmap[color2Index].r - heatmap[color1Index].r * percentage + heatmap[color1Index].r;
            float blue = heatmap[color2Index].b - heatmap[color1Index].b * percentage + heatmap[color1Index].b;
            float green = heatmap[color2Index].g - heatmap[color1Index].g * percentage + heatmap[color1Index].g;
            heatmapColor = new Color(red,blue,green);
        }
        return heatmapColor;
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
