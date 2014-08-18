package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.widget.GridView;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.sorting.*;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.ui.adapters.AppErrorDetailsAdapter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.AppDetailsErrorView;
import org.androidannotations.annotations.*;

import java.util.Collections;
import java.util.List;

/**
 * Created by Евгений on 04.08.2014.
 */
@EActivity(R.layout.app_details_error_activity)
public class AppDetailsErrorActivity extends Activity implements AppDetailsErrorView
{
	private List<DailyStatisticsItem> dailyStatisticsItemList;
	private boolean clickResult = true;

	@ViewById(R.id.appDetailsGrid)
	GridView appDetailsGrid;

	@ViewById
	TextView tvAppName;

	@Bean(AppDetailsErrorPresenterImpl.class)
	AppDetailsErrorPresenter appDetailsErrorPresenter;

	private GraphResponse appErrorDetails;

	@Extra
	String appId;

	@Extra
	String appName;


	@Override
	protected void onStart()
	{
		super.onStart();
		appDetailsErrorPresenter.onStart();
	}

	@Override
	protected void onStop()
	{
		super.onStop();
		appDetailsErrorPresenter.onStop();
	}

	@AfterViews
	void init()
	{
		appDetailsErrorPresenter.init(this);
	}

	@AfterViews
	public void setAppName()
	{
		tvAppName.setText(appName);
	}

	@Override
	public Activity getContainer()
	{
		return this;
	}


	@Override
	public String getAppId()
	{
		return appId;
	}

	@Override
	public void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems)
	{
		dailyStatisticsItemList = dailyStatisticsItems;
		setNewAdapter();
	}

	@Click(R.id.tvDate)
	public void sortDate()
	{
		startSort(new SortedByDate());
	}

	@Click(R.id.tvCrashes)
	public void sortCrashes()
	{
		startSort(new SortedByCrashes());
	}

	@Click(R.id.tvLoads)
	public void sortLoads()
	{
		startSort(new SortedByLoads());
	}

	@Click(R.id.tvError)
	public void sortError()
	{
		startSort(new SortedByErrors());
	}

	public void setNewAdapter()
	{
		AppErrorDetailsAdapter appErrorDetailsAdapter = new AppErrorDetailsAdapter(this, R.layout.app_details_item, dailyStatisticsItemList);
		appDetailsGrid.setAdapter(appErrorDetailsAdapter);
	}

	public void startSort(java.util.Comparator<? super DailyStatisticsItem> sorting)
	{

		if (dailyStatisticsItemList != null)
		{
			if (clickResult)
			{
				Collections.sort(dailyStatisticsItemList, sorting);
				setNewAdapter();
				clickResult = false;
			}
			else
			{
				Collections.reverse(dailyStatisticsItemList);
				setNewAdapter();
				clickResult = true;
			}
		}
	}
}
