package intexsoft.by.crittercismapi.data.bean.sorting;

import java.util.Comparator;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

/**
 * Created by vadim on 02.08.2014.
 */

public class SortedByErrors implements Comparator<DailyStatisticsItem>
{

    @Override
    public int compare(DailyStatisticsItem dailyStatisticsItemFirst, DailyStatisticsItem dailyStatisticsItemSecond)
    {
        int errorsPercentFirst = dailyStatisticsItemFirst.getErrorsPercent();
        int errorsPercentSecond = dailyStatisticsItemSecond.getErrorsPercent();

        if(errorsPercentFirst > errorsPercentSecond)
        {
            return 1;
        }
        else if(errorsPercentFirst < errorsPercentSecond)
        {
            return -1;
        }
        else
        {
            return 0;
        }
    }
}
