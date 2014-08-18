package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import android.content.CursorLoader;
import android.database.Cursor;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper_;

/**
 * Created by anastasya.konovalova on 05.08.2014.
 */
public class DailyStatisticsCursorLoader extends CursorLoader
{
	DatabaseQueryHelper queryHelper;

	public DailyStatisticsCursorLoader(Context context)
	{
		super(context);

		queryHelper = DatabaseQueryHelper_.getInstance_(context);
	}

	@Override
	public Cursor loadInBackground() {
		Cursor cursor = queryHelper.getDailyStatisticsItem(null, null, null, null);

		return cursor;
	}
}
