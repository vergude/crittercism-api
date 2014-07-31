package intexsoft.by.crittercismapi.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
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

	public ErrorGraphService()
	{
		super(ErrorGraphService.class.getSimpleName());
	}

	public static void getDailyStatistics()
	{
		Context context = CrittercismApplication.getApplication();
		ErrorGraphService_.intent(context).fetchDailyStatistics().start();
	}

	@Override
	protected void onHandleIntent(Intent intent)
	{
		//Do nothing
	}

	@ServiceAction(Constants.Action.REQUEST_GET_DAILY_STATISTICS)
	protected void fetchDailyStatistics()
	{
		List<DailyStatisticsItem> items = remoteFacade.getErrorGraphAllApps(Constants.DURATION_ONE_DAY);

		DailyStatisticsLoadedEvent event = new DailyStatisticsLoadedEvent();
		event.setDailyStatisticsItems(items);

		Context context = CrittercismApplication.getApplication();
		EventObserver.sendEvent(context, event);
	}

}
