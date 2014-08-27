package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.data.loader.DailyStatisticsCursorLoader;
import intexsoft.by.crittercismapi.event.OnSwipeTouchEvent;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.adapters.binder.DailyItemViewBinder;
import intexsoft.by.crittercismapi.ui.presenter.StatisticsPresenter;
import intexsoft.by.crittercismapi.ui.presenter.StatisticsPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.StatisticsView;
import intexsoft.by.crittercismapi.ui.view.animation.EndAnimationListener;
import intexsoft.by.crittercismapi.ui.view.animation.MyAnimationSet;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.*;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 11.07.2014.
 */
@EFragment(R.layout.fragment_statistics)
public class StatisticsFragment extends Fragment implements StatisticsView, DatePickerFragment.FragmentDatePickerInterface, LoaderManager.LoaderCallbacks<Cursor>
{

	private static final String DATE_FORMAT = "d, MMM yyyy";
	private Animation animationStart = null;
	private MyAnimationSet myAnimationSet;
	private boolean clickResult = false;

	public static final String TAG = MainFragment.class.getSimpleName();

	@ViewById
	TextView tvDate;

	@ViewById
	LinearLayout idFragmentLayout;

	@ViewById
	FrameLayout progressContainer;

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

		myAnimationSet = new MyAnimationSet(true);
		if (savedInstanceState == null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DATE, -1);

			selectedDate = calendar.getTime();
		}
	}

	private final OnSwipeTouchEvent onSwipeTouchEvent = new OnSwipeTouchEvent(getActivity())
	{
		@Override
		public void onSwipeLeft()
		{
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(selectedDate);
			mCalendar.add(Calendar.DAY_OF_MONTH, 1);

			selectedDate = mCalendar.getTime();

			setNewDate();

			tvDate.startAnimation(animationStart);
			progressContainer.setVisibility(View.VISIBLE);

		}

		@Override
		public void onSwipeRight()
		{
			Calendar mCalendar = Calendar.getInstance();
			mCalendar.setTime(selectedDate);
			mCalendar.add(Calendar.DAY_OF_MONTH, -1);

			selectedDate = mCalendar.getTime();

			setNewDate();

			tvDate.startAnimation(animationStart);
			progressContainer.setVisibility(View.VISIBLE);
		}
	};

	@AfterViews
	void swipeDate()
	{
		animationStart = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
		idFragmentLayout.setOnTouchListener(onSwipeTouchEvent);
		gvAppInfo.setOnTouchListener(onSwipeTouchEvent);
	}

	@AfterViews
	void onClickListViewItem()
	{
		gvAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(final AdapterView<?> adapterView, final View view, final int i, long l)
			{
				if (((ViewGroup) view).findViewById(R.id.animationView).getVisibility() == View.INVISIBLE && !clickResult)
				{
					clickResult = true;

					((ViewGroup) view).findViewById(R.id.animationView).startAnimation(myAnimationSet);
					myAnimationSet.setAnimationListener(
							new EndAnimationListener()
							{
								@Override
								public void onAnimationEnd(Animation animation)
								{
									((ViewGroup) view).findViewById(R.id.animationView).setVisibility(View.INVISIBLE);
									clickResult = false;
									Launcher.showAppDetailsErrorActivity(getActivity(),((DailyItemViewBinder) view).getRemoteId(),
											((DailyItemViewBinder) view).getAppName());
								}
							}
					);
					((ViewGroup) view).findViewById(R.id.animationView).setVisibility(View.VISIBLE);
				}
			}
		});
	}

	@UiThread
	void removeView(View view, View fadeView, int i)
	{
		((ViewGroup) view).removeView(fadeView);
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
		progressContainer.setVisibility(View.VISIBLE);
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

		tvDate.startAnimation(animationStart);
		progressContainer.setVisibility(View.VISIBLE);
	}

	@Click(R.id.ibNextDay)
	public void nextDay()
	{
		Calendar mCalendar = Calendar.getInstance();
		mCalendar.setTime(selectedDate);
		mCalendar.add(Calendar.DAY_OF_MONTH, 1);

		selectedDate = mCalendar.getTime();

		setNewDate();

		tvDate.startAnimation(animationStart);
		progressContainer.setVisibility(View.VISIBLE);
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

		tvDate.startAnimation(animationStart);
		progressContainer.setVisibility(View.VISIBLE);
	}

	public void setNewDate()
	{
		tvDate.setText(DateTimeUtils.getFormattedDate(selectedDate, DATE_FORMAT));

		getLoaderManager().restartLoader(0, null, this);
	}

	@Override
	public void dataLoaded()
	{

	}

	@AfterViews
	protected void init() {
		adapter = new DailyStatisticsAdapter(getActivity(), true);
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
		hideProgressBar();
	}

	@Override
	public void onLoaderReset(Loader loader)
	{

	}

	@UiThread(delay = 500)
	void hideProgressBar()
	{
		progressContainer.setVisibility(View.INVISIBLE);
	}
}
