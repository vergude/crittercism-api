package intexsoft.by.crittercismapi.ui.activity;


import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.manager.LoginManager;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;


/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EActivity
@OptionsMenu(R.menu.menu)
public class MainActivity extends NavigationActivity
{
	@Bean
	LoginManager loginManager;

	@AfterViews
	void initViews()
	{

	}

	@OptionsItem(R.id.logout)
	void logoutSelect()
	{
		loginManager.clearExpireDate();
		Launcher.showLoginActivity(this, true);
		this.finish();
	}
}
