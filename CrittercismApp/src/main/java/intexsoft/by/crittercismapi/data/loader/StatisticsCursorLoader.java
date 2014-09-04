package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import android.util.Log;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper_;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;

import java.util.Date;

/**
 * Created by Евгений on 27.08.2014.
 */
public class StatisticsCursorLoader extends CursorLoader
{
	private DatabaseQueryHelper queryHelper;

	private Date startDate;
	private Date endDate;
	private String appId;
	private String orderBy;

	public StatisticsCursorLoader(Context context, Date startDate, Date endDate, String sortColumnName, String appId)
	{
		super(context);

		this.startDate = startDate;
		this.endDate = endDate;
		this.appId = appId;
		this.queryHelper = DatabaseQueryHelper_.getInstance_(context);
		this.orderBy = sortColumnName;
	}

	@Override
	public Cursor loadInBackground()
	{
		String stringStartDate = DateTimeUtils.getFormatedStartOfDay(startDate);
		String stringEndDate = DateTimeUtils.getFormatedStartOfDay(endDate);

		Log.d("stringStartDate", stringStartDate);
		Log.d("stringEndDate", (new Date(Long.parseLong(stringEndDate))).toString());


		Cursor cursor = queryHelper.getDailyStatisticsItem(
				null,
				DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ? and "
						+ DailyStatisticsItem.COLUMN_APP_REMOTE_ID + " = ?",
						new String[]{stringStartDate, stringEndDate, appId},
						 orderBy);

		return cursor;
	}
}
