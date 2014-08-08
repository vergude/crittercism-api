package intexsoft.by.crittercismapi.manager;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.Calendar;
import java.util.Date;

import intexsoft.by.crittercismapi.settings.SettingsFacade;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.StringUtils;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@EBean(scope = EBean.Scope.Singleton)
public class LoginManager
{
	@Bean
	SettingsFacade settingsFacade;

	@RootContext
	Context context;

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
		settingsFacade.saveExpireDate(getExpireDate(expiredValue));

		//TODO save expiredValue
		Log.d("LoginManager", "Saved");
	}

	private long getExpireDate(int expiredValue)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.SECOND, expiredValue);
		return calendar.getTimeInMillis();
	}

	public void clearExpireDate()
	{
		settingsFacade.saveExpireDate(0);
	}
}
