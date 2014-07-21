package intexsoft.by.crittercismapi.manager;

import android.util.Log;
import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.StringUtils;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@EBean
public class LoginManager
{
	@Bean
	SettingsFacade settingsFacade;

	public boolean isLoginNotExpired()
	{
		return settingsFacade.getExpireDate() > DateTimeUtils.getCurrentDateLong();
	}

	public boolean isLoginExpired()
	{
		return settingsFacade.getExpireDate() <= DateTimeUtils.getCurrentDateLong();
	}

	public boolean isLoginAndPasswordSaved()
	{
		return StringUtils.isNotEmpty(settingsFacade.getLogin()) && StringUtils.isNotEmpty(settingsFacade.getPassword());
	}

	public String getLogin()
	{
		return settingsFacade.getLogin();
	}

	public String getPassword()
	{
		return settingsFacade.getPassword();
	}

	public void saveLoginData(String login, String password, String token, int expiredValue)
	{
		settingsFacade.saveLogin(login);
		settingsFacade.savePassword(password);
		settingsFacade.saveToken(token);

		Log.d("LoginManager", "Saved");

	}
}
