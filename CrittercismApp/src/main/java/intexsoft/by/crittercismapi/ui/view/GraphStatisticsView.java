package intexsoft.by.crittercismapi.ui.view;

import android.content.Intent;

/**
 * Created by vadim on 25.10.2014.
 */

public interface GraphStatisticsView extends BaseView
{
    void dataLoaded();

    void showGraph(Intent intent);
}
