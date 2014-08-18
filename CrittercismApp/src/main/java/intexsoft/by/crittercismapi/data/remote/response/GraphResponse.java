package intexsoft.by.crittercismapi.data.remote.response;

import intexsoft.by.crittercismapi.data.remote.request.GraphRequestInternal;

/**
 * Created by Евгений on 25.07.2014.
 */
public class GraphResponse
{
	private GraphRequestInternal params;
	private SeriesCollection data;

	public GraphRequestInternal getParams()
	{
		return params;
	}

	public void setParams(GraphRequestInternal params)
	{
		this.params = params;
	}

	public SeriesCollection getData()
	{
		return data;
	}

	public void setData(SeriesCollection data)
	{
		this.data = data;
	}
}
