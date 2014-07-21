package intexsoft.by.crittercismapi.ui.presenter;

import intexsoft.by.crittercismapi.ui.view.LoginView;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
public interface LoginPresenter extends BasePresenter<LoginView>
{
	void doLogin(String login, String password);
}
