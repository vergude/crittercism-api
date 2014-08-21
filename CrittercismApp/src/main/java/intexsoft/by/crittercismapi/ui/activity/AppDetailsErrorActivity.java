package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByCrashes;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByDate;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByErrors;
import intexsoft.by.crittercismapi.data.bean.sorting.SortedByLoads;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.ui.adapters.AppErrorDetailsAdapter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.AppDetailsErrorView;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

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
		if (dailyStatisticsItemList == null)
		{
			showError();
		}
		else
		{
			AppErrorDetailsAdapter appErrorDetailsAdapter = new AppErrorDetailsAdapter(this, R.layout.app_details_item, dailyStatisticsItemList);
			appDetailsGrid.setAdapter(appErrorDetailsAdapter);
		}
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

	public void showError()
	{
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Error!")
				.setMessage("No data is loaded")
				.setIcon(R.drawable.ic_launcher)
				.setCancelable(false)
				.setNegativeButton("Restart App",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int id) {
								dialog.cancel();
								finish();
								Launcher.showLoginActivity(getApplication(), false);
							}
						});
		AlertDialog alert = builder.create();
		alert.show();
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		int id = item.getItemId();

		switch (id)
		{
			case android.R.id.home:
				onBackPressed();
				return true;
		}
		// Handle your other action bar items...
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();

		(this).overridePendingTransition(R.anim.empty_animation, R.anim.slide_left_out);
	}
}
