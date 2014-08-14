package intexsoft.by.crittercismapi.data.remote.service;

import android.content.Context;
import android.util.Log;
import intexsoft.by.crittercismapi.CrittercismApplication;
import org.androidannotations.annotations.EBean;
import org.androidannotations.api.rest.RestErrorHandler;
import org.springframework.web.client.RestClientException;

/**
 * Created by Евгений on 13.08.2014.
 */
@EBean
public class CrittercismRestErrorHandler implements RestErrorHandler
{
	@Override
	public void onRestClientExceptionThrown(RestClientException e)
	{
		Log.d("****************errorRest: ", e.getMessage());
	}

	private static Context getContext()
	{
		return CrittercismApplication.getApplication();
	}
}
