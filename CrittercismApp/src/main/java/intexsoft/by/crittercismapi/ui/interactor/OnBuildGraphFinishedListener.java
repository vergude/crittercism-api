package intexsoft.by.crittercismapi.ui.interactor;

import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;

/**
 * Created by vadim on 27.10.2014.
 */

public interface OnBuildGraphFinishedListener
{
    void finishBuild(XYMultipleSeriesDataset dataset, XYMultipleSeriesRenderer renderer);
}
