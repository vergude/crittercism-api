package intexsoft.by.crittercismapi.data.bean.sorting;

import java.util.Comparator;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

/**
 * Created by vadim on 02.08.2014.
 */

public class SortedByCrashes implements Comparator<DailyStatisticsItem> 
{

    @Override
    public int compare(DailyStatisticsItem dailyStatisticsItemFirst, DailyStatisticsItem dailyStatisticsItemSecond)
    {
        int crashesFirst = dailyStatisticsItemFirst.getCrashesCount();
        int crashesSecond = dailyStatisticsItemSecond.getCrashesCount();

        if(crashesFirst > crashesSecond)
        {
            return 1;
        }
        else if(crashesFirst < crashesSecond)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
