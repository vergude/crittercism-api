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
	private static final String COLUMN_SUM = "count_sum";
	private static final int ONE_MONTH_DAYS = -30;
	private PersistenceFacade persistenceFacade;

	private Date endDate;

	public CommonStatisticsLoader(Context context)
	{
		super(context);
	}

	@Override
	public CommonStatisticsData loadInBackground()
	{
		persistenceFacade = PersistenceFacade_.getInstance_(getContext());

		endDate = new Date();

		CommonStatisticsData commonStatisticsData = new CommonStatisticsData();

		commonStatisticsData.setMostCrashesByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(getStartDate(endDate, ONE_MONTH_DAYS),
				endDate, DailyStatisticsItem.COLUMN_CRASHES_COUNT, COLUMN_SUM));

		commonStatisticsData.setMostCrashesByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(
				DailyStatisticsItem.COLUMN_CRASHES_COUNT, COLUMN_SUM));

		commonStatisticsData.setMostCrashesByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(getStartDate(endDate, -1),
				endDate, DailyStatisticsItem.COLUMN_CRASHES_COUNT, COLUMN_SUM));

		commonStatisticsData.setMostErrorByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(getStartDate(endDate, ONE_MONTH_DAYS),
				endDate, null, DailyStatisticsItem.COLUMN_CRASHES_PERCENT));

		commonStatisticsData.setMostErrorByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(null,
				DailyStatisticsItem.COLUMN_CRASHES_PERCENT));

		commonStatisticsData.setMostErrorByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(
				getStartDate(endDate, -1), endDate, null, DailyStatisticsItem.COLUMN_CRASHES_PERCENT));

		commonStatisticsData.setMostDownloadsByMonthAppName(persistenceFacade.getMaxCrashesAppNameMonth(
				getStartDate(endDate, ONE_MONTH_DAYS), endDate, DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, COLUMN_SUM));

		commonStatisticsData.setMostDownloadsByAllTimeAppName(persistenceFacade.getMaxCrashesAppNameAllTime(
				DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, COLUMN_SUM));

		commonStatisticsData.setMostDownloadsByNightAppName(persistenceFacade.getMaxCrashesAppNameNight(getStartDate(endDate, -1),
				endDate, DailyStatisticsItem.COLUMN_APP_LOADS_COUNT, COLUMN_SUM));

		return commonStatisticsData;
	}

	private Date getStartDate(Date endDat, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDat);
		calendar.add(Calendar.DAY_OF_MONTH, value);

		return calendar.getTime();
	}
}
