package intexsoft.by.crittercismapi.data.bean;

import nl.qbusict.cupboard.annotation.Column;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */
public class CrittercismApp extends Entity
{
	public static final String COLUMN_REMOTE_ID = "remote_id";
	public static final String COLUMN_NAME = "name";
	public static final String COLUMN_USER_LOGIN = "user_login";

	@Column(COLUMN_REMOTE_ID)
	private String remoteId;

	@Column(COLUMN_NAME)
	private String name;

	@Column(COLUMN_USER_LOGIN)
	private String userLogin;

	public CrittercismApp()
	{
	}

	public CrittercismApp(String remoteId, String name)
	{
		this.remoteId = remoteId;
		this.name = name;
	}

	public String getRemoteId()
	{
		return remoteId;
	}

	public void setRemoteId(String remoteId)
	{
		this.remoteId = remoteId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getUserLogin()
	{
		return userLogin;
	}

	public void setUserLogin(String userLogin)
	{
		this.userLogin = userLogin;
	}
}
