package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.util.Log;
import intexsoft.by.crittercismapi.data.remote.service.CrittercismAPIService;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;


@EBean(scope = EBean.Scope.Singleton)
public class RemoteFacade
{

	@RootContext
	protected Context context;

	@RestService
	protected CrittercismAPIService remoteService;

	public static RemoteFacade getInstance(Context context)
	{
		return RemoteFacade_.getInstance_(context);
	}

	public void getApps() {
		ThreadUtils.checkAndThrowIfUIThread();

		String response = remoteService.getApps();

		Log.d("**********", response);
	}
}
