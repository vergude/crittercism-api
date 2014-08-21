package intexsoft.by.crittercismapi.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
public final class DateTimeUtils
{
	private final static String SQL_DATE_FORMAT = "yyyyMMdd hh:mm:ss";

	private DateTimeUtils()
	{
	}

	public static long getCurrentDateLong()
	{
		return (new Date()).getTime();
	}

	public static String getFormatedEndOfDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);



		return String.valueOf(calendar.getTime().getTime());
	}

	public static String getFormatedStartOfDay(Date date)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);

		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);

		return String.valueOf(calendar.getTime().getTime());
	}
}
