package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.content.DialogInterface;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.*;
import android.widget.*;

import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByCrashes;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByErrors;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByLoads;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByName;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.event.OnSwipeTouchEvent;
import intexsoft.by.crittercismapi.ui.adapters.AppInfoAdapter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MainPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MainView;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

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
	private static final String DATE_FORMAT = "d, MMM yyyy";

	public static final String TAG = MainFragment.class.getSimpleName();
	private Calendar calendar = Calendar.getInstance();
	private boolean clickShortResult = true;
	private List<DailyStatisticsItem> mDailyStatisticsItems;
	Animation animationStart = null;


	@ViewById
	TextView tvDate;

	@ViewById(R.id.progressContainer)
	FrameLayout progressContainer;

	@ViewById
	LinearLayout idFragmentLayout;

	@ViewById
	GridView gvAppInfo;

	@Bean(MainPresenterImpl.class)
	MainPresenter presenter;

	@Bean
	RemoteFacade remoteFacade;

	private final OnSwipeTouchEvent onSwipeTouchEvent = new OnSwipeTouchEvent(getActivity())
	{
		@Override
		public void onSwipeLeft()
		{
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			setNewDate();
			tvDate.startAnimation(animationStart);
			progressContainer.setVisibility(View.VISIBLE);
			hideProgressBar();

		}

		@Override
		public void onSwipeRight()
		{
			calendar.add(Calendar.DAY_OF_MONTH, 1);
			setNewDate();
			tvDate.startAnimation(animationStart);
			progressContainer.setVisibility(View.VISIBLE);
			hideProgressBar();
		}
	};

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
	void swipeDate()
	{
		animationStart = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
		idFragmentLayout.setOnTouchListener(onSwipeTouchEvent);
		gvAppInfo.setOnTouchListener(onSwipeTouchEvent);
	}

	@AfterViews
	void initViews()
	{
		presenter.init(this);
		gvAppInfo.setOnItemClickListener(new AdapterView.OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> adapterView, final View view, final int i, long l)
			{
				if (((ViewGroup) view).findViewWithTag("TAG_FADE_ITEM_VIEW") == null)
			{
				final View fadeView = new View(getActivity());
				fadeView.setTag("TAG_FADE_ITEM_VIEW");
				RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, view.getHeight());
				fadeView.setLayoutParams(layoutParams);
					fadeView.setBackgroundColor(0xA9FEA20F);

				AnimationSet animationSet = new AnimationSet(true);

					fadeView.startAnimation(getAnimationSet(animationSet));
					getAnimationSet(animationSet).setAnimationListener(new Animation.AnimationListener()
				{
					@Override
					public void onAnimationStart(Animation animation)
					{

					}

					@Override
					public void onAnimationEnd(Animation animation)
					{
							removeView(view, fadeView, i);
					}

					@Override
					public void onAnimationRepeat(Animation animation)
					{

					}
				});
					((ViewGroup) view).addView(fadeView, 0);
				}
			}
		});
	}

	@UiThread
	void removeView(View view, View fadeView, int i)
	{
		((ViewGroup) view).removeView(fadeView);
//				Launcher.showAppDetailsErrorActivity(getActivity(), mDailyStatisticsItems.get(i).getApplication().getRemoteId(),
//				mDailyStatisticsItems.get(i).getApplication().getName());
			}

	private AnimationSet getAnimationSet(AnimationSet animationSet)
	{
		Animation animationScale = new ScaleAnimation(0, 1, 0, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		animationScale.setDuration(100);

		Animation animationAlpha = new AlphaAnimation(0, 1);
		animationAlpha.setDuration(100);
		animationAlpha.setStartOffset(50);

		Animation animationAlphaEnd = new AlphaAnimation(1, 0);
		animationAlphaEnd.setDuration(700);
		animationAlphaEnd.setStartOffset(150);

		animationSet.setFillEnabled(true);

		animationSet.setInterpolator(new LinearInterpolator());
		animationSet.setDuration(850);

		animationSet.addAnimation(animationScale);
		animationSet.addAnimation(animationAlpha);
		animationSet.addAnimation(animationAlphaEnd);

		return animationSet;
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
	public Date getCurrentDate()
	{
		return calendar.getTime();
	}

	@Override
	public void setSelectedDate(Date date)
		{
		tvDate.setText(DateTimeUtils.getFormattedDate(date, DATE_FORMAT));
		calendar.setTime(date);
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
			if (clickShortResult)
			{
				Collections.sort(mDailyStatisticsItems, sorting);
				setNewAdapter();
				clickShortResult = false;
			}
			else
			{
				Collections.reverse(mDailyStatisticsItems);
				setNewAdapter();
				clickShortResult = true;
			}
		}
	}

	public void setNewAdapter()
	{
		if (mDailyStatisticsItems == null)
		{
			showError();
		}
		else
		{
			AppInfoAdapter appInfoAdapter = new AppInfoAdapter(getActivity(), R.layout.appinfo_item, mDailyStatisticsItems);
			gvAppInfo.setAdapter(appInfoAdapter);
		}
	}

	public void showError()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		builder.setTitle("Error!")
				.setMessage("No data is loaded")
				.setIcon(R.drawable.ic_launcher)
				.setCancelable(false)
				.setNegativeButton("Restart App",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								getActivity().finish();
								Launcher.showLoginActivity(getActivity(), false);
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@UiThread(delay = 2000)
	void hideProgressBar()
	{
		progressContainer.setVisibility(View.INVISIBLE);
	}
}
