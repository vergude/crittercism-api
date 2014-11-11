package intexsoft.by.crittercismapi.data.db;

import intexsoft.by.crittercismapi.R;

/**
 * Created by Евгений on 24.10.2014.
 */

public enum TimeType
{
	ALL_TIME(R.integer.all_time_value, R.string.all_time_type),
	ONE_MONTH(R.integer.month_time_value, R.string.month_time_type),
	LAST_NIGHT(R.integer.night_time_value, R.string.night_time_type);

	private int daysCount;
	private int timeType;

	public int getTimeType()
	{
		return timeType;
	}

	TimeType(int daysCount, int timeType)
	{
		this.daysCount = daysCount;
		this.timeType = timeType;
	}

	public int getDaysCount()
	{
		return daysCount;
	}
};

