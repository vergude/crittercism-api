package intexsoft.by.crittercismapi.data.remote.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Created by Евгений on 21.07.2014.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class AppSummaryData
{

	private Object links;
	private String appName;
	private String appType;
	private String[] appVersions;
	private Integer crashPercent;
	private Integer dau;
	private Integer latency;
	private String latestAppStoreReleaseDate;
	private String latestVersionString;
	private String linkToAppStore;
	private String iconURL;
	private Integer mau;
	private Integer rating;
	private String role;

	public Object getLinks()
	{
		return links;
	}

	public void setLinks(Object links)
	{
		this.links = links;
	}

	public String getAppName()
	{
		return appName;
	}

	public void setAppName(String appName)
	{
		this.appName = appName;
	}

	public String getAppType()
	{
		return appType;
	}

	public void setAppType(String appType)
	{
		this.appType = appType;
	}

	public String[] getAppVersions()
	{
		return appVersions;
	}

	public void setAppVersions(String[] appVersions)
	{
		this.appVersions = appVersions;
	}

	public Integer getCrashPercent()
	{
		return crashPercent;
	}

	public void setCrashPercent(Integer crashPercent)
	{
		this.crashPercent = crashPercent;
	}

	public Integer getDau()
	{
		return dau;
	}

	public void setDau(Integer dau)
	{
		this.dau = dau;
	}

	public Integer getLatency()
	{
		return latency;
	}

	public void setLatency(Integer latency)
	{
		this.latency = latency;
	}

	public String getLatestAppStoreReleaseDate()
	{
		return latestAppStoreReleaseDate;
	}

	public void setLatestAppStoreReleaseDate(String latestAppStoreReleaseDate)
	{
		this.latestAppStoreReleaseDate = latestAppStoreReleaseDate;
	}

	public String getLatestVersionString()
	{
		return latestVersionString;
	}

	public void setLatestVersionString(String latestVersionString)
	{
		this.latestVersionString = latestVersionString;
	}

	public String getLinkToAppStore()
	{
		return linkToAppStore;
	}

	public void setLinkToAppStore(String linkToAppStore)
	{
		this.linkToAppStore = linkToAppStore;
	}

	public String getIconURL()
	{
		return iconURL;
	}

	public void setIconURL(String iconURL)
	{
		this.iconURL = iconURL;
	}

	public Integer getMau()
	{
		return mau;
	}

	public void setMau(Integer mau)
	{
		this.mau = mau;
	}

	public Integer getRating()
	{
		return rating;
	}

	public void setRating(Integer rating)
	{
		this.rating = rating;
	}

	public String getRole()
	{
		return role;
	}

	public void setRole(String role)
	{
		this.role = role;
	}
}
