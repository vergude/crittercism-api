package intexsoft.by.crittercismapi.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import intexsoft.by.crittercismapi.service.ErrorGraphService;

import java.util.Date;

/**
 * Created by Евгений on 29.07.2014.
 */
public class TimeForLoadDailyDataReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent)
	{

		Log.d("Alarm", "onReceive: " + (new Date(System.currentTimeMillis())).toString());

		ErrorGraphService.getAndSaveDailyStatistics();

		Log.d("Alarm", "Data loaded: " + (new Date()).toString());
	}
}
