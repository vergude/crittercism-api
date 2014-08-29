package intexsoft.by.crittercismapi.receiver;

/**
 * Created by anastasya.konovalova on 26.08.2014.
 */

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.service.DailyDataSavingService;

import java.util.Calendar;

public class DeviceRebootReceiver extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent i)
	{
		scheduleAlarms(context);
	}

	public static void scheduleAlarms(Context context)
	{
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent("by.crittercismapi.alarm"), 0);
		AlarmManager am = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));

		long currenttime = System.currentTimeMillis();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(currenttime);

		Log.d("Alarm", "Current time from calendar: " + calendar.getTimeInMillis());
		Log.d("Alarm", "Current time system: " + currenttime);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 58);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Log.d("Alarm", "Start time:" + calendar.getTime().toString());

		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), Constants.INTERVAL, pi);
		context.startService(new Intent(context, DailyDataSavingService.class));
	}

}
