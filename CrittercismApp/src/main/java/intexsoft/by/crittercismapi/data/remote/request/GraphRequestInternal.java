package intexsoft.by.crittercismapi.data.remote.request;

import java.io.Serializable;

/**
 * Created by Евгений on 24.07.2014.
 */
public class GraphRequestInternal implements Serializable
{
    private String appId;
    private String graph;
    private int duration;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGraph() {
        return graph;
    }

    public void setGraph(String graph) {
        this.graph = graph;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }
}
