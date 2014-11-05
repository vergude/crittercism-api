package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;
import java.util.Date;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.loader.GraphStatisticsCursorLoader;
import intexsoft.by.crittercismapi.data.loader.MonthStatisticsCursorLoader;
import intexsoft.by.crittercismapi.event.OnSwipeTouchEvent;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.adapters.binder.DailyItemViewBinder;
import intexsoft.by.crittercismapi.ui.interactor.BuildLineGraphImpl;
import intexsoft.by.crittercismapi.ui.interactor.BuildPieGraphImpl;
import intexsoft.by.crittercismapi.ui.presenter.GraphStatisticsPresenter;
import intexsoft.by.crittercismapi.ui.presenter.GraphStatisticsPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.GraphStatisticsView;
import intexsoft.by.crittercismapi.ui.view.animation.EndAnimationListener;
import intexsoft.by.crittercismapi.ui.view.animation.MyAnimationSet;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;

@EFragment(R.layout.fragment_graph_statistics)
@OptionsMenu(R.menu.graph)
public class GraphStatisticsFragment extends Fragment implements GraphStatisticsView, LoaderManager.LoaderCallbacks<Cursor>
{

    private static final String DATE_FORMAT = "MMMM, yyyy";
    private Animation animationStart = null;
    private boolean clickResult = false;
    private MyAnimationSet myAnimationSet;

    private static final int DELAY_PROGRESS_BAR = 500;

    private static final String SELECTED_KEY = "selectedDate";
    private static final String SELECTED_TYPE = "selectedTypeGraph";
    private static final String SHORT_TYPE = "ASC";

    private String sortColumnName;
    private String sortOrder;

    private String selectedColumnName;
    private static final String APPID = "AppId";

    @ViewById
    TextView tvGraphTypeCrashes;

    @ViewById
    TextView tvGraphTypeLoads;

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

    @ViewById
    LinearLayout idFragmentLayout;


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
            selectedColumnName = savedInstanceState.getString(SELECTED_TYPE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState)
    {
        outState.putLong(SELECTED_KEY, selectedDate.getTime());
        outState.putString(SELECTED_TYPE, selectedColumnName);
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
        if (selectedColumnName != null)
        {
            selectTypeGraph(selectedColumnName);
        }
    }

    @AfterViews
    void swipeDate()
    {
        animationStart = AnimationUtils.loadAnimation(getActivity(), R.anim.scale);
        idFragmentLayout.setOnTouchListener(onSwipeTouchEvent);
        lvApplications.setOnTouchListener(onSwipeTouchEvent);
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
                        if (checkTypeGraph())
                        {
                            Bundle bundle = new Bundle();
                            bundle.putString(APPID, ((DailyItemViewBinder) view).getRemoteId());
                            getLoaderManager().restartLoader(1, bundle, getFragment());

                        }
                    }
                }
        );
        ((ViewGroup) view).findViewById(R.id.animationView).setVisibility(View.VISIBLE);
    }

    public GraphStatisticsFragment getFragment()
    {
        return this;
    }

    public boolean checkTypeGraph()
    {
        if (selectedColumnName == null)
        {
            Toast.makeText(getActivity(), "Please set type graph!", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
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

    private final OnSwipeTouchEvent onSwipeTouchEvent = new OnSwipeTouchEvent(getActivity())
    {
        @Override
        public void onSwipeLeft()
        {
            setDate(1);

            tvDateMonth.startAnimation(animationStart);
            progressContainer.setVisibility(View.VISIBLE);

        }

        @Override
        public void onSwipeRight()
        {
            setDate(-1);

            tvDateMonth.startAnimation(animationStart);
            progressContainer.setVisibility(View.VISIBLE);
        }
    };

    void setDate(int month)
    {
        Calendar mCalendar = Calendar.getInstance();
        mCalendar.setTime(selectedDate);
        mCalendar.add(Calendar.MONTH, month);

        selectedDate = mCalendar.getTime();

        setNewDate();
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

    @Click(R.id.tvGraphTypeCrashes)
    public void selectTypeCrashes()
    {
        selectTypeGraph(DailyStatisticsItem.COLUMN_CRASHES_COUNT);
    }

    @Click(R.id.tvGraphTypeLoads)
    public void selectTypeLoads()
    {
        selectTypeGraph(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT);
    }

    public void selectTypeGraph(String columnName)
    {
        if (columnName.equals(DailyStatisticsItem.COLUMN_CRASHES_COUNT))
        {
            tvGraphTypeCrashes.setTextColor(getResources().getColor(R.color.blue));
            tvGraphTypeLoads.setTextColor(getResources().getColor(R.color.back));
        }
        else
        {
            tvGraphTypeCrashes.setTextColor(getResources().getColor(R.color.back));
            tvGraphTypeLoads.setTextColor(getResources().getColor(R.color.blue));
        }
        selectedColumnName = columnName;
    }

    @OptionsItem
    public void showPieGraph()
    {
        if (checkTypeGraph())
        {
            getLoaderManager().restartLoader(2, null, this);
        }
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
    public void showGraph(Intent intent)
    {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.slide_left_in, R.anim.empty_animation);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle bundle)
    {
        Loader<Cursor> loader = null;

        switch (id)
        {
            case 0:
                loader = getMonthStatisticsCursorLoader();
                break;
            case 1:
                loader = new GraphStatisticsCursorLoader(getActivity(), bundle.getString(APPID), selectedColumnName, selectedDate);
                break;
            case 2:
                loader = getMonthStatisticsCursorLoader();
                break;
            default:
                break;
        }
        return loader;
    }

    public MonthStatisticsCursorLoader getMonthStatisticsCursorLoader()
    {
        String sortBy = (sortColumnName != null) ? sortColumnName + " " + sortOrder : null;
        return new MonthStatisticsCursorLoader(getActivity(), selectedDate, sortBy);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        switch (cursorLoader.getId())
        {
            case 0:
                adapter.swapCursor(cursor);
                hideProgressBar();
                break;
            case 1:
                presenter.buildConcreteGraph(cursor, selectedColumnName, getActivity(), new BuildLineGraphImpl());
                break;
            case 2:
                presenter.buildConcreteGraph(cursor, selectedColumnName, getActivity(), new BuildPieGraphImpl());
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }
}
