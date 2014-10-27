package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.loader.MonthStatisticsCursorLoader;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.adapters.binder.DailyItemViewBinder;
import intexsoft.by.crittercismapi.ui.presenter.MonthStatisticPresenter;
import intexsoft.by.crittercismapi.ui.presenter.MonthStatisticsPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.MonthStatisticsView;
import intexsoft.by.crittercismapi.ui.view.animation.EndAnimationListener;
import intexsoft.by.crittercismapi.ui.view.animation.MyAnimationSet;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.Launcher;

@EFragment(R.layout.fragment_month_statistcs)
public class MonthStatisticsFragment extends Fragment implements MonthStatisticsView, LoaderManager.LoaderCallbacks<Cursor>
{

    private static final String DATE_FORMAT = "MMMM, yyyy";
    private Animation animationStart = null;
    private boolean clickResult = false;
    private MyAnimationSet myAnimationSet;

    private static final int DELAY_PROGRESS_BAR = 500;
    private static final String SELECTED_KEY = "selectedDate";
    private static final String SHORT_TYPE = "ASC";


    public static final String TAG = MainFragment.class.getSimpleName();


    public static MonthStatisticsFragment build()
    {
        return MonthStatisticsFragment_.builder().build();
    }

    @ViewById
    TextView tvDateMonth;

    @ViewById
    GridView gvAppInfo;

    @ViewById
    FrameLayout progressContainer;

    @Bean(MonthStatisticsPresenterImpl.class)
    MonthStatisticPresenter presenter;

    Date selectedDate;

    private String sortColumnName;
    private String sortOrder;

    private DailyStatisticsAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);

        myAnimationSet = new MyAnimationSet(true);
        if (savedInstanceState == null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());

            selectedDate = calendar.getTime();
        }
        else
        {
            Date date = new Date(savedInstanceState.getLong(SELECTED_KEY));
            selectedDate = date;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putLong(SELECTED_KEY, selectedDate.getTime());
        super.onSaveInstanceState(outState);
    }

    @AfterViews
    void initViews()
    {
        presenter.init(this);
        setNewDate();
    }

    @AfterViews
    void swipeDate()
    {
        animationStart = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
    }

    @AfterViews
    protected void init()
    {
        adapter = new DailyStatisticsAdapter(getActivity(), true);
        if (gvAppInfo != null)
        {
            gvAppInfo.setAdapter(adapter);
        }
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
                    startAnimation(view, i);
                }
            }
        });
    }

    void startAnimation(final View view, final int id)
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
                        Launcher.showAppDetailsErrorActivity(getActivity(), ((DailyItemViewBinder) view).getRemoteId(),
                                ((DailyItemViewBinder) view).getAppName());
                    }
                }
        );
        ((ViewGroup) view).findViewById(R.id.animationView).setVisibility(View.VISIBLE);
    }

    @Click(R.id.ibPreviousMonth)
    public void previousMonth()
    {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(selectedDate);
        mCalendar.add(Calendar.MONTH, -1);

        selectedDate = mCalendar.getTime();

        setNewDate();

        tvDateMonth.startAnimation(animationStart);
        progressContainer.setVisibility(View.VISIBLE);
    }

    @Click(R.id.ibNextMonth)
    public void nextMonth()
    {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(selectedDate);
        mCalendar.add(Calendar.MONTH, 1);

        selectedDate = mCalendar.getTime();

        setNewDate();

        tvDateMonth.startAnimation(animationStart);
        progressContainer.setVisibility(View.VISIBLE);
    }

    private void setNewDate()
    {
        tvDateMonth.setText(DateTimeUtils.getFormattedDate(selectedDate, DATE_FORMAT));

        getLoaderManager().restartLoader(0, null, this);

    }

    @Override
    public Activity getContainer()
    {
        return getActivity();
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
        startSort(DailyStatisticsItem.COLUMN_CRASHES_PERCENT);
    }

    public void startSort(String columnName)
    {
        if (columnName.equals(sortColumnName) || sortColumnName == null)
        {
            sortOrder = (SHORT_TYPE.equals(sortOrder)) ? "DESC" : SHORT_TYPE;
        }
        sortColumnName = columnName;

        getLoaderManager().restartLoader(0, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        String sortBy = (sortColumnName != null) ? sortColumnName + " " + sortOrder : null;

        return new MonthStatisticsCursorLoader(getActivity(), selectedDate, sortBy);
    }

    @Override
    public void onLoadFinished(Loader loader, Cursor data)
    {
        adapter.swapCursor(data);

        hideProgressBar();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }

    @UiThread(delay = DELAY_PROGRESS_BAR)
    void hideProgressBar()
    {
        progressContainer.setVisibility(View.INVISIBLE);
    }

    @Override
    public void dataLoaded()
    {

    }
}
