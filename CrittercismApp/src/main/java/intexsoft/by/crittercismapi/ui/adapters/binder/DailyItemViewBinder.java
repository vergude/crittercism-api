package intexsoft.by.crittercismapi.ui.adapters.binder;

import android.content.Context;
import android.database.Cursor;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by dmitry.lomako on 29.07.2014.
 */

@EViewGroup(R.layout.appinfo_item)
public class DailyItemViewBinder extends RelativeLayout {

	@ViewById(R.id.tvAppName)
	TextView tvAppName;

	@ViewById(R.id.tvCrashesCount)
	TextView tvCrashesCount;

	@ViewById(R.id.tvAppLoadsCount)
	TextView tvAppLoadsCount;

	@ViewById(R.id.tvAppErrorPersent)
	TextView tvAppErrorPersent;

	private String remoteId;
	private String appName;

	public String getAppName()
	{
		return appName;
	}

	public String getRemoteId()
	{
		return remoteId;
	}

	public static DailyItemViewBinder build(Context context) {
        return DailyItemViewBinder_.build(context);
    }

    public DailyItemViewBinder(Context context) {
        super(context);
    }

    public DailyItemViewBinder(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyItemViewBinder(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public View bind(Cursor data) {

		int crashesCount = data.getInt(data.getColumnIndex(DailyStatisticsItem.COLUMN_CRASHES_COUNT));
		int appLoadsCount = data.getInt(data.getColumnIndex(DailyStatisticsItem.COLUMN_APP_LOADS_COUNT));

		remoteId = data.getString(data.getColumnIndex(DailyStatisticsItem.COLUMN_APP_REMOTE_ID));
		appName = data.getString(data.getColumnIndex(CrittercismApp.COLUMN_NAME));

		tvAppName.setText(data.getString(data.getColumnIndex(CrittercismApp.COLUMN_NAME)));
		tvCrashesCount.setText(String.valueOf(crashesCount));
		tvAppLoadsCount.setText(String.valueOf(appLoadsCount));
		tvAppErrorPersent.setText(DailyStatisticsItem.getFormatedCrashesPercent(crashesCount, appLoadsCount));

        return this;
    }

}
