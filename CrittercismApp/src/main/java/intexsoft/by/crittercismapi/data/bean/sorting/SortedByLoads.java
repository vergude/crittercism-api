package intexsoft.by.crittercismapi.data.bean.sorting;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.Comparator;

/**
 * Created by vadim on 02.08.2014.
 */

public class SortedByLoads implements Comparator<DailyStatisticsItem>
{

	@Override
	public int compare(DailyStatisticsItem dailyStatisticsItemFirst, DailyStatisticsItem dailyStatisticsItemSecond)
	{
		int loadsFirst = dailyStatisticsItemFirst.getAppLoadsCount();
		int loadsSecond = dailyStatisticsItemSecond.getAppLoadsCount();

		if (loadsFirst > loadsSecond)
		{
			return 1;
		}
		else if (loadsFirst < loadsSecond)
		{
			return -1;
		}
		else
		{
			return 0;
		}
	}
}
