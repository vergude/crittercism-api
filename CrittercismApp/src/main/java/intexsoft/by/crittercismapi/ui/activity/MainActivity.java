package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.service.Receiver;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu)
public class MainActivity extends Activity implements MainView
{
	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

    private Receiver broadcastReceiver;
    private PendingIntent pi;
    private AlarmManager am;

	@Bean
	RemoteFacade remoteFacade;

	@Override
	public void onStart()
	{
		super.onStart();
		presenter.onStart();
	}

	@Override
	public void onStop()
	{
		super.onStop();
		presenter.onStop();
        am.cancel(pi);
	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);
		getApps();
        setupAlarm();

        am.setRepeating(AlarmManager.RTC_WAKEUP, 0 , Constants.INTERVAL,pi);
	}

    @OptionsItem(R.id.logout)
    void logoutSelect()
    {
        presenter.logout();
    }

	@Background
	void getApps()
	{
		remoteFacade.getErrorGraphAllApps();
	}

    private void setupAlarm()
    {
        broadcastReceiver = new Receiver();
        registerReceiver(broadcastReceiver,new IntentFilter("by.crittercismapi.alarm"));
        pi = PendingIntent.getBroadcast(this,0,new Intent("by.crittercismapi.alarm"),0);
        am = (AlarmManager) (this.getSystemService(Context.ALARM_SERVICE));
    }

	@Override
	public Activity getActivity()
	{
		return this;
	}
}
