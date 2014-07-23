package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.widget.ListView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.ui.adapters.NavigationDrawerAdapter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu)
public class MainActivity extends Activity implements MainView
{
    @ViewById(R.id.main_left_drawer)
    ListView leftDrawer;

	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

	@Override
	public void onStart()
	{
		super.onStart();
		presenter.onStart();

	}

	@Override
	public void onStop()
	{
		super.onStop();
		presenter.onStop();
	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);
	}

	@AfterViews
    void initDrawer()
    {
        String[] names = getResources().getStringArray(R.array.drawer);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this,names);
        leftDrawer.setAdapter(adapter);
    }

    @ItemClick(R.id.main_left_drawer)
    public void drawerItemClick(int position)
    {
        if(position==1)
        {
            StatisticsActivity_.intent(this).start();
        }
    }

	@OptionsItem(R.id.logout)
    void logoutSelect()
    {
        presenter.logout();
    }


	@Override
	public Activity getActivity()
	{
		return this;
	}


}
