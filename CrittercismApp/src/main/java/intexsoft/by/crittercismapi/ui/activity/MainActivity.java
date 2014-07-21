package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.BaseView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity implements BaseView
{
	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

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

		getApps();
	}

	@Background
	void getApps()
	{
		remoteFacade.getApps();
	}


	@Override
	public Activity getActivity()
	{
		return this;
	}
}
