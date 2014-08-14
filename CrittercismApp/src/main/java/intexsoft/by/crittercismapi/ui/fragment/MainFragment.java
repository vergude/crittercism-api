package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByCrashes;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByErrors;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByLoads;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByName;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.ui.adapters.AppInfoAdapter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */

@EFragment(R.layout.fragment_main)
public class MainFragment extends Fragment implements MainView, DatePickerFragment.FragmentDatePickerInterface
{
	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d, MMM yyyy");
	public static final String TAG = MainFragment.class.getSimpleName();
	private Calendar calendar = Calendar.getInstance();
	private boolean clickResult = true;
	private List<DailyStatisticsItem> mDailyStatisticsItems;


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
	void errors()
	{

	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);
		gvAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
			{
				Launcher.showAppDetailsErrorActivity(getActivity(), mDailyStatisticsItems.get(i).getApplication().getId(),
						mDailyStatisticsItems.get(i).getApplication().getName());
			}
		});
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
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		setNewDate();
	}

	@Click(R.id.ibNextDay)
	public void nextDay()
	{
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		setNewDate();
	}

	@Override
	public Calendar getCalendar()
	{
		return calendar;
	}

	@Override
	public void setDate(String date)
	{
		tvDate.setText(date);
		parseNewDate(date);
	}

	public void parseNewDate(String date)
	{
		try
		{
			Date parsedDate = DATE_FORMAT.parse(date);
			calendar.setTime(parsedDate);
		}
		catch (ParseException e)
		{
			Toast.makeText(getActivity(), getResources().getString(R.string.error_parse_date), Toast.LENGTH_LONG).show();
		}
	}

	public void setNewDate()
	{
		String[] mounts = getResources().getStringArray(R.array.year);
		String dayOfMonth = Integer.toString(calendar.get(Calendar.DAY_OF_MONTH));
		String month = mounts[calendar.get(Calendar.MONTH)];
		String year = Integer.toString(calendar.get(Calendar.YEAR));
		String date = dayOfMonth.concat(", ").concat(month).concat(" ").concat(year);
		tvDate.setText(date);
	}

	@Override
	public void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems)
	{
		mDailyStatisticsItems = dailyStatisticsItems;
		setNewAdapter();
	}

	@Click(R.id.tvHeadAppName)
	public void sortAppNAme()
	{
		startSort(new SortedByName());
	}

	@Click(R.id.tvHeadCrashes)
	public void sortAppCrashes()
	{
		startSort(new SortedByCrashes());
	}

	@Click(R.id.tvHeadLoads)
	public void sortAppLoads()
	{
		startSort(new SortedByLoads());
	}

	@Click(R.id.tvHeadAppErrors)
	public void sortAppErrors()
	{
		startSort(new SortedByErrors());
	}

	public void startSort(java.util.Comparator<? super DailyStatisticsItem> sorting)
	{

		if (mDailyStatisticsItems != null)
		{
			if (clickResult)
			{
				Collections.sort(mDailyStatisticsItems, sorting);
				setNewAdapter();
				clickResult = false;
			}
			else
			{
				Collections.reverse(mDailyStatisticsItems);
				setNewAdapter();
				clickResult = true;
			}
		}
	}

	public void setNewAdapter()
	{
			AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getActivity(), R.layout.appinfo_item, mDailyStatisticsItems);
			gvAppInfo.setAdapter(appInfoAdapter);
	}
}
