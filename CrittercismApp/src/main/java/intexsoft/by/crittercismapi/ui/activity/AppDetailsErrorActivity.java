package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.GridView;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.loader.StatisticsCursorLoader;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.ui.adapters.DailyStatisticsAdapter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenter;
import intexsoft.by.crittercismapi.ui.presenter.AppDetailsErrorPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.AppDetailsErrorView;
import intexsoft.by.crittercismapi.utils.Launcher;
import org.androidannotations.annotations.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Евгений on 04.08.2014.
 */
@EActivity(R.layout.app_details_error_activity)
public class AppDetailsErrorActivity extends Activity implements AppDetailsErrorView, LoaderManager.LoaderCallbacks<Cursor>
{
	private List<DailyStatisticsItem> dailyStatisticsItemList;
	private boolean clickResult = true;

	private Date startDate;
	private Date endDate;

	private String sortColumnName;
	private String sortOrder;

	private DailyStatisticsAdapter adapter;


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
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);



		endDate = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endDate);
		calendar.add(Calendar.DAY_OF_MONTH, -30);
		startDate = calendar.getTime();

		getLoaderManager().initLoader(0, null, this);

	}

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

//	@AfterViews
//	void init()
//	{
//		appDetailsErrorPresenter.init(this);
//	}

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
		//setNewAdapter();
	}

	@Click(R.id.tvDate)
	public void sortDate()
	{
		startSort(DailyStatisticsItem.COLUMN_DATE);
	}

	@Click(R.id.tvCrashes)
	public void sortCrashes()
	{
		startSort(DailyStatisticsItem.COLUMN_CRASHES_COUNT);
	}

	@Click(R.id.tvLoads)
	public void sortLoads()
	{
		startSort(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT);
	}

	@Click(R.id.tvError)
	public void sortError()
	{
		startSort(DailyStatisticsItem.COLUMN_CRASHES_PERCENT);
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

	@AfterViews
	protected void init() {
		adapter = new DailyStatisticsAdapter(this, false);
		if (appDetailsGrid != null) {
			appDetailsGrid.setAdapter(adapter);
		}
	}

	@Override
	public void onBackPressed()
	{
		super.onBackPressed();

		(this).overridePendingTransition(R.anim.empty_animation, R.anim.slide_left_out);
	}

	@Override
	public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
	{
		String sortBy = (sortColumnName != null) ? sortColumnName + " " + sortOrder : null;

		return new StatisticsCursorLoader(this, startDate, endDate , sortBy, appId);
	}

	@Override
	public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
	{
		adapter.swapCursor(cursor);
	}

	@Override
	public void onLoaderReset(Loader<Cursor> cursorLoader)
	{

	}
}
