package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.view.FastStatisticView;
import org.androidannotations.annotations.EFragment;

/**
 * Created by Евгений on 27.08.2014.
 */
@EFragment(R.layout.fast_statistic)
public class FastStatisticFragment extends Fragment implements FastStatisticView
{
	public static final String TAG = MainFragment.class.getSimpleName();

	public static FastStatisticFragment build()
	{
		return FastStatisticFragment_.builder().build();
	}

	@Override
	public Activity getContainer()
	{
		return null;
	}
}
