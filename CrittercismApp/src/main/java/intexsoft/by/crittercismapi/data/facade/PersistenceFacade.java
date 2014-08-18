package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.net.Uri;
import intexsoft.by.crittercismapi.data.CrittercismAPIContentProvider;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.bean.Entity;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import nl.qbusict.cupboard.CupboardFactory;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.util.List;

/**
 * Created by dmitry.lomako on 09.07.2014.
 */

@EBean(scope = EBean.Scope.Singleton)
public class PersistenceFacade
{

    @RootContext
    Context context;

    public static PersistenceFacade getInstance(Context context) {
        return PersistenceFacade_.getInstance_(context);
    }

	/*
    public Cursor getLocations() {
        ThreadUtils.checkAndThrowIfUIThread();

        return getLocations(null, null, null, Location.COLUMN_TITLE + " ASC");
    }

    public Cursor getLocations(String[] projection, String selection, String[] args, String sortOrder) {
        return getCursor(ManageEverywhereContentProvider.LOCATION_URI, DailyStatisticsItem.class, projection, selection, args, sortOrder);
    }

    public Cursor getTags(String[] projection, String selection, String[] args, String sortOrder) {
        return getCursor(ManageEverywhereContentProvider.TAG_URI, Tag.class, projection, selection, args, sortOrder);
    }

    private Cursor getCursor(Uri uri, Class<? extends Entity> entityClazz, String[] projection, String selection, String[] args, String sortOrder) {
        ThreadUtils.checkAndThrowIfUIThread();

        return CupboardFactory.cupboard().withContext(context).query(uri, entityClazz).withProjection(projection).
                withSelection(selection, args).orderBy(sortOrder).getCursor();
    }

    public void saveApplications(List<CrittercismApp> applications) {
        CupboardFactory.cupboard().withContext(context).put(ManageEverywhereContentProvider.LOCATION_URI, CrittercismApp.class, applications);
    }

    public List<Record> getNotUploadedRecords() {
        ThreadUtils.checkAndThrowIfUIThread();
        return CupboardFactory.cupboard().withContext(context)
                .query(ManageEverywhereContentProvider.RECORD_URI, Record.class)
                .withSelection(Record.COLUMN_SENT + " = ?", new String[]{"1"})
                .list();
    }

    private <T extends Entity> void save(Uri uri, Class<T> clazz, T toSave) {
        CupboardFactory.cupboard().withContext(context).put(uri, clazz, toSave);
    }

    private <T extends Entity> Uri save(Uri uri, T toSave) {
       return CupboardFactory.cupboard().withContext(context).put(uri, toSave);
    }*/

	public void saveDailyStatisticsItems(List<DailyStatisticsItem> statisticsItems) {
		ThreadUtils.checkAndThrowIfUIThread();
		CupboardFactory.cupboard().withContext(context).put(CrittercismAPIContentProvider.DAILY_STATISTIC_URI, DailyStatisticsItem.class, statisticsItems);
	}

	private <T extends Entity> Uri save(Uri uri, T toSave) {
		return CupboardFactory.cupboard().withContext(context).put(uri, toSave);
	}

}
