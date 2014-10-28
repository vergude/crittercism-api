package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.widget.FrameLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;

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

    @ViewById
    FrameLayout graphLayout;

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
    void initViews()
    {
        presenter.init(this);

    }

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

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor)
    {
        if (cursor != null) {
           presenter.buildGraph(cursor, selectedColumnName);
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

    @Override
    public void showGraph(XYMultipleSeriesDataset multipleSeriesDataset, XYMultipleSeriesRenderer multipleSeriesRenderer)
    {
        GraphicalView graphicalView = ChartFactory.getCubeLineChartView(this, multipleSeriesDataset, multipleSeriesRenderer, 0);
        graphLayout.addView(graphicalView);
    }
}
