package intexsoft.by.crittercismapi.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.activity.AppDetailsErrorActivity_;
import intexsoft.by.crittercismapi.ui.activity.LoginActivity_;
import intexsoft.by.crittercismapi.ui.activity.MainActivity_;

public final class Launcher
{
	public static final String TAG = Launcher.class.getSimpleName();

	private Launcher()
	{
	}

	public static void showMainActivity(Context context)
	{
		MainActivity_.intent(context).start();
	}

	public static void showAppDetailsErrorActivity(Context context, String appId, String appName)
	{
		View fadeView = new View(context);
		fadeView.setTag("TAG_FADE_VIEW");

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
		fadeView.setLayoutParams(layoutParams);
		fadeView.setBackgroundColor(0x55000000);

		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(200);

		fadeView.startAnimation(animation);
		ViewGroup relativeLayout = (ViewGroup) ((Activity)context).getWindow().getDecorView();
		relativeLayout.addView(fadeView);
		fadeView.setClickable(true);
		fadeView.bringToFront();

		AppDetailsErrorActivity_.intent(context).appId(appId).appName(appName).start();

		((Activity)context).overridePendingTransition(R.anim.slide_left_in, R.anim.empty_animation);
	}

	public static void showLoginActivity(Context context, boolean isFromLogout)
	{
		LoginActivity_.intent(context).isFromLogout(isFromLogout).start();
	}
}
