package intexsoft.by.crittercismapi.settings;

import android.content.Context;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Author: sergey.shostko
 * Date: 07.04.2014
 */
@EBean(scope = EBean.Scope.Singleton)
public class SettingsFacade
{
	@Pref
	protected AppSettings_ appSettings;

	public static SettingsFacade getInstance(Context context)
	{
		return SettingsFacade_.getInstance_(context);
	}

	// APP SETTINGS

	public String getLogin()
	{
		return appSettings.login().get();
	}

	public void saveLogin(String login)
	{
		appSettings.edit().login().put(login).apply();
	}

	public String getPassword()
	{
		return appSettings.password().get();
	}

	public void savePassword(String password)
	{
		appSettings.edit().password().put(password).apply();
	}

	public String getToken()
	{
		return appSettings.token().get();
	}

	public void saveToken(String token)
	{
		appSettings.edit().token().put(token).apply();
	}

	public long getExpireDate()
	{
		return appSettings.expiredDate().get();
	}

	public void saveExpireDate(long date)
	{
		appSettings.edit().expiredDate().put(date).apply();
	}

	public boolean getAutoLogin()
	{
		return appSettings.autoLogin().get();
	}

}
