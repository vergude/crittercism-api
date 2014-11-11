package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import intexsoft.by.crittercismapi.data.CrittercismAPIContentProvider;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.Entity;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.data.db.FastStatisticItem;
import intexsoft.by.crittercismapi.data.db.StatisticType;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import nl.qbusict.cupboard.CupboardFactory;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EBean(scope = EBean.Scope.Singleton)
public class PersistenceFacade
{
    @RootContext
    Context context;

	@Bean
	SettingsFacade settingsFacade;

	@Bean
	DatabaseQueryHelper databaseQueryHelper;

	private static final String COLUMN_ERROR = "CAST (crashes_count AS REAL)/(CAST (app_loads_count AS REAL))";

    public static PersistenceFacade getInstance(Context context)
	{
        return PersistenceFacade_.getInstance_(context);
    }

	public void saveDailyStatisticsItems(List<DailyStatisticsItem> statisticsItems)
	{
		ThreadUtils.checkAndThrowIfUIThread();
		CupboardFactory.cupboard().withContext(context).put(CrittercismAPIContentProvider.DAILY_STATISTIC_URI,
				DailyStatisticsItem.class, statisticsItems);
	}

	public void saveApps(List<CrittercismApp> appList)
	{
		ThreadUtils.checkAndThrowIfUIThread();
		CupboardFactory.cupboard().withContext(context).put(CrittercismAPIContentProvider.CRITTERCISM_APP_URI,
				CrittercismApp.class, appList);
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
		return CupboardFactory.cupboard().withContext(context).query(CrittercismAPIContentProvider.CRITTERCISM_APP_URI,
				CrittercismApp.class).
				withSelection(CrittercismApp.COLUMN_USER_LOGIN + " = ?", new String[]{user}).list();
	}

	private <T extends Entity> Uri save(Uri uri, T toSave)
	{
		return CupboardFactory.cupboard().withContext(context).put(uri, toSave);
	}


	public FastStatisticItem getFastStatisticItem(String time, StatisticType type)
	{
		FastStatisticItem fastStatisticItem = new FastStatisticItem();
		Cursor cursor = databaseQueryHelper.getDailyStatisticsItemSum(DailyStatisticsItem.COLUMN_APP_REMOTE_ID,
				context.getString(type.getColumnName()), time);
		cursor.moveToFirst();
		fastStatisticItem.setCountType(context.getString(type.getStatisticType()));
		fastStatisticItem.setAppName(cursor.getString(cursor.getColumnIndex("name")));
		fastStatisticItem.setCountResult(type.formatStatisticsValue(cursor.getFloat(cursor.getColumnIndex("max_count"))));

		return fastStatisticItem;
	}

}
