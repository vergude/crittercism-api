package intexsoft.by.crittercismapi.data.remote.request;

import java.io.Serializable;

/**
 * Created by Евгений on 24.07.2014.
 */
public class GraphRequestInternal implements Serializable
{
    private String appId;
    //private String [] applds;
    private String graph;
    private int duration;
    //private Filter filters;

//    public String[] getApplds() {
//        return applds;
//    }
//
//    public void setApplds(String[] applds) {
//        this.applds = applds;
//    }

//   public Filter getFilters() {
//        return filters;
//    }

//    public void setFilters(Filter filters) {
//        this.filters = filters;
//    }

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
