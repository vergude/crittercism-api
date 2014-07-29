package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements MainView
{
	public static final String TAG = MainFragment.class.getSimpleName();

	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

	public static MainFragment build()
	{
		return MainFragment_.builder().build();
	}

	@Override
	public void onStart()
	{
		super.onStart();
		presenter.onStart();

	}

	@Override
	public void onStop()
	{
		super.onStop();
		presenter.onStop();
	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);
	}


	@Override
	public Activity getContainer()
	{
		return getActivity();
	}


}
