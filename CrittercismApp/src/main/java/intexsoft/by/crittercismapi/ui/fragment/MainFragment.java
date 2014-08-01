package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.ui.adapters.AppInfoAdapter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements MainView,DatePickerFragment.FragmentDatePickerInterface
{
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d, MMM yyyy");
	public static final String TAG = MainFragment.class.getSimpleName();
    private Calendar mCalendar = Calendar.getInstance();
    private ArrayList<DailyStatisticsItem> mDailyStatisticsItems = new ArrayList<DailyStatisticsItem>();

    @ViewById
    TextView tvDate;

    @ViewById
    GridView gvAppInfo;

	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

	public static MainFragment build()
	{
		return MainFragment_.builder().build();
	}

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

	@Override
	public Activity getContainer()
	{
		return getActivity();
	}

    @AfterViews
    public void setCurrentDate()
    {
        setNewDate();
    }

    @Click(R.id.tvDate)
    public void setDateFromCalendar()
    {
        DialogFragment datePickerFragment = new DatePickerFragment();
        datePickerFragment.setTargetFragment(this, 0);
        datePickerFragment.show(getFragmentManager(), "datePicker");
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

    @Override
    public Calendar getCalendar()
    {
        return mCalendar;
    }

    @Override
    public void setDate(String date)
    {
        tvDate.setText(date);
        setAppInfoGrid(date);
        parseNewDate(date);
    }

    public void parseNewDate(String date)
    {
        try
        {
            Date parsedDate = DATE_FORMAT.parse(date);
            mCalendar.setTime(parsedDate);
        } catch (ParseException e)
        {
            Toast.makeText(getActivity(), getResources().getString(R.string.error_parse_date), Toast.LENGTH_LONG).show();
        }
    }

    public void setNewDate()
    {
        String[]mounts=getResources().getStringArray(R.array.year);
        String dayOfMonth = Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH));
        String month = mounts[(mCalendar.get(Calendar.MONTH))];
        String year = Integer.toString(mCalendar.get(Calendar.YEAR));
        String date = dayOfMonth.concat(", ").concat(month).concat(" ").concat(year);
        tvDate.setText(date);
        setAppInfoGrid(date);
    }

    //**Generates fake objects*/
    @AfterViews
    public void setCrittercismAppsItems(){
        ArrayList<CrittercismApp>crittercismAppsItems = new ArrayList<CrittercismApp>();
        String []appNames = {"App1","App2","App3","App4","App5","App6","App7","App8","App9","App10"};
        for(int i=0;i<10;i++){
            CrittercismApp crittercismAppItem = new CrittercismApp(Integer.toString(generatedRandomInt()),appNames[i]);
            crittercismAppsItems.add(crittercismAppItem);
        }
        setDailyStatisticsItems(crittercismAppsItems);
    }

    //**Generates fake objects*/
    public void setDailyStatisticsItems(ArrayList<CrittercismApp>crittercismAppsItems){
        String []dates={"24, July 2014","25, July 2014","26, July 2014","27, July 2014","28, July 2014","29, July 2014",
                "30, July 2014","31, July 2014","1, August 2014","2, August 2014","3, August 2014"};
        for(String date:dates){
            for(CrittercismApp crittercismApp:crittercismAppsItems) {
                DailyStatisticsItem dailyStatisticsItem = new DailyStatisticsItem(crittercismApp,generatedRandomInt(),
                        generatedRandomInt(),date);
                mDailyStatisticsItems.add(dailyStatisticsItem);
            }
        }
        setAppInfoGrid(tvDate.getText().toString());
    }
    public Integer generatedRandomInt(){
        Random rand = new Random();
        return rand.nextInt(100);
    }

    public void setAppInfoGrid(String date){
        ArrayList<DailyStatisticsItem> dailyStatisticsItemsForAdapter = new ArrayList<DailyStatisticsItem>();
        for(DailyStatisticsItem dailyStatisticsItem:mDailyStatisticsItems)
        {
            if(date.equals(dailyStatisticsItem.getDate()))
            {
                dailyStatisticsItemsForAdapter.add(dailyStatisticsItem);
            }
        }
        AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getActivity(),R.layout.appinfo_item,dailyStatisticsItemsForAdapter);
        gvAppInfo.setAdapter(appInfoAdapter);
    }
}
