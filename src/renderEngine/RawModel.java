package renderEngine;

/**
 * Created by Ryder Stancescu on 1/8/2018.
 */
public class RawModel {


    private int vaoId;
    private int vertexCount;

    public RawModel(int vaoID, int vertexCount)
    {
        this.vaoId = vaoID;
        this.vertexCount=vertexCount;
    }

    //Getters
    public int getVaoId() {
        return vaoId;
    }

    public int getVertexCount() {
        return vertexCount;
    }
}
