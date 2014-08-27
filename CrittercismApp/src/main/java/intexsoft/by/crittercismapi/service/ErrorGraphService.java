package intexsoft.by.crittercismapi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.event.AppDetailsLoadedEvent;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.manager.ErrorGraphManager;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EIntentService;
import org.androidannotations.annotations.ServiceAction;

import java.util.List;

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

	@Bean
	ErrorGraphManager errorGraphManager;

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
	}

	@ServiceAction(Constants.Action.REQUEST_GET_APP_DETAILS_ERROR)
	public void fetchAppDetailsError(String appId)
	{
		List<DailyStatisticsItem> appErrorDetailsList = errorGraphManager.getMonthlyStatistics(appId);

		AppDetailsLoadedEvent event = new AppDetailsLoadedEvent();
		event.setDailyStatisticsItems(appErrorDetailsList);

		EventObserver.sendEvent(getContext(), event);
	}

	private static Context getContext()
	{
		return CrittercismApplication.getApplication();
	}

}
