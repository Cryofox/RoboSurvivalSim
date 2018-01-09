package renderEngine.Models;

import renderEngine.Textures.ModelTexture;

/**
 * Created by Ryder Stancescu on 1/9/2018.
 */
public class TexturedModel{
    private RawModel rawModel;
    private ModelTexture texture;

    public TexturedModel(RawModel rawModel, ModelTexture texture)
    {
        this.rawModel=rawModel; this.texture=texture;
    }









    public RawModel getRawModel() {
        return rawModel;
    }
    public ModelTexture getTexture() {
        return texture;
    }
}
