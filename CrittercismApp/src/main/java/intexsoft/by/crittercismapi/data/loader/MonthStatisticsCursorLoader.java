package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;

import java.util.Date;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper_;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;

/**
 * Created by vadim on 04.10.2014.
 */
public class MonthStatisticsCursorLoader extends CursorLoader {

    private DatabaseQueryHelper queryHelper;
    private Date date;
    private String orderBy;

    public MonthStatisticsCursorLoader(Context context, Date date, String sortColumnName)
    {
        super(context);
        this.date = date;
        this.orderBy = sortColumnName;
        this.queryHelper = DatabaseQueryHelper_.getInstance_(context);
    }

    @Override
    public Cursor loadInBackground()
    {
        String startDate = DateTimeUtils.getFormattedStartMonth(date);
        String endDate = DateTimeUtils.getFormattedEndMonth(date);

        Cursor cursor = queryHelper.getDailyMonthStatisticsItem(
                null,
                DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ? ",
                new String[]{startDate, endDate},
                orderBy);

        return cursor;
    }
}
