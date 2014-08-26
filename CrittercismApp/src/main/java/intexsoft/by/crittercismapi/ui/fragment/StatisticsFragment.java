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
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
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
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment implements StatisticsView, DatePickerFragment.FragmentDatePickerInterface, LoaderManager.LoaderCallbacks<Cursor>
{

	private static final String DATE_FORMAT = "d, MMM yyyy";

	public static final String TAG = MainFragment.class.getSimpleName();

	@ViewById
	TextView tvDate;

	@ViewById
	ListView gvAppInfo;

	@Bean(StatisticsPresenterImpl.class)
	StatisticsPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

	Date selectedDate;

	private DailyStatisticsAdapter adapter;

    private String sortColumnName;
    private String sortOrder;

	public static StatisticsFragment build()
	{
		return StatisticsFragment_.builder().build();
	}

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);

		if (savedInstanceState == null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);

			selectedDate = calendar.getTime();
		}
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

	@Override
	public void onResume()
	{
		super.onResume();
		getLoaderManager().initLoader(0, null, this);
	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);

		setNewDate();
	}

	@Override
	public Activity getContainer()
	{
		return getActivity();
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
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(selectedDate);
		mCalendar.add(Calendar.DAY_OF_MONTH, -1);

		selectedDate = mCalendar.getTime();

		setNewDate();
	}

	@Click(R.id.ibNextDay)
	public void nextDay()
	{
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(selectedDate);
		mCalendar.add(Calendar.DAY_OF_MONTH, 1);

		selectedDate = mCalendar.getTime();

		setNewDate();
	}

	@Override
	public Date getCurrentDate()
	{
		return selectedDate;
	}

	@Override
	public void setSelectedDate(Date date)
	{
		if (selectedDate.equals(date))
		{
			return;
		}

		selectedDate = date;
		setNewDate();
	}

	public void setNewDate()
	{
		String[]mounts = getResources().getStringArray(R.array.year);
		String dayOfMonth = Integer.toString(mCalendar.get(Calendar.DAY_OF_MONTH));
		String month = mounts[(mCalendar.get(Calendar.MONTH))];
		String year = Integer.toString(mCalendar.get(Calendar.YEAR));
		String date = dayOfMonth.concat(", ").concat(month).concat(" ").concat(year);
		tvDate.setText(date);
		tvDate.setText(DateTimeUtils.getFormattedDate(selectedDate, DATE_FORMAT));

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
		startSort(CrittercismApp.COLUMN_NAME);
	}

	@Click(R.id.tvHeadCrashes)
	public void sortAppCrashes()
	{
		startSort(DailyStatisticsItem.COLUMN_CRASHES_COUNT);
	}

	@Click(R.id.tvHeadLoads)
	public void sortAppLoads()
	{
		startSort(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT);
	}

	@Click(R.id.tvHeadAppErrors)
	public void sortAppErrors()
	{
		startSort(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT);
	}

	public void startSort(String columnName)
    {
        if (columnName.equals(sortColumnName) || sortColumnName == null)
        {
            sortOrder = ("ASC".equals(sortOrder)) ? "DESC" : "ASC";
        }
        sortColumnName = columnName;

        getLoaderManager().restartLoader(0, null, this);
	}


	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args)
	{
        String sortBy = (sortColumnName != null) ? sortColumnName + " " + sortOrder : null;

		return new DailyStatisticsCursorLoader(getActivity(), selectedDate, sortBy);
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
