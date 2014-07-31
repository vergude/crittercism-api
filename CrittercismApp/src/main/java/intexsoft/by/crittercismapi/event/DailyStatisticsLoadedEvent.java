package intexsoft.by.crittercismapi.event;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.List;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
public class DailyStatisticsLoadedEvent implements EventObserver.Event
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
