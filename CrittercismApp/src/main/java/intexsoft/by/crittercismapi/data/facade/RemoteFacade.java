package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.HashMap;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.remote.entity.AppSummaryData;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequest;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequestInternal;
import intexsoft.by.crittercismapi.data.remote.service.CrittercismAPIService;
import intexsoft.by.crittercismapi.utils.ThreadUtils;


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

        HashMap<String,AppSummaryData> response = remoteService.getApps();

        Log.d("**********", response.size() + "");
	}

    public void getErrorGraph() {
        ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> responseApp = remoteService.getApps();

        GraphRequest graphRequest = new GraphRequest();
        GraphRequestInternal graphRequestInternal = new GraphRequestInternal();
        graphRequestInternal.setAppId(responseApp.keySet().iterator().next());
        graphRequestInternal.setGraph(Constants.GRAPH);
        graphRequestInternal.setDuration(Constants.DURATION);

        graphRequest.setParams(graphRequestInternal);

        String response = remoteService.getErrorGraph(graphRequest);

        Log.d("**********", response + "");
    }
}
