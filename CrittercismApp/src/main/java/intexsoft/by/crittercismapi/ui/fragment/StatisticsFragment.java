package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByCrashes;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByErrors;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByLoads;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByName;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.data.loader.DailyStatisticsCursorLoader;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.presenter.StatisticsPresenter;
import intexsoft.by.crittercismapi.ui.presenter.StatisticsPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.StatisticsView;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment implements StatisticsView, DatePickerFragment.FragmentDatePickerInterface, LoaderManager.LoaderCallbacks<Cursor>
{

	private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("d, MMM yyyy");
	public static final String TAG = MainFragment.class.getSimpleName();
	private Calendar mCalendar = Calendar.getInstance();

	@ViewById
	TextView tvDate;

	@ViewById
	ListView gvAppInfo;

	@Bean(StatisticsPresenterImpl.class)
	StatisticsPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;


	private DailyStatisticsAdapter adapter;

	public static StatisticsFragment build()
	{
		return StatisticsFragment_.builder().build();
	}

	@Override
	public void onStart()
	{
		super.onStart();

		getLoaderManager().initLoader(0, null, this);

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

		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void dataLoaded()
	{

	}

	@AfterViews
	protected void init() {
		adapter = new DailyStatisticsAdapter(getActivity());
		if (gvAppInfo != null) {
			gvAppInfo.setAdapter(adapter);
		}
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

	public void startSort(java.util.Comparator<? super DailyStatisticsItem> sorting){
//        if(mDailyStatisticsItems!=null)
//        {
//            Collections.sort(mDailyStatisticsItems, sorting);
//            //setNewAdapter();
//        }
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
		return new DailyStatisticsCursorLoader(getActivity(), mCalendar.getTime());
	}

	@Override
	public void onLoadFinished(Loader loader, Cursor data)
	{
		adapter.swapCursor(data);
	}

	@Override
	public void onLoaderReset(Loader loader)
	{

	}
}
