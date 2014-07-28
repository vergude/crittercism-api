package intexsoft.by.crittercismapi.data.remote.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.io.Serializable;

/**
 * Created by Евгений on 24.07.2014.
 */


public class GraphRequest implements Serializable
{
	@JsonProperty
    private GraphRequestInternal params;

    public void setParams(GraphRequestInternal params)
    {
        this.params = params;
    }

    public GraphRequestInternal getParams()
    {
        return params;
    }
}
