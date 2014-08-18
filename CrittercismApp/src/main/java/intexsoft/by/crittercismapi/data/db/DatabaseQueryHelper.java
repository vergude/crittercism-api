package intexsoft.by.crittercismapi.data.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import nl.qbusict.cupboard.CupboardFactory;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.jetbrains.annotations.Nullable;

/**
 * Created by dmitry.lomako on 09.07.2014.
 */

@EBean(scope = EBean.Scope.Singleton)
public class DatabaseQueryHelper
{

    @RootContext
    Context context;

    private CrittercismAppSqliteOpenHelper getHelper() {
        return CrittercismAppSqliteOpenHelper.getInstance(context);
    }

    private SQLiteDatabase getReadableDb() {
        return getHelper().getReadableDatabase();
    }

    private SQLiteDatabase getReadWriteDb() {
        return getHelper().getWritableDatabase();
    }

	@Nullable
	public Cursor getDailyStatisticsItem(String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		ThreadUtils.checkAndThrowIfUIThread();

		try {
			Cursor result = CupboardFactory.cupboard().withDatabase(getReadableDb()).query(DailyStatisticsItem.class).
					withProjection(projection).withSelection(selection, selectionArgs).
					orderBy(sortOrder).getCursor();
			return result;
		} catch (SQLException e) {
			handleException(e);
			return null;
		}
	}

    private void handleException(SQLException e) {
		Log.e("DatabaseQueryHelper", e.getMessage());
	}

	public long save(Class<?> clazz, ContentValues values) {
		ThreadUtils.checkAndThrowIfUIThread();
		try {
			return CupboardFactory.cupboard().withDatabase(getReadWriteDb()).put(clazz, values);
		} catch (SQLException e) {
			handleException(e);
			return -1;
		}
	}

	@Nullable
	public Cursor getCursor(Class className, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		ThreadUtils.checkAndThrowIfUIThread();

		try {
			Cursor result = CupboardFactory.cupboard().withDatabase(getReadableDb()).query(className).
					withProjection(projection).withSelection(selection, selectionArgs).
					orderBy(sortOrder).getCursor();
			return result;
		} catch (SQLException e) {
			handleException(e);
			return null;
		}
	}
}
