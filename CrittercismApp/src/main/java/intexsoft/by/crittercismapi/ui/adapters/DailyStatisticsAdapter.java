package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import intexsoft.by.crittercismapi.ui.adapters.binder.DailyItemViewBinder;

/**
 * Created by vadim on 30.07.2014.
 */
public class DailyStatisticsAdapter extends CursorAdapter
{
	private boolean isAdapterWithName;

	public DailyStatisticsAdapter(Context context, boolean isAdapterWithName)
	{
		super(context, null, 0);
		this.isAdapterWithName = isAdapterWithName;
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent)
	{
		return DailyItemViewBinder.build(context);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor)
	{
		((DailyItemViewBinder) view).bind(cursor, isAdapterWithName);
	}
}
