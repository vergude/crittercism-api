package intexsoft.by.crittercismapi.data.remote.request;

import java.io.Serializable;

/**
 * Created by Евгений on 24.07.2014.
 */
public class GraphRequest implements Serializable
{
    private GraphRequestInternal params = new GraphRequestInternal();

    public void setParams(GraphRequestInternal params)
    {
        this.params = params;
    }

    public GraphRequestInternal getParams()
    {
        return params;
    }
}
