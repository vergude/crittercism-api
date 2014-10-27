package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
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
import intexsoft.by.crittercismapi.data.loader.GraphStatisticsCursorLoader;
import intexsoft.by.crittercismapi.data.loader.MonthStatisticsCursorLoader;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.adapters.binder.DailyItemViewBinder;
import intexsoft.by.crittercismapi.ui.presenter.GraphStatisticsPresenter;
import intexsoft.by.crittercismapi.ui.presenter.GraphStatisticsPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.GraphStatisticsView;
import intexsoft.by.crittercismapi.ui.view.animation.EndAnimationListener;
import intexsoft.by.crittercismapi.ui.view.animation.MyAnimationSet;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import intexsoft.by.crittercismapi.utils.Launcher;

@EFragment(R.layout.fragment_graph_statistics)
public class GraphStatisticsFragment extends Fragment implements GraphStatisticsView, LoaderManager.LoaderCallbacks<Cursor>
{

    private static final String DATE_FORMAT = "MMMM, yyyy";
    private Animation animationStart = null;
    private boolean clickResult = false;
    private MyAnimationSet myAnimationSet;

    private static final int DELAY_PROGRESS_BAR = 500;

    private static final String SELECTED_KEY = "selectedDate";
    private static final String SHORT_TYPE = "ASC";

    private String sortColumnName;
    private String sortOrder;

    private String selectedColumnName;

    @ViewById
    TextView tvDateMonth;

    @ViewById
    TextView tvHeadCrashes;

    @ViewById
    TextView tvHeadLoads;

    @ViewById
    ListView lvApplications;

    @ViewById
    FrameLayout progressContainer;


    @Bean(GraphStatisticsPresenterImpl.class)
    GraphStatisticsPresenter presenter;

    Date selectedDate;

    private DailyStatisticsAdapter adapter;

    public static final String TAG = MainFragment.class.getSimpleName();

    public static GraphStatisticsFragment build()
    {
        return GraphStatisticsFragment_.builder().build();
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
    protected void init()
    {
        adapter = new DailyStatisticsAdapter(getActivity(), true);
        if (lvApplications != null)
        {
            lvApplications.setAdapter(adapter);
        }
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

    @AfterViews
    void onClickListViewItem()
    {
        lvApplications.setOnItemClickListener(new AdapterView.OnItemClickListener()
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
                        Launcher.startGraphActivity(getActivity(), ((DailyItemViewBinder) view).getRemoteId(),
                                ((DailyItemViewBinder) view).getAppName(), selectedColumnName, selectedDate);
                    }
                }
        );
        ((ViewGroup) view).findViewById(R.id.animationView).setVisibility(View.VISIBLE);
    }
    private void setNewDate()
    {
        tvDateMonth.setText(DateTimeUtils.getFormattedDate(selectedDate, DATE_FORMAT));
        getLoaderManager().restartLoader(0, null, this);

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
        selectTypeGraph(DailyStatisticsItem.COLUMN_CRASHES_COUNT);
    }

    @Click(R.id.tvHeadLoads)
    public void sortAppLoads()
    {
        selectTypeGraph(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT);
    }

    public void selectTypeGraph(String columnName)
    {
        if (columnName.equals(DailyStatisticsItem.COLUMN_CRASHES_COUNT))
        {
            tvHeadCrashes.setTextColor(getResources().getColor(R.color.blue));
            tvHeadLoads.setTextColor(getResources().getColor(R.color.back));
        }
        else
        {
            tvHeadCrashes.setTextColor(getResources().getColor(R.color.back));
            tvHeadLoads.setTextColor(getResources().getColor(R.color.blue));
        }
        selectedColumnName = columnName;
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
    public void onResume()
    {
        super.onResume();

        progressContainer.setVisibility(View.VISIBLE);
        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle)
    {
        String sortBy = (sortColumnName != null) ? sortColumnName + " " + sortOrder : null;
        return new MonthStatisticsCursorLoader(getActivity(), selectedDate, sortBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor data)
    {
        adapter.swapCursor(data);
        hideProgressBar();
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }
}
