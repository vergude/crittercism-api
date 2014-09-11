package intexsoft.by.crittercismapi.receiver;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import intexsoft.by.crittercismapi.service.NotificationService;

import java.util.Calendar;

/**
 * Created by Евгений on 05.09.2014.
 */
public class NotificationReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive(Context context, Intent intent)
	{
		context.startService(new Intent(context, NotificationService.class));
	}

	public static void startnAlarmForNotification(Context context)
	{
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent("by.crittercismapi.notification"), 0);
		AlarmManager am = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));

		long curenttime = System.currentTimeMillis();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(curenttime);

		Log.d("CurentTime", calendar.getTime().toString());

		calendar.set(Calendar.HOUR_OF_DAY, 16);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Log.d("TargetDate", calendar.getTime().toString());

		if (curenttime > calendar.getTimeInMillis())
		{
			calendar.add(Calendar.DATE, 1);
			Log.d("addOneDay", calendar.getTime().toString());
		}

		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), 24 * 60 * 60 * 1000, pi);
	}
}
