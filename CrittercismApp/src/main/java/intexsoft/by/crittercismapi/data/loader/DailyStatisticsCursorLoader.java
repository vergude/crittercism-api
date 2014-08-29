package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper_;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;

import java.util.Date;

/**
 * Created by anastasya.konovalova on 05.08.2014.
 */
public class DailyStatisticsCursorLoader extends CursorLoader
{
	private DatabaseQueryHelper queryHelper;

	private Date date;
    private String orderBy;

	public DailyStatisticsCursorLoader(Context context, Date date, String sortColumnName)
	{
		super(context);

		this.date = date;
        this.orderBy = sortColumnName;
		this.queryHelper = DatabaseQueryHelper_.getInstance_(context);
	}

	@Override
	public Cursor loadInBackground() {

		String startDate = DateTimeUtils.getFormatedStartOfDay(date);
		String endDate = DateTimeUtils.getFormatedEndOfDay(date);

		Cursor cursor = queryHelper.getDailyStatisticsItem(
				null,
				DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ? " ,
				new String[]{startDate, endDate},
                orderBy);

		return cursor;
	}
}
