package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;

import intexsoft.by.crittercismapi.utils.StringUtils;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.presenter.LoginPresenter;
import intexsoft.by.crittercismapi.ui.presenter.LoginPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.LoginView;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EActivity(R.layout.activity_login)
public class LoginActivity extends Activity implements LoginView
{
	@ViewById(R.id.email)
	EditText loginField;

    @ViewById(R.id.progressbar_conteiner)
    FrameLayout progressBarConteiner;

	@ViewById(R.id.password)
	EditText passwordField;

	@Bean(LoginPresenterImpl.class)
	LoginPresenter loginPresenter;

    @Extra
    public boolean isFromLogout;

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
		loginPresenter.init(this);
	}

	@Click(R.id.login_button)
	void onLoginClick()
	{
		loginPresenter.doLogin(loginField.getText().toString(), passwordField.getText().toString());
	}


	@Override
	public void onFillStoredFields(String login, String password)
	{
		if (StringUtils.isNotEmpty(login))
		{
			loginField.setText(login);
		}
		if (StringUtils.isNotEmpty(password))
		{
			passwordField.setText(password);
		}
	}

    @Override
    public void showProgressBar() {
        progressBarConteiner.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBarConteiner.setVisibility(View.INVISIBLE);
    }

    @Override
    public boolean isFromLogout() {
        return isFromLogout;
    }

    @Override
	public Activity getContainer()
	{
		return this;
	}
}
