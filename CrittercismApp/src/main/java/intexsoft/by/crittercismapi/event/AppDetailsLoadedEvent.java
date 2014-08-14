package intexsoft.by.crittercismapi.event;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.List;

/**
 * Created by Евгений on 05.08.2014.
 */
public class AppDetailsLoadedEvent implements EventObserver.Event
{
	private List<DailyStatisticsItem> dailyStatisticsItems;

	public List<DailyStatisticsItem> getDailyStatisticsItems()
	{
		return dailyStatisticsItems;
	}

	public void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems)
	{
		this.dailyStatisticsItems = dailyStatisticsItems;
	}
}
