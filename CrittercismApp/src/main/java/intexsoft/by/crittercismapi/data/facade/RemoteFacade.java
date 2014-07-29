package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.HashMap;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequest;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequestInternal;
import intexsoft.by.crittercismapi.data.remote.request.PieRequest;
import intexsoft.by.crittercismapi.data.remote.request.PieRequestInternal;
import intexsoft.by.crittercismapi.data.remote.response.AppSummaryData;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.response.PieResponse;
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

	public void getApps()
    {
		ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> response = remoteService.getApps();

        Log.d("**********", response.size() + "");
	}

    public void getErrorGraphOneApp()
    {
        ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> responseApp = remoteService.getApps();

        GraphRequest graphRequest = new GraphRequest();
        GraphRequestInternal graphRequestInternal = new GraphRequestInternal();
        graphRequestInternal.setApplds(responseApp.keySet().toArray(new String [responseApp.keySet().size()]));
        graphRequestInternal.setGraph(Constants.GRAPH_CRASHES);
        graphRequestInternal.setDuration(Constants.DURATION);

        graphRequest.setParams(graphRequestInternal);

        GraphResponse graphResponse = remoteService.getErrorGraph(graphRequest);
    }

    public void getErrorGraphAllApps()
    {
        ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> responseApp = remoteService.getApps();

        PieRequest pieRequest = new PieRequest();
        PieRequestInternal pieRequestInternal = new PieRequestInternal();
        pieRequestInternal.setAppId(responseApp.keySet().iterator().next());
        pieRequestInternal.setDuration(Constants.DURATION);
        pieRequestInternal.setGroupBy(Constants.GROUP_BY);
        pieRequestInternal.setGraph(Constants.GRAPH_CRASHES);
        pieRequest.setParams(pieRequestInternal);

        PieResponse pieResponseCrashes = remoteService.getErrorGraphAllApps(pieRequest);

        pieRequestInternal.setGraph(Constants.GRAPH_APPLOADS);
        pieRequest.setParams(pieRequestInternal);

        PieResponse pieResponseAppLoads = remoteService.getErrorGraphAllApps(pieRequest);
    }
}
