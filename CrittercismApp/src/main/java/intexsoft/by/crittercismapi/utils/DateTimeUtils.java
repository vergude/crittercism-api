package intexsoft.by.crittercismapi.utils;

import java.util.Date;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
public class DateTimeUtils
{
	private DateTimeUtils()
	{
	}

	public static long getCurrentDateLong()
	{
		return (new Date()).getTime();
	}
}
