package intexsoft.by.crittercismapi;

import android.app.Application;
import intexsoft.by.crittercismapi.data.CupboardConfigurator;
import intexsoft.by.crittercismapi.receiver.DeviceRebootReceiver;
import org.androidannotations.annotations.EApplication;

/**
 * Created by user on 25.05.2014.
 */
@EApplication
public class CrittercismApplication extends Application
{

    private static Application application;

	static {
		CupboardConfigurator.configure();
	}

    @Override
    public void onCreate()
    {
        super.onCreate();
        application = this;

		DeviceRebootReceiver.scheduleAlarms(this);
	}

    public static Application getApplication()
    {
        return application;
    }
}
