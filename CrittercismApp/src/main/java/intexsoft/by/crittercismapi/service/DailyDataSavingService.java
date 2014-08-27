package intexsoft.by.crittercismapi.service;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import intexsoft.by.crittercismapi.receiver.TimeForLoadDailyDataReceiver;

/**
 * Created by anastasya.konovalova on 26.08.2014.
 */
public class DailyDataSavingService extends Service
{
	private TimeForLoadDailyDataReceiver broadcastTimeForLoadDailyDataReceiver;

	public void onCreate() {
		super.onCreate();

		broadcastTimeForLoadDailyDataReceiver = new TimeForLoadDailyDataReceiver();
		registerReceiver(broadcastTimeForLoadDailyDataReceiver, new IntentFilter("by.crittercismapi.alarm"));
	}

	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	public void onDestroy() {
		super.onDestroy();
	}

	public IBinder onBind(Intent intent) {
		return null;
	}
}

