package intexsoft.by.crittercismapi.data.db;

/**
 * Created by Евгений on 21.10.2014.
 */
public class FastStatisticItem
{
	private String appName;
	private String countResult;
	private String countType;

	public String getCountType()
	{
		return countType;
	}

	public void setCountType(String countType)
	{
		this.countType = countType;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getCountResult()
	{
		return countResult;
	}

	public void setCountResult(String countResult)
	{
		this.countResult = countResult;
	}
}
