package intexsoft.by.crittercismapi.data.bean;

import java.io.Serializable;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */
public class DailyStatisticsItem implements Serializable
{
	private long id;

	private CrittercismApp application;

	private int crashesCount;

	private int appLoadsCount;

    private String date;

	public DailyStatisticsItem()
	{
	}

    public DailyStatisticsItem(CrittercismApp application, int crashesCount, int appLoadsCount,String date)
	{
		this.application = application;
		this.crashesCount = crashesCount;
		this.appLoadsCount = appLoadsCount;
        this.date=date;
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

    public String getDate()
    {
        return date;
    }

    public void setDate(String date)
    {
        this.date = date;
    }
}
