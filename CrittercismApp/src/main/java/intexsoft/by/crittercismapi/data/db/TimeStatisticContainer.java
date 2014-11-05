package intexsoft.by.crittercismapi.data.db;

import java.util.List;

/**
 * Created by Евгений on 28.10.2014.
 */
public class TimeStatisticContainer
{
	private String timeType;

	private List<FastStatisticItem> fastStatisticItemList;

	public String getTimeType()
	{
		return timeType;
	}

	public void setTimeType(String timeType)
	{
		this.timeType = timeType;
	}

	public List<FastStatisticItem> getFastStatisticItemList()
	{
		return fastStatisticItemList;
	}

	public void setFastStatisticItemList(List<FastStatisticItem> fastStatisticItemList)
	{
		this.fastStatisticItemList = fastStatisticItemList;
	}
}
