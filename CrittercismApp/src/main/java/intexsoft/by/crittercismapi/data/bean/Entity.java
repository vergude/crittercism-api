package intexsoft.by.crittercismapi.data.bean;

import nl.qbusict.cupboard.annotation.Column;

import java.io.Serializable;

/**
 * Created by dmitry.lomako on 09.07.2014.
 */
public abstract class Entity implements Serializable
{
    public static final String COLUMN_ID = "_id";

    @Column(COLUMN_ID)
    protected Long id;

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getId()
	{
		return id;
	}
}
