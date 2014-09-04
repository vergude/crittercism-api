package intexsoft.by.crittercismapi.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
public final class DateTimeUtils
{
	private static final String SQL_DATE_FORMAT = "yyyyMMdd hh:mm:ss";
	private static final int HOUR_DAY = 23;
	private static final int MINUTE_DAY = 59;
	private static final int SECOND_DAY = 59;

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

		calendar.set(Calendar.HOUR_OF_DAY, HOUR_DAY);
		calendar.set(Calendar.MINUTE, MINUTE_DAY);
		calendar.set(Calendar.SECOND, SECOND_DAY);

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


	public static String getFormattedDate(Date date, String dateFormat)
	{
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.getDefault());

		return sdf.format(date);
	}
}
