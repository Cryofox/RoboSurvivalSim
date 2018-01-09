import org.junit.jupiter.api.Test;
import renderEngine.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class ColorTest {
    @Test
    public void presetColor()
    {
        //EbonyClay = 34,49,63
        float r = 34f/ 255f;
        float g = 49f / 255f;
        float b = 63f / 255f;

        assertEquals(r, Color.ebonyClay.getR());
        assertEquals(g, Color.ebonyClay.getG());
        assertEquals(b, Color.ebonyClay.getB());

    }

    @Test
    public void conversionFrom255()
    {
        //EbonyClay = 34,49,63
        int r = 34;
        int g = 49;
        int b = 63;

        float fr = 34f/ 255f;
        float fg = 49f / 255f;
        float fb = 63f / 255f;
        Color color = new Color(r,g,b);
        assertEquals(fr, color.getR());
        assertEquals(fg, color.getG());
        assertEquals(fb, color.getB());
    }

    @Test
    public void fromRaw()
    {
        //EbonyClay = 34,49,63
        float r = 34f/ 255f;
        float g = 49f / 255f;
        float b = 63f / 255f;
        Color color = new Color(r,g,b);
        assertEquals(r, color.getR());
        assertEquals(g, color.getG());
        assertEquals(b, color.getB());
    }
}
