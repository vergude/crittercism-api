package intexsoft.by.crittercismapi.ui.interactor;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;

/**
 * Created by vadim on 27.10.2014.
 */
public interface BuildGraphInteractor
{
    Intent buildGraph(Cursor cursor, String selectedColumnName, Context context);
}
