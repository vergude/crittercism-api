package intexsoft.by.crittercismapi;

import android.app.Application;
import intexsoft.by.crittercismapi.data.CupboardConfigurator;
import intexsoft.by.crittercismapi.receiver.DeviceRebootReceiver;
import intexsoft.by.crittercismapi.receiver.NotificationReceiver;
import org.androidannotations.annotations.EApplication;

/**
 * Created by user on 25.05.2014.
 */
@EApplication
public class CrittercismApplication extends Application
{
	private static final String CRITTERCISM_APP_ID = "54002e19b573f168ef000005";

    private static Application application;

	static
	{
		CupboardConfigurator.configure();
	}

    @Override
    public void onCreate()
    {
        super.onCreate();
        application = this;

		DeviceRebootReceiver.scheduleAlarms(this);
		NotificationReceiver.startAlarmForNotification(this);

		//Crittercism.initialize(getApplicationContext(), CRITTERCISM_APP_ID);
	}

    public static Application getApplication()
    {
        return application;
    }
}
