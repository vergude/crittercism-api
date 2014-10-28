package intexsoft.by.crittercismapi.ui.interactor;

import android.database.Cursor;

/**
 * Created by vadim on 27.10.2014.
 */
public interface BuildGraphInteractor
{
    void buildGraph(Cursor cursor, String selectedColumnName, OnBuildGraphFinishedListener listener);
}
