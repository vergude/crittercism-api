package intexsoft.by.crittercismapi.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import nl.qbusict.cupboard.CupboardFactory;

public class CrittercismAppSqliteOpenHelper extends SQLiteOpenHelper
{

	private static final String DB_NAME = "crittercism.db";
	private static final int DB_VERSION = 1;

	private static CrittercismAppSqliteOpenHelper crittercismAppSqliteOpenHelper;

	public static CrittercismAppSqliteOpenHelper getInstance(Context context)
	{
		if (crittercismAppSqliteOpenHelper == null)
		{
			synchronized (CrittercismAppSqliteOpenHelper.class)
			{
				if (crittercismAppSqliteOpenHelper == null)
				{
					crittercismAppSqliteOpenHelper = new CrittercismAppSqliteOpenHelper(context);
				}
			}
		}
		return crittercismAppSqliteOpenHelper;
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
