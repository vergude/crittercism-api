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
 * Created by vadim on 25.10.2014.
 */

public class GraphStatisticsCursorLoader extends CursorLoader
{
    private DatabaseQueryHelper queryHelper;
    private String appId;
    private String selectedColumnName;
    private Date  date;

    public GraphStatisticsCursorLoader(Context context, String appId, String selectedColumnName, Date date)
    {
        super(context);
        this.appId = appId;
        this.selectedColumnName = selectedColumnName;
        this.date = date;
        this.queryHelper = DatabaseQueryHelper_.getInstance_(context);
    }

    @Override
    public Cursor loadInBackground()
    {

        String startDate = DateTimeUtils.getFormattedStartMonth(date);
        String endDate = DateTimeUtils.getFormattedEndMonth(date);

        Cursor cursor = queryHelper.getDailyGraphStatisticsItem(
                null,
                DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ? and "
                        + DailyStatisticsItem.COLUMN_APP_REMOTE_ID + " = ?",
                new String[]{startDate, endDate, appId},
                selectedColumnName
        );

        return cursor;
    }
}
