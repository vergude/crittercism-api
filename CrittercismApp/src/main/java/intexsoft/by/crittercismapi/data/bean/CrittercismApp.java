package intexsoft.by.crittercismapi.data.bean;

import java.io.Serializable;

/**
 * Created by anastasya.konovalova on 29.07.2014.
 */
public class CrittercismApp implements Serializable
{
	private String id;

	private String name;

	public CrittercismApp()
	{
	}

	public CrittercismApp(String id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}
}
