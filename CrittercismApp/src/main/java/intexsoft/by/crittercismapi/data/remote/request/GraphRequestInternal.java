package intexsoft.by.crittercismapi.data.remote.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import intexsoft.by.crittercismapi.data.remote.response.Filter;

import java.io.Serializable;

/**
 * Created by Евгений on 24.07.2014.
 */
public class GraphRequestInternal implements Serializable
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String appId;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String[] appIds;
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String graph;
	private int duration;

	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private Filter filters;

	public String[] getAppIds()
	{
		return appIds;
	}

	public void setAppIds(String[] appIds)
	{
		this.appIds = appIds;
	}

	public String getAppId()
	{
		return appId;
	}

	public void setAppId(String appId)
	{
		this.appId = appId;
	}

	public String getGraph()
	{
		return graph;
	}

	public void setGraph(String graph)
	{
		this.graph = graph;
	}

	public int getDuration()
	{
		return duration;
	}

	public void setDuration(int duration)
	{
		this.duration = duration;
	}

	public Filter getFilters()
	{
		return filters;
	}

	public void setFilters(Filter filters)
	{
		this.filters = filters;
	}
}
