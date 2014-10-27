package intexsoft.by.crittercismapi.ui.presenter;

import android.database.Cursor;

import intexsoft.by.crittercismapi.ui.view.GraphActivityView;

/**
 * Created by vadim on 27.10.2014.
 */
public interface GraphActivityPresenter extends BasePresenter<GraphActivityView>
{
    void buildGraph(Cursor cursor);
}
