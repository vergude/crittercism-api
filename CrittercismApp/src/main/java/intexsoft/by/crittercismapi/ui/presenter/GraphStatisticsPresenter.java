package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import android.database.Cursor;

import intexsoft.by.crittercismapi.ui.interactor.BuildGraphInteractor;
import intexsoft.by.crittercismapi.ui.view.GraphStatisticsView;

/**
 * Created by vadim on 25.10.2014.
 */
public interface GraphStatisticsPresenter extends BasePresenter<GraphStatisticsView>
{
    void buildConcreteGraph(Cursor cursor, String selectedColumnName, Context context, BuildGraphInteractor interactor);
}
