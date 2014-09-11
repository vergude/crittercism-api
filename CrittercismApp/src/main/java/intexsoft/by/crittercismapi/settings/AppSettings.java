package intexsoft.by.crittercismapi.settings;

import org.androidannotations.annotations.sharedpreferences.DefaultBoolean;
import org.androidannotations.annotations.sharedpreferences.DefaultLong;
import org.androidannotations.annotations.sharedpreferences.DefaultString;
import org.androidannotations.annotations.sharedpreferences.SharedPref;

/**
 * Created by anastasya.konovalova on 21.07.14.
 */
@SharedPref(SharedPref.Scope.APPLICATION_DEFAULT)
public interface AppSettings
{
	@DefaultString("")
	String login();

	@DefaultString("")
	String password();

	@DefaultString("")
	String token();

	@DefaultLong(0)
	long expiredDate();

	@DefaultLong(0)
	long lastSavingDate();

	@DefaultBoolean(false)
	boolean autoLogin();
}
