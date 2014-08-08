package intexsoft.by.crittercismapi.utils;

import android.os.Looper;

public final class ThreadUtils
{
	private ThreadUtils()
	{

	}

	public static void checkAndThrowIfUIThread()
	{
		if (Looper.myLooper() == Looper.getMainLooper())
		{
			throw new RuntimeException("Method cannot be called from UI thread.");
		}
	}

	public static void checkAndThrowIfNonUIThread()
	{
		if (Looper.myLooper() != Looper.getMainLooper())
		{
			throw new RuntimeException("Method must be called from UI thread.");
		}
	}
}