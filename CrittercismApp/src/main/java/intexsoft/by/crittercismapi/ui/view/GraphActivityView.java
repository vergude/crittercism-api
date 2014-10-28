package intexsoft.by.crittercismapi.ui.view;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

/**
 * Created by vadim on 27.10.2014.
 */
public interface GraphActivityView extends BaseView
{
    void dataLoaded();

    void showGraph(XYMultipleSeriesDataset multipleSeriesDataset, XYMultipleSeriesRenderer multipleSeriesRenderer);
}
