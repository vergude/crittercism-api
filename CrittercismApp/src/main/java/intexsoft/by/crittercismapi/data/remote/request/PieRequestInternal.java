package intexsoft.by.crittercismapi.data.remote.request;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Created by Евгений on 29.07.2014.
 */
public class PieRequestInternal extends GraphRequestInternal
{
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private String groupBy;

	public String getGroupBy()
	{
		return groupBy;
	}

	public void setGroupBy(String groupBy)
	{
		this.groupBy = groupBy;
	}


}
