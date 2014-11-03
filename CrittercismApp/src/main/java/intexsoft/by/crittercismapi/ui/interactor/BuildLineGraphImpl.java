package intexsoft.by.crittercismapi.ui.interactor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;

import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;
import org.androidannotations.annotations.EBean;

import java.util.Date;

import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;


/**
 * Created by vadim on 27.10.2014.
 */

@EBean
public class BuildLineGraphImpl implements BuildGraphInteractor
{

    private static final String COLOR_ORANGE = "#FEA20F";
    private static final String COLOR_WHITE = "#FFFFFF";
    private static final int TEXT_SIZE_LABELS = 15;
    private static final int TEXT_SIZE_LEGEND = 20;
    private static final int MARGINS = 30;

    private static final String LOADS_GRAPH = "Loads";
    private static final String CRASHES_GRAPH = "Crashes";
    private static final String DAYS = "Days";

    private static final String DATE_FORMAT = "d";

    @Override
    public Intent buildGraph(Cursor cursor, String selectedColumnName, Context context)
    {
        XYMultipleSeriesDataset multipleSeriesDatasetdataset = new XYMultipleSeriesDataset();
        multipleSeriesDatasetdataset.addSeries(getSeries(cursor, selectedColumnName));

        XYMultipleSeriesRenderer multipleSeriesRenderer = new XYMultipleSeriesRenderer();
        multipleSeriesRenderer.addSeriesRenderer(getSeriesRenderer());

        setOptionsGraph(multipleSeriesRenderer, cursor);

        return ChartFactory.getLineChartIntent(context, multipleSeriesDatasetdataset, multipleSeriesRenderer);
    }

    private void setOptionsGraph(XYMultipleSeriesRenderer multipleSeriesRenderer, Cursor cursor)
    {
        cursor.moveToFirst();
        multipleSeriesRenderer.setChartTitle(cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME)));

        multipleSeriesRenderer.setXTitle(DAYS);

        multipleSeriesRenderer.setZoomButtonsVisible(true);

        multipleSeriesRenderer.setShowGrid(true);

        int[]margins = {MARGINS, MARGINS, MARGINS, MARGINS};
        multipleSeriesRenderer.setMargins(margins);

        setColors(multipleSeriesRenderer);
        setTexSize(multipleSeriesRenderer);
    }

    private void setColors(XYMultipleSeriesRenderer multipleSeriesRenderer)
    {
        multipleSeriesRenderer.setLabelsColor(Color.RED);
        multipleSeriesRenderer.setXLabelsColor(Color.RED);
        multipleSeriesRenderer.setYLabelsColor(0, Color.RED);
        multipleSeriesRenderer.setAxesColor(Color.parseColor(COLOR_ORANGE));

        multipleSeriesRenderer.setApplyBackgroundColor(true);
        multipleSeriesRenderer.setBackgroundColor(Color.parseColor(COLOR_WHITE));
    }

    private void setTexSize(XYMultipleSeriesRenderer multipleSeriesRenderer)
    {
        multipleSeriesRenderer.setChartTitleTextSize(TEXT_SIZE_LEGEND);
        multipleSeriesRenderer.setAxisTitleTextSize(TEXT_SIZE_LEGEND);

        multipleSeriesRenderer.setLabelsTextSize(TEXT_SIZE_LABELS);
        multipleSeriesRenderer.setLegendTextSize(TEXT_SIZE_LEGEND);
    }


    private XYSeries getSeries(Cursor cursor, String selectedColumnName)
    {
        XYSeries series = new XYSeries(getLegend(selectedColumnName));
        if (cursor.moveToFirst())
        {
            do
            {
                series.add(getIntDate(cursor), cursor.getInt(cursor.getColumnIndex(selectedColumnName)));
            }
            while (cursor.moveToNext());
        }
        return series;
    }

    private String getLegend(String selectedColumnName)
    {
        return ("crashes_count".equals(selectedColumnName)) ? CRASHES_GRAPH : LOADS_GRAPH;
    }


    private XYSeriesRenderer getSeriesRenderer()
    {
        XYSeriesRenderer seriesRenderer = new XYSeriesRenderer();
        seriesRenderer.setColor(Color.parseColor(COLOR_ORANGE));
        seriesRenderer.setPointStyle(PointStyle.CIRCLE);
        seriesRenderer.setFillPoints(true);
        seriesRenderer.setDisplayChartValuesDistance(0);
        seriesRenderer.setDisplayChartValues(true);
        seriesRenderer.setChartValuesTextSize(TEXT_SIZE_LABELS);
        return seriesRenderer;
    }

    private int getIntDate(Cursor cursor)
    {
        String stringDate = DateTimeUtils.getFormattedDate(new Date(cursor.
                getLong(cursor.getColumnIndex(DailyStatisticsItem.COLUMN_DATE))), DATE_FORMAT);
        int intDate = Integer.valueOf(stringDate);
        return intDate;
    }
}
