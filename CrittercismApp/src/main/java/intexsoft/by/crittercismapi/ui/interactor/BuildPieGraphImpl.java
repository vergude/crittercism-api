package intexsoft.by.crittercismapi.ui.interactor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.Random;

import intexsoft.by.crittercismapi.data.bean.CrittercismApp;

/**
 * Created by vadim on 31.10.2014.
 */

public class BuildPieGraphImpl implements BuildGraphInteractor
{

    private int appCount;
    private static final int MAX_COLOR = 255;
    private static final int TEXT_SIZE_LABELS = 15;
    private static final int TEXT_SIZE_LEGEND = 20;

    @Override
    public Intent buildGraph(Cursor cursor, String selectedColumnName, Context context)
    {
        CategorySeries series = getSeries(cursor, selectedColumnName);
        DefaultRenderer renderer = getDefaultRenderer();

        return ChartFactory.getPieChartIntent(context, series, renderer, "Pie Graph");
    }

    public CategorySeries getSeries(Cursor cursor, String selectedColumnName)
    {
        CategorySeries series = new CategorySeries("Applications");
        if (cursor.moveToFirst())
        {
            do
            {
                series.add(cursor.getString(cursor.getColumnIndex(CrittercismApp.COLUMN_NAME)),
                        cursor.getInt(cursor.getColumnIndex(selectedColumnName)));
                appCount++;
            }
            while (cursor.moveToNext());
        }
        return series;
    }

    public DefaultRenderer getDefaultRenderer()
    {
        DefaultRenderer defaultRenderer = new DefaultRenderer();
        for (int count = 0; count < appCount; count++)
        {
            SimpleSeriesRenderer simpleSeriesRenderer = new SimpleSeriesRenderer();
            simpleSeriesRenderer.setColor(getRandomColor());
            defaultRenderer.addSeriesRenderer(simpleSeriesRenderer);
            setGraphOptions(defaultRenderer);
        }
        return defaultRenderer;
    }

    public void setGraphOptions(DefaultRenderer defaultRenderer)
    {
        defaultRenderer.setChartTitle("Month Statistics");
        defaultRenderer.setChartTitleTextSize(TEXT_SIZE_LEGEND);

        defaultRenderer.setZoomButtonsVisible(true);

        defaultRenderer.setApplyBackgroundColor(true);
        defaultRenderer.setBackgroundColor(Color.BLACK);

        defaultRenderer.setLabelsTextSize(TEXT_SIZE_LABELS);
        defaultRenderer.setLabelsColor(Color.RED);

        defaultRenderer.setLegendTextSize(TEXT_SIZE_LEGEND);
    }

    public int getRandomColor()
    {
        Random random = new Random();
        int r = random.nextInt(MAX_COLOR);
        int g = random.nextInt(MAX_COLOR);
        int b = random.nextInt(MAX_COLOR);

        return Color.rgb(r, g, b);
    }
}
