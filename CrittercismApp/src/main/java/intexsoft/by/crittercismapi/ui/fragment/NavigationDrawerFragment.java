package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;

/**
 * Created by eugene.galonsky on 27.03.14.
 */
@EFragment(R.layout.fragment_navigation_drawer)
public class NavigationDrawerFragment extends Fragment
{
	@ViewById(R.id.item_apps)
	TextView catalogsItem;

	@ViewById(R.id.item_statistics)
	TextView bookmarksItem;

    @ViewById(R.id.item_month_statistics)
    TextView mouthStatistics;

	@ViewById(R.id.item_fast_statistics)
	TextView fastStatistic;

    @ViewById(R.id.item_graph_statistics)
    TextView graphStatistics;

	@ViewById(R.id.item_settings)
	View settingsItem;

	@InstanceState
	NavigationItem currentItem = NavigationItem.NAVIGATION_APPS;

	private NavigationListener navigationListener;
	private NavigationDrawerCloser drawerCloser;

	@AfterViews
	void init()
	{
		if (currentItem != null)
		{
			selectNavigationItem(currentItem);
			performNavigation(currentItem);
		}
	}

	@Override
	public void onAttach(Activity activity)
	{
		super.onAttach(activity);

		//Init listeners.
		if (activity instanceof NavigationDrawerCloser)
		{
			drawerCloser = (NavigationDrawerCloser) activity;
		}
		if (activity instanceof NavigationListener)
		{
			navigationListener = (NavigationListener) activity;
		}
	}

	@Override
	public void onDetach()
	{
		//Remove all listeners
		drawerCloser = null;
		navigationListener = null;
		super.onDetach();
	}

	@Click(R.id.item_apps)
	void appsClicked(View v)
	{
		selectNavigationItem(NavigationItem.NAVIGATION_APPS);
		performNavigation(NavigationItem.NAVIGATION_APPS);
		closeDrawer();
	}

	@Click(R.id.item_statistics)
	void statisticsClicked(View v)
	{
		selectNavigationItem(NavigationItem.NAVIGATION_STATISTICS);
		performNavigation(NavigationItem.NAVIGATION_STATISTICS);
		closeDrawer();
	}

    @Click(R.id.item_month_statistics)
    void mouthStatisticsClicked()
    {
        selectNavigationItem(NavigationItem.NAVIGATION_MONTH_STATISTICS);
        performNavigation(NavigationItem.NAVIGATION_MONTH_STATISTICS);
        closeDrawer();
    }

	@Click(R.id.item_fast_statistics)
	void fastStatisticsClicked(View v)
	{
		selectNavigationItem(NavigationItem.NAVIGATION_FAST_STATISTICS);
		performNavigation(NavigationItem.NAVIGATION_FAST_STATISTICS);
		closeDrawer();
	}

    @Click(R.id.item_graph_statistics)
    void graphStatisticsClicked(View v)
    {
        selectNavigationItem(NavigationItem.NAVIGATION_GRAPH_STATISTICS);
        performNavigation(NavigationItem.NAVIGATION_GRAPH_STATISTICS);
        closeDrawer();
    }

	@Click(R.id.item_settings)
	void settingsClicked(View v)
	{
		performNavigation(NavigationItem.NAVIGATION_SETTINGS);
		closeDrawer();
	}


	private void selectNavigationItem(NavigationItem item)
	{
		currentItem = item;
		switch (item)
		{
			case NAVIGATION_APPS:
                catalogsItem.setTypeface(null, Typeface.BOLD);
				bookmarksItem.setTypeface(null, Typeface.NORMAL);
				fastStatistic.setTypeface(null, Typeface.NORMAL);
                mouthStatistics.setTypeface(null, Typeface.NORMAL);
                graphStatistics.setTypeface(null, Typeface.NORMAL);
				break;
			case NAVIGATION_STATISTICS:
				catalogsItem.setTypeface(null, Typeface.NORMAL);
				bookmarksItem.setTypeface(null, Typeface.BOLD);
				fastStatistic.setTypeface(null, Typeface.NORMAL);
                mouthStatistics.setTypeface(null, Typeface.NORMAL);
                graphStatistics.setTypeface(null, Typeface.NORMAL);
				break;
            case NAVIGATION_MONTH_STATISTICS:
                catalogsItem.setTypeface(null, Typeface.NORMAL);
                bookmarksItem.setTypeface(null, Typeface.NORMAL);
                mouthStatistics.setTypeface(null, Typeface.BOLD);
                fastStatistic.setTypeface(null, Typeface.NORMAL);
                graphStatistics.setTypeface(null, Typeface.NORMAL);
                break;
			case NAVIGATION_FAST_STATISTICS:
				catalogsItem.setTypeface(null, Typeface.NORMAL);
				bookmarksItem.setTypeface(null, Typeface.NORMAL);
                mouthStatistics.setTypeface(null, Typeface.NORMAL);
                fastStatistic.setTypeface(null, Typeface.BOLD);
                graphStatistics.setTypeface(null, Typeface.NORMAL);
				break;
            case NAVIGATION_GRAPH_STATISTICS:
                catalogsItem.setTypeface(null, Typeface.NORMAL);
                bookmarksItem.setTypeface(null, Typeface.NORMAL);
                mouthStatistics.setTypeface(null, Typeface.NORMAL);
                fastStatistic.setTypeface(null, Typeface.NORMAL);
                graphStatistics.setTypeface(null, Typeface.BOLD);
                break;
			default:
				break;
		}
	}

	private void performNavigation(NavigationItem item)
	{
		if (navigationListener != null)
		{
			navigationListener.onPerformNavigation(item);
		}
	}

	private void closeDrawer()
	{
		if (drawerCloser != null)
		{
			drawerCloser.closeDrawer();
		}
	}

}
