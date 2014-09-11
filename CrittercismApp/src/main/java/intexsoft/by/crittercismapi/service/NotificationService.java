package intexsoft.by.crittercismapi.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import org.androidannotations.annotations.EBean;

import java.util.List;

/**
 * Created by Евгений on 05.09.2014.
 */
@EBean
public class NotificationService extends Service
{

	private List<DailyStatisticsItem> dailyStatisticsItemList;

	private String appNameLeader = "";

	private int crashesCount;

	private static final int NOTIFICATION_ID = 1;

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onCreate()
	{
		super.onCreate();
		getData();
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}

	void getData()
	{
		Thread t = new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				dailyStatisticsItemList = RemoteFacade.getInstance(getApplicationContext())
						.getErrorGraphAllApps(Constants.DURATION_ONE_DAY);

				loadData(dailyStatisticsItemList);

				showNotification();
			}
		});
		t.start();
	}

	void showNotification()
	{
		Context context = getApplicationContext();
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

		NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
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
