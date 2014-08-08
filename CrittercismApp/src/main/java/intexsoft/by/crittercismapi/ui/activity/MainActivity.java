package intexsoft.by.crittercismapi.ui.activity;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.manager.LoginManager;
import intexsoft.by.crittercismapi.service.Receiver;
import intexsoft.by.crittercismapi.utils.Launcher;


/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EActivity
@OptionsMenu(R.menu.menu)
public class MainActivity extends NavigationActivity
{
	@Bean
	LoginManager loginManager;

	private Receiver broadcastReceiver;
	private PendingIntent pi;
	private AlarmManager am;

	@AfterViews
	void initViews()
	{
		setupAlarm();
		am.setRepeating(AlarmManager.RTC_WAKEUP, 0, Constants.INTERVAL, pi);
	}

	@OptionsItem(R.id.logout)
	void logoutSelect()
	{
		loginManager.clearExpireDate();
		Launcher.showLoginActivity(this, true);
		this.finish();
	}

	private void setupAlarm()
	{
		broadcastReceiver = new Receiver();
		pi = PendingIntent.getBroadcast(this, 0, new Intent("by.crittercismapi.alarm"), 0);
		am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
	}
}
