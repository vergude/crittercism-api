package intexsoft.by.crittercismapi.receiver;

/**
 * Created by anastasya.konovalova on 26.08.2014.
 */
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.service.DailyDataSavingService;

public class DeviceRebootReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent i) {
		scheduleAlarms(context);
	}

	public static void scheduleAlarms(Context context) {
		PendingIntent pi = PendingIntent.getBroadcast(context, 0, new Intent("by.crittercismapi.alarm"), 0);
		AlarmManager am = (AlarmManager) (context.getSystemService(Context.ALARM_SERVICE));

		am.setRepeating(AlarmManager.ELAPSED_REALTIME, 0 , Constants.INTERVAL, pi);
		context.startService(new Intent(context, DailyDataSavingService.class));
	}

}
