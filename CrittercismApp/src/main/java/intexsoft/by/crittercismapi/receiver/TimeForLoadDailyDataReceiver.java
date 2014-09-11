package intexsoft.by.crittercismapi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import intexsoft.by.crittercismapi.service.ErrorGraphService;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.settings.SettingsFacade_;

import java.util.Date;

/**
 * Created by Евгений on 29.07.2014.
 */
public class TimeForLoadDailyDataReceiver extends BroadcastReceiver
{

	private static final String LOG_KEY = "Alarm";

	SettingsFacade settingsFacade;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		settingsFacade = SettingsFacade_.getInstance_(context);

		Log.d(LOG_KEY, "onReceive: " + (new Date(System.currentTimeMillis())).toString());

		ErrorGraphService.getAndSaveDailyStatistics();

		Log.d(LOG_KEY, "Data loaded: " + (new Date()).toString());

		settingsFacade.saveLastSavingDate(System.currentTimeMillis());

		Log.d("Alarm", "Data loaded: " + (new Date()).toString());
	}
}
