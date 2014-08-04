package intexsoft.by.crittercismapi.data.bean.sorting;

import java.util.Comparator;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

/**
 * Created by vadim on 02.08.2014.
 */

public class SortedByName implements Comparator<DailyStatisticsItem>
{

    public int compare(DailyStatisticsItem dailyStatisticsItemFirst, DailyStatisticsItem dailyStatisticsItemSecond)
    {
        String nameFirst = dailyStatisticsItemFirst.getApplication().getName();
        String nameSecond = dailyStatisticsItemSecond.getApplication().getName();

        return nameFirst.compareTo(nameSecond);

    }
}
