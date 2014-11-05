package intexsoft.by.crittercismapi.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import nl.qbusict.cupboard.CupboardFactory;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dmitry.lomako on 09.07.2014.
 */

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseQueryHelper
{

	@RootContext
	Context context;

	private static final String TABLE_DB = "DailyStatisticsItem as DS inner join CrittercismApp as CA on DS.app_remote_id = CA.remote_id ";

	private CrittercismAppSqliteOpenHelper getHelper()
	{
		return CrittercismAppSqliteOpenHelper.getInstance(context);
	}

	private SQLiteDatabase getReadableDb()
	{
		return getHelper().getReadableDatabase();
	}

	private SQLiteDatabase getReadWriteDb()
	{
		return getHelper().getWritableDatabase();
	}

	@Nullable
	public Cursor getDailyStatisticsItem(String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		try
		{
			String [] columns = {"DS._id as _id", "DS.crashes_count as crashes_count", "DS.app_loads_count as app_loads_count",
					"DS.date as date, CA.name as name", "DS.app_remote_id as app_remote_id",
					"CAST (crashes_count AS REAL)/(CAST (app_loads_count AS REAL)) as crashes_percent", };

			Cursor result = getReadableDb().query(TABLE_DB, columns, selection, selectionArgs, null, null, sortOrder);
			return result;
		}
		catch (SQLException e)
		{
			handleException(e);
			return null;
		}
	}


	@Nullable
	public Cursor getDailyStatisticsItemSum(String groupBy, String column, String time)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		try
		{
			Cursor result = getReadableDb().rawQuery("SELECT MAX(count_sum) as max_count, name FROM (SELECT  DS.app_loads_count as app_loads_count, " +
													"CA.name as name, " +
													"DS.date as date, " +
													"SUM("+ column +") as count_sum " +
													"FROM DailyStatisticsItem AS DS INNER JOIN CrittercismApp AS CA ON " +
													"DS.app_remote_id = CA.remote_id WHERE date >= ? GROUP BY app_remote_id)", new String[] {time});
			return result;
		}
		catch (SQLException e)
		{
			handleException(e);
			return null;
		}
	}

	private void handleException(SQLException e)
	{
		Log.e("DatabaseQueryHelper", e.getMessage());
	}

	public long save(Class<?> clazz, ContentValues values)
	{
		ThreadUtils.checkAndThrowIfUIThread();
		try
		{
			return CupboardFactory.cupboard().withDatabase(getReadWriteDb()).put(clazz, values);
		}
		catch (SQLException e)
		{
			handleException(e);
			return -1;
		}
	}

	@Nullable
	public Cursor getCursor(Class className, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		try
		{
			Cursor result = CupboardFactory.cupboard().withDatabase(getReadableDb()).query(className).
					withProjection(projection).withSelection(selection, selectionArgs).
					orderBy(sortOrder).getCursor();
			return result;
		}
		catch (SQLException e)
		{
			handleException(e);
			return null;
		}
	}
}
