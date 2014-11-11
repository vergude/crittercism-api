package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.widget.ListView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.db.TimeStatisticContainer;
import intexsoft.by.crittercismapi.data.loader.CommonStatisticsLoader;
import intexsoft.by.crittercismapi.ui.adapters.FastStatisticAdapter;
import intexsoft.by.crittercismapi.ui.view.FastStatisticView;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

/**
 * Created by Евгений on 27.08.2014.
 */
@EFragment(R.layout.fast_statistic_fragment)
public class FastStatisticFragment extends Fragment implements FastStatisticView, LoaderManager.LoaderCallbacks<List<TimeStatisticContainer>>
{
	public static final String TAG = MainFragment.class.getSimpleName();

	@ViewById
	ListView fastStatisticList;

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
	public Loader<List<TimeStatisticContainer>> onCreateLoader(int i, Bundle bundle)
	{
		return new CommonStatisticsLoader(getActivity());
	}

	@Override
	public void onLoadFinished(Loader<List<TimeStatisticContainer>> loader, List<TimeStatisticContainer> data)
	{
		FastStatisticAdapter fastStatisticAdapter = new FastStatisticAdapter(getActivity(),data);
		fastStatisticList.setAdapter(fastStatisticAdapter);
	}

	@Override
	public void onLoaderReset(Loader<List<TimeStatisticContainer>> loader)
	{

	}

}
