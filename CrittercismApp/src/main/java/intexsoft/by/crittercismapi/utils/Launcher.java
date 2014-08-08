package intexsoft.by.crittercismapi.utils;

import android.content.Context;
import intexsoft.by.crittercismapi.ui.activity.LoginActivity_;
import intexsoft.by.crittercismapi.ui.activity.MainActivity_;
import intexsoft.by.crittercismapi.ui.activity.AppDetailsErrorActivity_;

public final class Launcher
{
	public static final String TAG = Launcher.class.getSimpleName();

	private Launcher()
	{
	}

	public static void showMainActivity(Context context)
	{
		MainActivity_.intent(context).start();
//		Intent intent = new Intent(context, MainActivity.class);
//		context.startActivity(intent);
	}

	public static void showAppDetailsErrorActivity(Context context, String appId)
	{
		AppDetailsErrorActivity_.intent(context).appId(appId).start();
	}

	public static void showLoginActivity(Context context, boolean isFromLogout)
	{
		LoginActivity_.intent(context).isFromLogout(isFromLogout).start();
	}
}
