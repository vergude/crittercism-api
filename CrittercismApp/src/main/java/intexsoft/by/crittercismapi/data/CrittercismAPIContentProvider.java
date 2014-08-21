package intexsoft.by.crittercismapi.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.db.DatabaseQueryHelper;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EProvider;

/**
 * Created by dmitry.lomako on 09.07.2014.
 */

@EProvider
public class CrittercismAPIContentProvider extends ContentProvider {

    private static final String AUTHORITY = "intexsoft.by.crittercismapi";

    public static final Uri DAILY_STATISTIC_URI = Uri.parse("content://" + AUTHORITY + "/DailyStatisticsItem");

	public static final Uri CRITTERCISM_APP_URI = Uri.parse("content://" + AUTHORITY + "/CrittercismApp");

    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    private static final int URI_CODE_DAILY_STATISTIC = 0;

	private static final int URI_CODE_CRITTERCISM_APP = 1;

    @Bean
	DatabaseQueryHelper queryHelper;

    static {
        uriMatcher.addURI(AUTHORITY, "DailyStatisticsItem", URI_CODE_DAILY_STATISTIC);
		uriMatcher.addURI(AUTHORITY, "CrittercismApp", URI_CODE_CRITTERCISM_APP);
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        switch (uriMatcher.match(uri)) {
            case URI_CODE_DAILY_STATISTIC: {
                return queryHelper.getDailyStatisticsItem(projection, selection, selectionArgs, sortOrder);
            }
			case URI_CODE_CRITTERCISM_APP: {
				return queryHelper.getCursor(CrittercismApp.class, projection, selection, selectionArgs, sortOrder);
			}
        }
        return null;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        switch (uriMatcher.match(uri)) {
            case URI_CODE_DAILY_STATISTIC: {
                long id = queryHelper.save(DailyStatisticsItem.class, values);
                return ContentUris.withAppendedId(uri, id);
            }
			case URI_CODE_CRITTERCISM_APP: {
				long id = queryHelper.save(CrittercismApp.class, values);
				return ContentUris.withAppendedId(uri, id);
			}
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        switch (uriMatcher.match(uri)) {
//            case URI_CODE_DAILY_STATISTIC: {
//                return queryHelper.updateTag(values, selection, selectionArgs);
//            }
            default: {
                return 0;
            }
        }
    }

}
