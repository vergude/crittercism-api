package intexsoft.by.crittercismapi.data.bean.sorting;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.Comparator;
import java.util.Date;

/**
 * Created by Евгений on 13.08.2014.
 */
public class SortedByDate implements Comparator<DailyStatisticsItem>
{
	@Override
	public int compare(DailyStatisticsItem dailyStatisticsItemFirst, DailyStatisticsItem dailyStatisticsItemSecond)
	{
		Date dateFirst = dailyStatisticsItemFirst.getDate();
		Date dateSecond = dailyStatisticsItemSecond.getDate();

		return dateFirst.compareTo(dateSecond);
	}
}
