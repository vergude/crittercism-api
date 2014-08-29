package intexsoft.by.crittercismapi.data.loader;

import android.content.AsyncTaskLoader;
import android.content.Context;

public abstract class OneTimeLoader<T> extends AsyncTaskLoader<T>
{
	protected T data;

	public OneTimeLoader(Context context)
	{
		super(context);
	}

	@Override
	protected void onStartLoading()
	{
		if (data != null)
		{
			// If we currently have a result available, deliver it
			// immediately.
			deliverResult(data);
		}
		else
		{
			forceLoad();
		}
	}

	@Override
	protected void onStopLoading()
	{
		// Attempt to cancel the current load task if possible.
		cancelLoad();
	}

	@Override
	public void deliverResult(T tData)
	{
		this.data = tData;
		if (isStarted() && tData != null)
		{
			// If the Loader is currently started, we can immediately
			// deliver its results.
			super.deliverResult(tData);
		}
	}
}
