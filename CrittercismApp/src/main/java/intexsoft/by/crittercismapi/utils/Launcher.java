package intexsoft.by.crittercismapi.utils;

import android.content.Context;
import intexsoft.by.crittercismapi.ui.activity.LoginActivity_;
import intexsoft.by.crittercismapi.ui.activity.MainActivity_;

public class Launcher
{
	public static final String TAG = Launcher.class.getSimpleName();

	private Launcher()
	{
	}

	public static void showMainActivity(Context context)
	{
		MainActivity_.intent(context).start();
	}

    public  static void showLoginActivity(Context context,boolean isFromLogout)
    {
        LoginActivity_.intent(context).isFromLogout(isFromLogout).start();
    }
}
