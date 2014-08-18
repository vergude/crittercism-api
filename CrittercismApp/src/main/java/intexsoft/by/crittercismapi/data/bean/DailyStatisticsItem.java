package intexsoft.by.crittercismapi.data.bean;

import nl.qbusict.cupboard.annotation.Column;
import nl.qbusict.cupboard.annotation.Ignore;

import java.util.Date;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */

public class DailyStatisticsItem extends Entity
{
	public static final String COLUMN_APP_REMOTE_ID = "app_remote_id";
	public static final String COLUMN_CRASHES_COUNT = "crashes_count";
	public static final String COLUMN_APP_LOADS_COUNT = "app_loads_count";
	public static final String COLUMN_DATE = "date";

	@Ignore
	private CrittercismApp application;

	@Column(COLUMN_APP_REMOTE_ID)
	private String appRemoteId;

	@Column(COLUMN_CRASHES_COUNT)
	private int crashesCount;

	@Column(COLUMN_APP_LOADS_COUNT)
	private int appLoadsCount;

	@Column(COLUMN_DATE)
    private Date date;

	public DailyStatisticsItem()
	{
	}

    public DailyStatisticsItem(CrittercismApp application, int crashesCount, int appLoadsCount)
	{
		this.application = application;
		this.appRemoteId = application.getRemoteId();
		this.crashesCount = crashesCount;
		this.appLoadsCount = appLoadsCount;
        this.date = new Date();
	}

	public CrittercismApp getApplication()
	{
		return application;
	}

	public void setApplication(CrittercismApp application)
	{
		this.application = application;
	}

	public int getCrashesCount()
	{
		return crashesCount;
	}

	public void setCrashesCount(int crashesCount)
	{
		this.crashesCount = crashesCount;
	}

	public int getAppLoadsCount()
	{
		return appLoadsCount;
	}

	public void setAppLoadsCount(int appLoadsCount)
	{
		this.appLoadsCount = appLoadsCount;
	}

	public double getErrorsPercent()
	{
		return ((double) crashesCount / appLoadsCount);
	}

	public String getAppRemoteId()
	{
		return appRemoteId;
	}

	public void setAppRemoteId(String appRemoteId)
	{
		this.appRemoteId = appRemoteId;
	}

	public Date getDate()
	{
		return date;
	}

	public void setDate(Date date)
	{
		this.date = date;
	}
}
