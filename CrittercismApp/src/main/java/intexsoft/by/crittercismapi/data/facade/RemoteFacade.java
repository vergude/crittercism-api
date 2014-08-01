package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
import android.util.Log;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.bean.CrittercismApp;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequest;
import intexsoft.by.crittercismapi.data.remote.request.GraphRequestInternal;
import intexsoft.by.crittercismapi.data.remote.request.PieRequest;
import intexsoft.by.crittercismapi.data.remote.request.PieRequestInternal;
import intexsoft.by.crittercismapi.data.remote.response.AppSummaryData;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.response.PieResponse;
import intexsoft.by.crittercismapi.data.remote.response.SeriesData;
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
        graphRequestInternal.setDuration(Constants.DURATION_ONE_DAY);

        graphRequest.setParams(graphRequestInternal);

        GraphResponse graphResponse = remoteService.getErrorGraph(graphRequest);
    }

    public List<DailyStatisticsItem> getErrorGraphAllApps(int duration)
    {
        ThreadUtils.checkAndThrowIfUIThread();

        HashMap<String,AppSummaryData> responseApp = remoteService.getApps();

        PieRequest pieRequest = new PieRequest();
        PieRequestInternal pieRequestInternal = new PieRequestInternal();
        pieRequestInternal.setAppIds(responseApp.keySet().toArray(new String[responseApp.keySet().size()]));
        pieRequestInternal.setDuration(duration);
        pieRequestInternal.setGroupBy(Constants.GROUP_BY_APP_ID);
        pieRequestInternal.setGraph(Constants.GRAPH_CRASHES);
        pieRequest.setParams(pieRequestInternal);

		PieResponse pieResponseCrashes = remoteService.getErrorGraphAllApps(pieRequest);

        pieRequestInternal.setGraph(Constants.GRAPH_APPLOADS);
        pieRequest.setParams(pieRequestInternal);

        PieResponse pieResponseAppLoads = remoteService.getErrorGraphAllApps(pieRequest);

		HashMap<String, DailyStatisticsItem> statisticsHashMap = new HashMap<String, DailyStatisticsItem>();

		for(SeriesData seriesData : pieResponseAppLoads.getData().getSlices())
		{
			String appId = seriesData.getLabel();
			CrittercismApp app = new CrittercismApp(seriesData.getLabel(), responseApp.get(appId).getAppName());
			DailyStatisticsItem item = new DailyStatisticsItem(app, 0, seriesData.getValue());

			statisticsHashMap.put(appId, item);
		}

		for(SeriesData seriesData : pieResponseCrashes.getData().getSlices())
		{
			String appId = seriesData.getLabel();
			if (!statisticsHashMap.containsKey(appId))
			{
				CrittercismApp app = new CrittercismApp(seriesData.getLabel(), responseApp.get(appId).getAppName());
				DailyStatisticsItem item = new DailyStatisticsItem(app, seriesData.getValue(), 0);
				statisticsHashMap.put(appId, item);
			}
			else
			{
				statisticsHashMap.get(appId).setCrashesCount(seriesData.getValue());
			}
		}

		return new ArrayList<DailyStatisticsItem>(statisticsHashMap.values());
    }
}
