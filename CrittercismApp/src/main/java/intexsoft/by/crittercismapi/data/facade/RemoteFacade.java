package intexsoft.by.crittercismapi.data.facade;

import android.content.Context;
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
import intexsoft.by.crittercismapi.data.remote.service.CrittercismRestErrorHandler;
import intexsoft.by.crittercismapi.utils.ThreadUtils;
import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.rest.RestService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@EBean(scope = EBean.Scope.Singleton)
public class RemoteFacade
{
	@Bean
	CrittercismRestErrorHandler errorHandler;

	@Bean
	PersistenceFacade persistenceFacade;

	@RootContext
	protected Context context;

	@RestService
	protected CrittercismAPIService remoteService;

	@AfterInject
	void afterInject()
	{
		remoteService.setRestErrorHandler(errorHandler);
	}

	public static RemoteFacade getInstance(Context context)
	{
		return RemoteFacade_.getInstance_(context);
	}

	@NotNull
	public List<CrittercismApp> getAppsForUser(String userLogin)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		HashMap<String, AppSummaryData> response = remoteService.getApps();

		if (response == null)
		{
			return Collections.EMPTY_LIST;
		}

		List<CrittercismApp> appsList = new ArrayList<CrittercismApp>();

		for (Map.Entry<String, AppSummaryData> entry : response.entrySet())
		{
			CrittercismApp app = new CrittercismApp(entry.getKey(), entry.getValue().getAppName());
			app.setUserLogin(userLogin);

			appsList.add(app);
		}

		return appsList;
	}

	public GraphResponse getErrorGraphOneApp(String appId, String graph)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		GraphRequest graphRequest = new GraphRequest();
		GraphRequestInternal graphRequestInternal = new GraphRequestInternal();
		graphRequestInternal.setAppId(appId);
		graphRequestInternal.setGraph(graph);
		graphRequestInternal.setDuration(Constants.DURATION_ONE_MONTH);

		graphRequest.setParams(graphRequestInternal);

		GraphResponse graphResponse = remoteService.getErrorGraph(graphRequest);

		return graphResponse;
	}

	public List<DailyStatisticsItem> getErrorGraphAllApps(int duration)
	{
		ThreadUtils.checkAndThrowIfUIThread();

		Map<String, CrittercismApp> appsMap = persistenceFacade.getAppsMapByCurrentUser();

		if (responseApp == null)
		{
			return null;
		}

		PieRequest pieRequest = new PieRequest();
		PieRequestInternal pieRequestInternal = new PieRequestInternal();
		pieRequestInternal.setAppIds(appsMap.keySet().toArray(new String[appsMap.keySet().size()]));
		pieRequestInternal.setDuration(duration);
		pieRequestInternal.setGroupBy(Constants.GROUP_BY_APP_ID);
		pieRequestInternal.setGraph(Constants.GRAPH_CRASHES);
		pieRequest.setParams(pieRequestInternal);

		PieResponse pieResponseCrashes = remoteService.getErrorGraphAllApps(pieRequest);
		if (pieResponseCrashes == null)
		{
			return null;
		}

		pieRequestInternal.setGraph(Constants.GRAPH_APPLOADS);
		pieRequest.setParams(pieRequestInternal);

		PieResponse pieResponseAppLoads = remoteService.getErrorGraphAllApps(pieRequest);

		if (pieResponseAppLoads == null)
		{
			return null;
		}

		HashMap<String, DailyStatisticsItem> statisticsHashMap = new HashMap<String, DailyStatisticsItem>();

		for (SeriesData seriesData : pieResponseAppLoads.getData().getSlices())
		{
			String appId = seriesData.getLabel();
			DailyStatisticsItem item = new DailyStatisticsItem(appsMap.get(appId), 0, seriesData.getValue());

			statisticsHashMap.put(appId, item);
		}

		for (SeriesData seriesData : pieResponseCrashes.getData().getSlices())
		{
			String appId = seriesData.getLabel();
			if (!statisticsHashMap.containsKey(appId))
			{
				DailyStatisticsItem item = new DailyStatisticsItem(appsMap.get(appId), seriesData.getValue(), 0);
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
