package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;

import java.util.Date;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.loader.GraphStatisticsCursorLoader;
import intexsoft.by.crittercismapi.ui.presenter.GraphActivityPresenter;
import intexsoft.by.crittercismapi.ui.presenter.GraphActivityPresenterImpl;
import intexsoft.by.crittercismapi.ui.view.GraphActivityView;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;

@EActivity(R.layout.activity_graph)
public class GraphActivity extends Activity implements LoaderManager.LoaderCallbacks<Cursor>, GraphActivityView

{

    @Extra
    String appId;

    @Extra
    String appName;

    @Extra
    String selectedColumnName;

    @Extra
    Date selectedDate;

    @Bean(GraphActivityPresenterImpl.class)
    GraphActivityPresenter presenter;

    @AfterViews
    public void init()
    {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle)
    {
        return new GraphStatisticsCursorLoader(this, appId, selectedColumnName, selectedDate);
    }
    private static final String DATE_FORMAT = "d, MMM (E)";
    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    long date = cursor.getLong(cursor.getColumnIndex(DailyStatisticsItem.COLUMN_DATE));
                    Log.d("ASDA", "" + (cursor.getInt(cursor.getColumnIndex(selectedColumnName)))+"------ "+(DateTimeUtils.getFormattedDate(new Date(date), DATE_FORMAT)));
                } while (cursor.moveToNext());
            }
            cursor.close();
        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader)
    {

    }

    @Override
    public Activity getContainer()
    {
        return null;
    }

    @Override
    public void dataLoaded()
    {

    }
}
