package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.presenter.LoginPresenter;
import intexsoft.by.crittercismapi.ui.presenter.LoginPresenterImpl;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EActivity(R.layout.activity_main)
public class MainActivity extends Activity
{
	@Bean(LoginPresenterImpl.class)
	LoginPresenter loginPresenter;

	@Override
	public void onStart()
	{
		super.onStart();
		loginPresenter.onStart();

	}

	@Override
	public void onStop()
	{
		super.onStop();
		loginPresenter.onStop();
	}

	@AfterViews
	void initViews()
	{
		loginPresenter.init();
	}

	@Click(R.id.login_button)
	void onLoginClick()
	{

	}



}
