package intexsoft.by.crittercismapi.manager;

import android.content.Context;
import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.data.remote.response.GraphResponse;
import intexsoft.by.crittercismapi.data.remote.response.SeriesData;
import intexsoft.by.crittercismapi.utils.DateTimeUtils;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by anastasya.konovalova on 21.07.2014.
 */
@EBean(scope = EBean.Scope.Singleton)
public class ErrorGraphManager
{
	@Bean
	RemoteFacade remoteFacade;

	@RootContext
	Context context;

	public List<DailyStatisticsItem> getMonthlyStatistics(String appId, Date fromDate)
	{
		LinkedHashMap<Date, DailyStatisticsItem> appErrorDetailsMap = new LinkedHashMap<Date, DailyStatisticsItem>();

		GraphResponse graphResponseItemsAppCrashes = remoteFacade.getErrorGraphOneApp(appId, Constants.GRAPH_CRASHES);
		processCrashes(graphResponseItemsAppCrashes, appErrorDetailsMap, new DailyCrashesItemProcessor(), appId, fromDate);


		GraphResponse graphResponseItemsAppLoads = remoteFacade.getErrorGraphOneApp(appId, Constants.GRAPH_APPLOADS);
		processCrashes(graphResponseItemsAppLoads, appErrorDetailsMap, new DailyLoadsItemProcessor(), appId, fromDate);
		
		return new ArrayList<DailyStatisticsItem>(appErrorDetailsMap.values());
	}

	private void processCrashes(GraphResponse graphResponse, LinkedHashMap<Date, DailyStatisticsItem> appErrorDetailsMap,
								DailyItemProcessor dailyItemProcessor, String appId, Date fromDate)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

		SeriesData[] seriesDataC = graphResponse.getData().getSeries();
		Integer[] crashesCountC = seriesDataC[0].getPoints();

		try
		{
			Date startDate = dateFormat.parse(graphResponse.getData().getStart());
			Date endDate = dateFormat.parse(graphResponse.getData().getEnd());

			Calendar startDateCalendar = Calendar.getInstance();
			startDateCalendar.setTime(startDate);

			//We don't need the data from last night
			Calendar endDateCalendar = Calendar.getInstance();
			endDateCalendar.setTime(endDate);
			endDateCalendar.add(Calendar.DATE, -1);

			int step = 0;

			if (fromDate != null)
			{
				Calendar fromDateCalendar = DateTimeUtils.getStartOfDay(fromDate);
				fromDate = fromDateCalendar.getTime();

				if (fromDate.after(startDate))
				{
					do
					{
						startDateCalendar.add(Calendar.DATE, 1);
						step++;
					}
					while (startDate.before(fromDate));
				}
			}

			do
			{
				dailyItemProcessor.processItem(appErrorDetailsMap, setTime(graphResponse, startDateCalendar), crashesCountC[step], appId);
				step++;
			}
			while (startDateCalendar.getTime().before(endDateCalendar.getTime()));

		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	private Date setTime(GraphResponse graphResponse, Calendar calendar)
	{
		calendar.add(Calendar.SECOND, graphResponse.getData().getInterval());
		return calendar.getTime();
	}


	interface DailyItemProcessor
	{
		void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count, String appId);
	}

	class DailyCrashesItemProcessor implements DailyItemProcessor
	{
		@Override
		public void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count, String appId)
		{
			DailyStatisticsItem dailyStatisticsItem = new DailyStatisticsItem();
			dailyStatisticsItem.setCrashesCount(count);
			dailyStatisticsItem.setDate(date);
			dailyStatisticsItem.setAppRemoteId(appId);

			dataDailyStatisticsItemMap.put(date, dailyStatisticsItem);
		}
	}

	class DailyLoadsItemProcessor implements DailyItemProcessor
	{

		@Override
		public void processItem(Map<Date, DailyStatisticsItem> dataDailyStatisticsItemMap, Date date, int count, String appId)
		{
			if (!dataDailyStatisticsItemMap.containsKey(date))
			{
				DailyStatisticsItem dailyStatisticsItem = new DailyStatisticsItem();
				dailyStatisticsItem.setAppLoadsCount(count);
				dailyStatisticsItem.setAppRemoteId(appId);

				dataDailyStatisticsItemMap.put(date, dailyStatisticsItem);
			}
			else
			{
				dataDailyStatisticsItemMap.get(date).setAppLoadsCount(count);
			}
		}
	}
}
