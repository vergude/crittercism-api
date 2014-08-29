package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade_;
import intexsoft.by.crittercismapi.data.loader.data.CommonStatisticsData;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 27.08.2014.
 */
public class CommonStatisticsLoader extends OneTimeLoader<CommonStatisticsData>
{

	private PersistenceFacade persistenceFacade;

	private Date endDate;

	public CommonStatisticsLoader(Context context)
	{
		super(context);
	}

	@Override
	public CommonStatisticsData loadInBackground()
	{
		endDate = new Date();
		persistenceFacade = PersistenceFacade_.getInstance_(getContext());
		CommonStatisticsData commonStatisticsData = new CommonStatisticsData();

		commonStatisticsData.setMostCrashesByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(getStartDate(endDate, -30), endDate, DailyStatisticsItem.COLUMN_CRASHES_COUNT, "count_sum"));
		commonStatisticsData.setMostCrashesByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(DailyStatisticsItem.COLUMN_CRASHES_COUNT, "count_sum"));
		commonStatisticsData.setMostCrashesByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(getStartDate(endDate,-1), endDate, DailyStatisticsItem.COLUMN_CRASHES_COUNT, "count_sum"));

		commonStatisticsData.setMostErrorByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(getStartDate(endDate, -30), endDate, null, DailyStatisticsItem.COLUMN_CRASHES_PERCENT));
		commonStatisticsData.setMostErrorByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(null, DailyStatisticsItem.COLUMN_CRASHES_PERCENT));
		commonStatisticsData.setMostErrorByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(getStartDate(endDate,-1), endDate, null, DailyStatisticsItem.COLUMN_CRASHES_PERCENT));

		commonStatisticsData.setMostDownloadsByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(getStartDate(endDate, -30), endDate, DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, "count_sum"));
		commonStatisticsData.setMostDownloadsByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, "count_sum"));
		commonStatisticsData.setMostDownloadsByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(getStartDate(endDate,-1), endDate, DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, "count_sum"));

		return commonStatisticsData;
	}

	private Date getStartDate(Date endDate, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DAY_OF_MONTH, value);

		return calendar.getTime();
	}
}
