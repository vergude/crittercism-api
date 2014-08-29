package intexsoft.by.crittercismapi.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import nl.qbusict.cupboard.CupboardFactory;

public class CrittercismAppSqliteOpenHelper extends SQLiteOpenHelper
{

	private static final String DB_NAME = "crittercism.db";
	private static final int DB_VERSION = 1;

	private static CrittercismAppSqliteOpenHelper INSTANCE;

	public static CrittercismAppSqliteOpenHelper getInstance(Context context)
	{
		if (INSTANCE == null)
		{
			synchronized (CrittercismAppSqliteOpenHelper.class)
			{
				if (INSTANCE == null)
				{
					INSTANCE = new CrittercismAppSqliteOpenHelper(context);
				}
			}
		}
		return INSTANCE;
	}

	public CrittercismAppSqliteOpenHelper(Context context)
	{
		super(context, DB_NAME, null, DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		CupboardFactory.cupboard().withDatabase(db).createTables();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		CupboardFactory.cupboard().withDatabase(db).upgradeTables();
	}
}
