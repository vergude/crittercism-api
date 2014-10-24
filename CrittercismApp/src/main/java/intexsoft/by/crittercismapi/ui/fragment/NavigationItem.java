package intexsoft.by.crittercismapi.ui.fragment;

import android.content.Context;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.R;

/**
 * Created by eugene.galonsky on 28.03.14.
 */
public enum NavigationItem
{
	NAVIGATION_APPS(R.string.navigate_apps),
	NAVIGATION_STATISTICS(R.string.navigate_statistics),
    NAVIGATION_MONTH_STATISTICS(R.string.month_statistics),
	NAVIGATION_FAST_STATISTICS(R.string.navigate_fast_statistics),
    NAVIGATION_GRAPH_STATISTICS(R.string.graph_statistics),
	NAVIGATION_SETTINGS(R.string.navigate_settings);

	private final int titleResId;

	NavigationItem(int titleResId)
	{
		this.titleResId = titleResId;
	}

	public int getTitleResId()
	{
		return titleResId;
	}

	public String getTitle(Context context)
	{
		return context == null ? null : context.getString(getTitleResId());
	}

	public String getTitle()
	{
		return getTitle(CrittercismApplication.getApplication());
	}
}
