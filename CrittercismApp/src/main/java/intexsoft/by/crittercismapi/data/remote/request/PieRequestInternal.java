package intexsoft.by.crittercismapi.data.remote.request;

import com.fasterxml.jackson.annotation.JsonInclude;

import intexsoft.by.crittercismapi.data.remote.response.Filter;

/**
 * Created by Евгений on 29.07.2014.
 */
public class PieRequestInternal
{
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String appId;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String [] applds;

	private String groupBy;
    private String graph;
    private int duration;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Filter filters;

    public String[] getApplds() {
        return applds;
    }

    public void setApplds(String[] applds) {
        this.applds = applds;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getGroupBy() {
        return groupBy;
    }

    public void setGroupBy(String groupBy) {
        this.groupBy = groupBy;
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

    public Filter getFilters() {
        return filters;
    }

    public void setFilters(Filter filters) {
        this.filters = filters;
    }
}
