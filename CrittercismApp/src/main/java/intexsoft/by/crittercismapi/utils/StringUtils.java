package intexsoft.by.crittercismapi.utils;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
public final class StringUtils
{
	private static final String EMPTY = "";

	public static boolean isNotEmpty(String value)
	{
		return value != null && !EMPTY.equals(value);
	}

	public static boolean isEmpty(String value)
	{
		return value == null || EMPTY.equals(value);
	}
}
