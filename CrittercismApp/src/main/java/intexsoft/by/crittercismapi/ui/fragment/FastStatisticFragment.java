package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.loader.CommonStatisticsLoader;
import intexsoft.by.crittercismapi.data.loader.data.CommonStatisticsData;
import intexsoft.by.crittercismapi.ui.view.FastStatisticView;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

/**
 * Created by Евгений on 27.08.2014.
 */
@EFragment(R.layout.fast_statistic)
public class FastStatisticFragment extends Fragment implements FastStatisticView, LoaderManager.LoaderCallbacks<CommonStatisticsData>
{
	public static final String TAG = MainFragment.class.getSimpleName();
	private CommonStatisticsData commonStatisticsData;

	@ViewById
	TextView appNameCrashesCountMonth;

	@ViewById
	TextView appNameCrashesCountAll;

	@ViewById
	TextView appNameCrashesCountNight;

	@ViewById
	TextView appNameLoadLeaderAll;

	@ViewById
	TextView appNameLoadLeaderMonth;

	@ViewById
	TextView appNameLoadLeaderNight;

	@ViewById
	TextView appNameErrorPercentAll;

	@ViewById
	TextView appNameErrorPercentMonth;

	@ViewById
	TextView appNameErrorPercentNight;




	public static FastStatisticFragment build()
	{
		return FastStatisticFragment_.builder().build();
	}

	@Override
	public Activity getContainer()
	{
		return getActivity();
	}

	@Override
	public void onResume()
	{
		super.onResume();
		getLoaderManager().initLoader(0, null, this);
	}

	@Override
	public Loader<CommonStatisticsData> onCreateLoader(int i, Bundle bundle)
	{
		return new CommonStatisticsLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<CommonStatisticsData> commonStatisticsDataLoader, CommonStatisticsData commonStatisticsData)
	{
		appNameCrashesCountMonth.setText(commonStatisticsData.getMostCrashesByMonthAppName());
		appNameCrashesCountAll.setText(commonStatisticsData.getMostCrashesByAllTimeAppName());
		appNameCrashesCountNight.setText(commonStatisticsData.getMostCrashesByNightAppName());

		appNameErrorPercentMonth.setText(commonStatisticsData.getMostErrorByMonthAppName());
		appNameErrorPercentAll.setText(commonStatisticsData.getMostErrorByAllTimeAppName());
		appNameErrorPercentNight.setText(commonStatisticsData.getMostErrorByNightAppName());

		appNameLoadLeaderMonth.setText(commonStatisticsData.getMostDownloadsByMonthAppName());
		appNameLoadLeaderAll.setText(commonStatisticsData.getMostDownloadsByAllTimeAppName());
		appNameLoadLeaderNight.setText(commonStatisticsData.getMostDownloadsByNightAppName());
	}

	@Override
	public void onLoaderReset(Loader<CommonStatisticsData> commonStatisticsDataLoader)
	{

	}
}
