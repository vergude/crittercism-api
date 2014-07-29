package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import android.widget.Toast;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.event.LoginPerformedEvent;
import intexsoft.by.crittercismapi.manager.LoginManager;
import intexsoft.by.crittercismapi.service.LoginService;
import intexsoft.by.crittercismapi.ui.view.LoginView;
import intexsoft.by.crittercismapi.utils.Launcher;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
@EBean
public class LoginPresenterImpl implements LoginPresenter
{
    private final EventObserver.Receiver loginReceiver = new EventObserver.Receiver()
    {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
            LoginPerformedEvent loginPerformedEvent = (LoginPerformedEvent) event;
            if(loginPerformedEvent.isSuccessful())
            {
                closeLoginAndShowMain();
            }
            else
            {
                loginView.hide();
                Toast.makeText(context,loginPerformedEvent.getErrorMessage(),Toast.LENGTH_LONG).show();
            }
        }
    };

	private LoginView loginView;

	@Bean
	LoginManager loginManager;

    @Override
    public void init(LoginView loginView)
	{
		this.loginView = loginView;

		if (loginManager.isLoginNotExpired())
		{
			closeLoginAndShowMain();
			return;
		}

		if (loginManager.isLoginExpired() && loginManager.isLoginAndPasswordSaved())
		{
			this.loginView.onFillStoredFields(loginManager.getLogin(), loginManager.getPassword());
		}
    }

	private void closeLoginAndShowMain()
	{
		Launcher.showMainActivity(loginView.getContainer());
		loginView.getContainer().finish();
	}

	@Override
    public void onStart()
    {
        EventObserver.register(getContext(), loginReceiver, LoginPerformedEvent.class);
    }

    @Override
    public void onStop()
    {
        EventObserver.unregister(getContext(), loginReceiver);
    }

    private Context getContext()
    {
        return CrittercismApplication.getApplication().getApplicationContext();
    }

	@Override
	public void doLogin(String login, String password)
	{
        loginView.show();
		LoginService.login(login, password);
	}
}
