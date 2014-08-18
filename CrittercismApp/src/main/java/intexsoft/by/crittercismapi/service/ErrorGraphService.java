package intexsoft.by.crittercismapi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.response.SeriesData;
import intexsoft.by.crittercismapi.event.AppDetailsLoadedEvent;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by anastasya.konovalova on 20.06.14.
 */

@EIntentService
public class ErrorGraphService extends IntentService
{
	public static final String TAG = ErrorGraphService.class.getSimpleName();

	@Bean
	RemoteFacade remoteFacade;

	@Bean
	PersistenceFacade persistenceFacade;

	public ErrorGraphService()
	{
		super(ErrorGraphService.class.getSimpleName());
	}

	public static void getTodayStatistics()
	{
		ErrorGraphService_.intent(getContext()).loadTodayStatistics().start();
	}

	public static void getAndSaveDailyStatistics()
	{
		ErrorGraphService_.intent(getContext()).fetchDailyStatistics().start();
	}

	public static void getAppErrorDetails(String appId)
	{
		ErrorGraphService_.intent(getContext()).fetchAppDetailsError(appId).start();
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		//Do nothing
	}

	@ServiceAction(Constants.Action.REQUEST_GET_TODAY_STATISTICS)
	protected void loadTodayStatistics()
	{
		List<DailyStatisticsItem> items = remoteFacade.getErrorGraphAllApps(Constants.DURATION_ONE_DAY);

		DailyStatisticsLoadedEvent event = new DailyStatisticsLoadedEvent();
		event.setDailyStatisticsItems(items);

		Context context = CrittercismApplication.getApplication();
		EventObserver.sendEvent(context, event);
	}

	@ServiceAction(Constants.Action.REQUEST_GET_AND_SAVE_DAILY_STATISTICS)
	protected void fetchDailyStatistics()
	{
		List<DailyStatisticsItem> items = remoteFacade.getErrorGraphAllApps(Constants.DURATION_ONE_DAY);

		persistenceFacade.saveDailyStatisticsItems(items);

		DailyStatisticsLoadedEvent event = new DailyStatisticsLoadedEvent();

		EventObserver.sendEvent(getContext(), event);
	}

	@ServiceAction(Constants.Action.REQUEST_GET_APP_DETAILS_ERROR)
	public void fetchAppDetailsError(String appId)
	{
		LinkedHashMap<Date, DailyStatisticsItem> appErrorDetailsMap = new LinkedHashMap<Date, DailyStatisticsItem>();

		GraphResponse graphResponseItemsAppCrashes = remoteFacade.getErrorGraphOneApp(appId, Constants.GRAPH_CRASHES);
		processCrashes(graphResponseItemsAppCrashes, appErrorDetailsMap, new DailyCrashesItemProcessor());


		GraphResponse graphResponseItemsAppLoads = remoteFacade.getErrorGraphOneApp(appId, Constants.GRAPH_APPLOADS);
		processCrashes(graphResponseItemsAppLoads, appErrorDetailsMap, new DailyLoadsItemProcessor());

		AppDetailsLoadedEvent event = new AppDetailsLoadedEvent();
		event.setDailyStatisticsItems(new ArrayList<DailyStatisticsItem>(appErrorDetailsMap.values()));

		EventObserver.sendEvent(getContext(), event);
	}

	private void processCrashes(GraphResponse graphResponse, LinkedHashMap<Date, DailyStatisticsItem> appErrorDetailsMap, DailyItemProcessor dailyItemProcessor)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		SeriesData[] seriesDataC = graphResponse.getData().getSeries();
		Integer[] crashesCountC = seriesDataC[0].getPoints();

		try
		{
			Date startDate = dateFormat.parse(graphResponse.getData().getStart());
			Date endDate = dateFormat.parse(graphResponse.getData().getEnd());
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(startDate);
			int step = 0;

			do
			{
				dailyItemProcessor.processItem(appErrorDetailsMap, setTime(graphResponse, calendar), crashesCountC[step]);
				step++;
			}
			while (calendar.getTimeInMillis() < endDate.getTime());

		}
		catch (ParseException e)
		{
				e.printStackTrace();
		}
	}

	Date setTime(GraphResponse graphResponse, Calendar calendar)
	{
		calendar.add(Calendar.SECOND, graphResponse.getData().getInterval());
		Date date = new Date();
		date.setTime(calendar.getTimeInMillis());
		return date;
	}

	private static Context getContext()
	{
		return CrittercismApplication.getApplication();
	}



	interface DailyItemProcessor
	{
		void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count);
	}

	class DailyCrashesItemProcessor implements DailyItemProcessor
	{
		@Override
		public void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count)
		{
			DailyStatisticsItem dailyStatisticsItem = new DailyStatisticsItem();
			dailyStatisticsItem.setCrashesCount(count);
			dailyStatisticsItem.setDate(date);

			dataDailyStatisticsItemMap.put(date, dailyStatisticsItem);
		}
	}

	class DailyLoadsItemProcessor implements DailyItemProcessor
	{

		@Override
		public void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count)
		{
			if (!dataDailyStatisticsItemMap.containsKey(date))
			{
				DailyStatisticsItem dailyStatisticsItem = new DailyStatisticsItem();
				dailyStatisticsItem.setAppLoadsCount(count);
				dataDailyStatisticsItemMap.put(date, dailyStatisticsItem);
			}
			else
			{
				dataDailyStatisticsItemMap.get(date).setAppLoadsCount(count);
			}
		}
	}

}
