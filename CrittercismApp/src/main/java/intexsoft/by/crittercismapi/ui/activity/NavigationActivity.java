package intexsoft.by.crittercismapi.ui.activity;

import android.app.Fragment;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.fragment.FastStatisticFragment;
import intexsoft.by.crittercismapi.ui.fragment.MainFragment;
import intexsoft.by.crittercismapi.ui.fragment.NavigationItem;
import intexsoft.by.crittercismapi.ui.fragment.StatisticsFragment;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */
public class NavigationActivity extends BaseNavigationActivity
{
	NavigationItem currentItem;

	private void showAppsFragment()
	{
		currentItem = NavigationItem.NAVIGATION_APPS;
		Fragment fragment = getContentFragment();
		if (fragment == null || !(fragment instanceof MainFragment))
		{
			replaceContentFragment(MainFragment.build(), MainFragment.TAG);
		}
	}

	private void showStatisticsFragment()
	{
		currentItem = NavigationItem.NAVIGATION_STATISTICS;
		Fragment fragment = getContentFragment();
		if (fragment == null || !(fragment instanceof StatisticsFragment))
		{
			replaceContentFragment(StatisticsFragment.build(), StatisticsFragment.TAG);
		}
	}

	private  void showFastStatisticFragment()
	{
		currentItem = NavigationItem.NAVIGATION_FAST_STATISTICS;
		Fragment fragment = getContentFragment();
		if (fragment == null || !(fragment instanceof FastStatisticFragment))
		{
			replaceContentFragment(FastStatisticFragment.build(), FastStatisticFragment.TAG);
		}
	}

	private Fragment getContentFragment()
	{
		return getFragmentManager().findFragmentById(R.id.content_frame);
	}

	private void replaceContentFragment(Fragment fragment, String tag)
	{
		// Insert the fragment by replacing any existing fragment
		getFragmentManager().beginTransaction()
				.replace(R.id.content_frame, fragment, tag)
				.commit();
		overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
	}

	private void updateTitle()
	{
		setTitle(currentItem != null && !isDrawerOpen() ? currentItem.getTitleResId() : R.string.app_name);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		updateTitle();
		//do not show menu when drawer is opened
		if (!isDrawerOpen())
		{
			return super.onCreateOptionsMenu(menu);
		}
		else
		{
			return false;
		}
	}

	@Override
	public void onPerformNavigation(NavigationItem item)
	{
		switch (item)
		{
			case NAVIGATION_APPS:
				showAppsFragment();
				break;
			case NAVIGATION_STATISTICS:
				showStatisticsFragment();
				break;
			case NAVIGATION_FAST_STATISTICS:
				showFastStatisticFragment();
				break;
			case NAVIGATION_SETTINGS:
				Intent intent = new Intent(this, SettingsActivity.class);
				startActivity(intent);
				break;
			default:
				break;
		}
		updateTitle();
	}

	@Override
	protected void onStart()
	{
		super.onStart();
	}

	@Override
	protected void onResume()
	{
		ViewGroup relativeLayout = (ViewGroup) this.getWindow().getDecorView();
		View fadeView =  relativeLayout.findViewWithTag("TAG_FADE_VIEW");

		if (fadeView != null)
		{
			Animation animation = new AlphaAnimation(1, 0);
			animation.setDuration(1000);
			fadeView.startAnimation(animation);

			relativeLayout.removeView(fadeView);
		}

		super.onResume();
	}
}
