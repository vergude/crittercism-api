package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.DialogFragment;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.data.remote.response.AppData;
import intexsoft.by.crittercismapi.ui.adapters.AppInfoAdapter;
import intexsoft.by.crittercismapi.ui.adapters.NavigationDrawerAdapter;
import intexsoft.by.crittercismapi.ui.dialogs.DatePickerFragment;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EActivity(R.layout.activity_main)
@OptionsMenu(R.menu.menu)
public class MainActivity extends Activity implements MainView,DatePickerFragment.FragmentDatePickerInterface
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d.M.yyyy");
    private Calendar mCalendar = Calendar.getInstance();
    private ArrayList<AppData> appDatas = new ArrayList<AppData>();

    @ViewById(R.id.main_left_drawer)
    ListView leftDrawer;

    @ViewById
    TextView tvDate;

    @ViewById
    ListView lvAppInfo;

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

    @AfterInject
    public void setAppData()
    {
        String []appNames = {"App1","App2","App3","App4","App5","App6","App7","App8","App9","App10"};
        String []dates={"24.7.2014","25.7.2014","26.7.2014","27.7.2014","28.7.2014","29.7.2014","30.7.2014","31.7.2014","1.8.2014",
                "2.8.2014","3.8.2014"};
        for(String date:dates)
        {
            for(String appName:appNames)
            {
                AppData appData = new AppData();
                appData.setAppName(appName);
                appData.setStartsPerDay(getRandomNumber());
                appData.setCrashes(getRandomNumber());
                appData.setCrashPercent(getRandomNumber());
                appData.setDate(date);
                appDatas.add(appData);
            }
        }
    }

    public Integer getRandomNumber()
    {
        Random rand = new Random();
        return rand.nextInt(100);
    }

    public void setAppInfo(String date)
    {
        ArrayList<AppData> appDataCurrentDate = new ArrayList<AppData>();
        for(AppData appData:appDatas)
        {
            if(date.equals(appData.getDate()))
            {
                appDataCurrentDate.add(appData);
            }
        }
        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(this,R.layout.appinfo_item,appDataCurrentDate);
        lvAppInfo.setAdapter(appInfoAdapter);
    }

	@AfterViews
	void initViews()
	{
		presenter.init(this);
		getApps();
	}

    @AfterViews
    public void initDrawer()
    {
        String[] names = getResources().getStringArray(R.array.drawer);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this,names);
        leftDrawer.setAdapter(adapter);
    }

    @AfterViews
    public void setCurrentDate()
    {
        String date = DATE_FORMAT.format(new Date(System.currentTimeMillis()));
        tvDate.setText(date);
        setAppInfo(date);
    }

    @Click(R.id.tvDate)
    public void setDateFromCalendar()
    {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    @Click(R.id.ibPreviousDay)
    public void previousDay()
    {
        mCalendar.add(Calendar.DAY_OF_MONTH, -1);
        setNewDate();

    }

    @Click(R.id.ibNextDay)
    public void nextDay()
    {
        mCalendar.add(Calendar.DAY_OF_MONTH, 1);
        setNewDate();
    }

    public void setNewDate()
    {
        String dayOfMonth = Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH));
        String month = Integer.toString((mCalendar.get(Calendar.MONTH))+1);
        String year = Integer.toString(mCalendar.get(Calendar.YEAR));
        String date = dayOfMonth.concat(".").concat(month).concat(".").concat(year);
        tvDate.setText(date);
        setAppInfo(date);
    }

    @ItemClick(R.id.main_left_drawer)
    public void drawerItemClick(int position)
    {
        if(position==1)
        {
            StatisticsActivity_.intent(this).start();
        }
    }

    @Override
    public Calendar getCalendar() {
        return mCalendar;
    }

    @Override
    public void setDate(String date)
    {
        tvDate.setText(date);
        parseNewDate(date);
        setAppInfo(date);
    }

    public void parseNewDate(String date)
    {
        try
        {
            Date parsedDate = DATE_FORMAT.parse(date);
            mCalendar.setTime(parsedDate);
        } catch (ParseException e)
        {
            Toast.makeText(this, getResources().getString(R.string.error), Toast.LENGTH_LONG).show();
        }
    }

    @OptionsItem(R.id.logout)
    void logoutSelect()
    {
        presenter.logout();
    }

	@Background
	void getApps()
	{
        remoteFacade.getErrorGraph();
	}


	@Override
	public Activity getActivity()
	{
		return this;
	}

}
