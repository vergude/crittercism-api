package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.AppDetailsErrorView;

/**
 * Created by Евгений on 04.08.2014.
 */
@EActivity(R.layout.activity_statistics)
public class AppDetailsErrorActivity extends Activity implements AppDetailsErrorView
{
	@Bean(AppDetailsErrorPresenterImpl.class)
	AppDetailsErrorPresenter appDetailsErrorPresenter;

	private GraphResponse appErrorDetails;

	@Extra
	String appId;


	@Override
	protected void onStart()
	{
		super.onStart();
		appDetailsErrorPresenter.onStart();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		appDetailsErrorPresenter.onStop();
	}

	@AfterViews
	void init()
	{
		appDetailsErrorPresenter.init(this);
	}

	@Override
	public Activity getContainer()
	{
		return this;
	}


	@Override
	public String getAppId()
	{
		return appId;
	}
}
