package intexsoft.by.crittercismapi.data.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */

public class DailyStatisticsItem implements Serializable
{
	private long id;

	private CrittercismApp application;

	private int crashesCount;

	private int appLoadsCount;

    private Date date;

	public DailyStatisticsItem()
	{
	}

    public DailyStatisticsItem(CrittercismApp application, int crashesCount, int appLoadsCount)
	{
		this.application = application;
		this.crashesCount = crashesCount;
		this.appLoadsCount = appLoadsCount;
        this.date = new Date();
	}

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
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

}
