package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import intexsoft.by.crittercismapi.data.CrittercismAPIContentProvider;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.Entity;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import nl.qbusict.cupboard.CupboardFactory;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;


@EBean(scope = EBean.Scope.Singleton)
public class PersistenceFacade
{
    @RootContext
    Context context;

	@Bean
	SettingsFacade settingsFacade;

	@Bean
	DatabaseQueryHelper databaseQueryHelper;

    public static PersistenceFacade getInstance(Context context) {
        return PersistenceFacade_.getInstance_(context);
    }

	public void saveDailyStatisticsItems(List<DailyStatisticsItem> statisticsItems) {
		ThreadUtils.checkAndThrowIfUIThread();
		CupboardFactory.cupboard().withContext(context).put(CrittercismAPIContentProvider.DAILY_STATISTIC_URI, DailyStatisticsItem.class, statisticsItems);
	}

	public void saveApps(List<CrittercismApp> appList) {
		ThreadUtils.checkAndThrowIfUIThread();
		CupboardFactory.cupboard().withContext(context).put(CrittercismAPIContentProvider.CRITTERCISM_APP_URI, CrittercismApp.class, appList);
	}

	@Nullable
	public List<CrittercismApp> getAppsByCurrentUser()
	{
		return getAppsByUser(settingsFacade.getLogin());
	}

	@NotNull
	public Map<String, CrittercismApp> getAppsMapByCurrentUser()
	{
		List<CrittercismApp> apps = getAppsByCurrentUser();

		if (apps == null || apps.size() == 0)
		{
			return Collections.EMPTY_MAP;
		}

		Map<String, CrittercismApp> map = new HashMap<String, CrittercismApp>();

		for (CrittercismApp app : apps)
		{
			map.put(app.getRemoteId(), app);
		}

		return map;
	}

	@Nullable
	public List<CrittercismApp> getAppsByUser(String user)
	{
		return CupboardFactory.cupboard().withContext(context).query(CrittercismAPIContentProvider.CRITTERCISM_APP_URI, CrittercismApp.class).
				withSelection(CrittercismApp.COLUMN_USER_LOGIN + " = ?", new String[]{user}).list();
	}

	private <T extends Entity> Uri save(Uri uri, T toSave) {
		return CupboardFactory.cupboard().withContext(context).put(uri, toSave);
	}

	public String getMaxCrashesAppNameMonth(Date startDate, Date endDate, String columnName, String valueSum)
{

	String stringStartDate = DateTimeUtils.getFormatedStartOfDay(startDate);
	String stringEndDate = DateTimeUtils.getFormatedStartOfDay(endDate);
	double maxCrashes;
	String appName = "";

	Cursor cursor = databaseQueryHelper.getDailyStatisticsItemSum(null,
			DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ?" ,
			new String[]{stringStartDate, stringEndDate}, DailyStatisticsItem.COLUMN_APP_REMOTE_ID, columnName);

	if(cursor != null)
	{
		cursor.moveToFirst();
		maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
		appName = appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
		while(cursor.moveToNext())
		{
			if(cursor.getDouble(cursor.getColumnIndex(valueSum)) >  maxCrashes)
			{
				appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
				maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
			}
		}
	}
	return appName;
}

	public String getMaxCrashesAppNameAllTime(String columnName, String valueSum)
	{
		double maxCrashes;
		String appName = "";

		Cursor cursor = databaseQueryHelper.getDailyStatisticsItemSum(null,null,
				null, DailyStatisticsItem.COLUMN_APP_REMOTE_ID,  columnName);

		if(cursor != null)
		{
			cursor.moveToFirst();
			maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
			appName = appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
			while(cursor.moveToNext())
			{
				if(cursor.getDouble(cursor.getColumnIndex(valueSum)) >  maxCrashes)
				{
					appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
					maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
				}
			}
		}
		return appName;
	}

	public String getMaxCrashesAppNameNight(Date startDate, Date endDate,String columnName, String valueSum)
	{

		String stringStartDate = DateTimeUtils.getFormatedStartOfDay(startDate);
		String stringEndDate = DateTimeUtils.getFormatedStartOfDay(endDate);
		double maxCrashes = 0;
		String appName = "";

		Cursor cursor = databaseQueryHelper.getDailyStatisticsItemSum(null,
				DailyStatisticsItem.COLUMN_DATE + " >= ? and " + DailyStatisticsItem.COLUMN_DATE + " < ?" ,
				new String[]{stringStartDate, stringEndDate}, DailyStatisticsItem.COLUMN_APP_REMOTE_ID, columnName);

		if(cursor != null)
		{
			cursor.moveToFirst();
			maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
			appName = appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
			while(cursor.moveToNext())
			{
				if(cursor.getDouble(cursor.getColumnIndex(valueSum)) >  maxCrashes)
				{
					appName = cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME));
					maxCrashes = cursor.getDouble(cursor.getColumnIndex(valueSum));
				}
			}
		}
		Log.d("*****************", String.format("%.3f", maxCrashes));
		return appName;
	}

}
