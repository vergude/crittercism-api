package intexsoft.by.crittercismapi.utils;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.FrameLayout;

import java.util.Date;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.activity.AppDetailsErrorActivity_;
import intexsoft.by.crittercismapi.ui.activity.GraphActivity_;
import intexsoft.by.crittercismapi.ui.activity.LoginActivity_;
import intexsoft.by.crittercismapi.ui.activity.MainActivity_;
import intexsoft.by.crittercismapi.ui.fragment.StatisticsFragment;

public final class Launcher
{
	public static final String TAG = Launcher.class.getSimpleName();

	private static final int ANIMATION_DURATION = 200;
	private static final int ANIMATION_COLOR = 0x55000000;

	private Launcher()
	{
	}

	public static void showMainActivity(Context context)
	{
		MainActivity_.intent(context).start();
	}

	public static void showAppDetailsErrorActivity(final Context context, final String appId, final String appName)
	{
		View fadeView = new View(context);
		fadeView.setTag("TAG_FADE_VIEW");

		FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT);
		fadeView.setLayoutParams(layoutParams);
		fadeView.setBackgroundColor(ANIMATION_COLOR);

		Animation animation = new AlphaAnimation(0, 1);
		animation.setDuration(ANIMATION_DURATION);
		animation.setAnimationListener(new Animation.AnimationListener()
									   {
										   @Override
										   public void onAnimationStart(Animation animation)
										   {

										   }

										   @Override
										   public void onAnimationEnd(Animation animation)
										   {
											   startAppDetailErrorActitvity(context, appId, appName);
										   }

										   @Override
										   public void onAnimationRepeat(Animation animation)
										   {

										   }
									   });

		fadeView.startAnimation(animation);
		ViewGroup relativeLayout = (ViewGroup) ((Activity)context).getWindow().getDecorView();
		relativeLayout.addView(fadeView);
		fadeView.setClickable(true);
		fadeView.bringToFront();
	}

	public static void startAppDetailErrorActitvity(Context context, String appId, String appName)
	{
		AppDetailsErrorActivity_.intent(context).appId(appId).appName(appName).start();
		((Activity)context).overridePendingTransition(R.anim.slide_left_in, R.anim.empty_animation);
	}

	public static void showLoginActivity(Context context, boolean isFromLogout)
	{
		LoginActivity_.intent(context).isFromLogout(isFromLogout).start();
	}

    public static void startGraphActivity(Context context, String appId, String appName, String selectedColumnName, Date date)
    {
        GraphActivity_.intent(context).appId(appId).appName(appName).selectedColumnName(selectedColumnName).selectedDate(date).start();
        ((Activity)context).overridePendingTransition(R.anim.slide_left_in, R.anim.empty_animation);
    }

}
