package intexsoft.by.crittercismapi.receiver;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.service.NotificationService;

import java.util.Calendar;
import java.util.List;

/**
 * Created by Евгений on 05.09.2014.
 */
public class NotificationReceiver extends BroadcastReceiver
{
	private List<DailyStatisticsItem> dailyStatisticsItemList;

	private String appNameLeader = "";

	private int crashesCount;

	private static final int NOTIFICATION_ID = 1;

	@Override
	public void onReceive(Context context, Intent intent)
	{
		Log.d("*******OnReceiver*******", "OnReceiverMethod");

		getData(context);
	}

	public static void startAlarmForNotification(Context context)
	{
		Intent intent = new Intent(context, NotificationReceiver.class);

		PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

		long curenttime = System.currentTimeMillis();

		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(curenttime);

		Log.d("CurentTime", calendar.getTime().toString());

		calendar.set(Calendar.HOUR_OF_DAY, 12);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		Log.d("TargetDate", calendar.getTime().toString());

		if (curenttime > calendar.getTimeInMillis())
		{
			calendar.add(Calendar.DATE, 1);
			Log.d("addOneDay", calendar.getTime().toString());
		}
		AlarmManager am = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));
		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pi);

	}


	void getData(final Context context)
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				dailyStatisticsItemList = RemoteFacade.getInstance(context)
						.getErrorGraphAllApps(Constants.DURATION_ONE_DAY);

				loadData(dailyStatisticsItemList);

				showNotification(context);
			}
		});
		t.start();
	}

	void showNotification(Context context)
	{
		Intent notificationIntent = new Intent(context, NotificationService.class);
		PendingIntent contentIntent = PendingIntent.getActivity(context,
				0, notificationIntent,
				PendingIntent.FLAG_CANCEL_CURRENT);

		Notification.Builder builder = new Notification.Builder(context)
				.setContentTitle("Crashes leader:")
				.setContentText(appNameLeader + " Crashes count: " + crashesCount)
				.setTicker("New notification...")
				.setContentIntent(contentIntent)
				.setDefaults(Notification.DEFAULT_SOUND).setAutoCancel(true)
				.setSmallIcon(R.drawable.ic_launcher);

		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
		nm.notify(NOTIFICATION_ID, builder.build());
	}

	void loadData(List<DailyStatisticsItem> dailyStatisticsItems)
	{
		appNameLeader = dailyStatisticsItems.get(0).getApplication().getName();
		crashesCount = dailyStatisticsItems.get(0).getCrashesCount();

		for (int i = 0; i < dailyStatisticsItems.size(); i++)
		{
			if (dailyStatisticsItems.get(i).getCrashesCount() > crashesCount)
			{
				crashesCount = dailyStatisticsItems.get(i).getCrashesCount();
				appNameLeader = dailyStatisticsItems.get(i).getApplication().getName();
			}
		}
	}
}
