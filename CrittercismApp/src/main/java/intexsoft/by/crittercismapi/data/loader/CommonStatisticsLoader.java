package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import intexsoft.by.crittercismapi.data.db.FastStatisticItem;
import intexsoft.by.crittercismapi.data.db.StatisticType;
import intexsoft.by.crittercismapi.data.db.TimeStatisticContainer;
import intexsoft.by.crittercismapi.data.db.TimeType;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade;
import intexsoft.by.crittercismapi.data.facade.PersistenceFacade_;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by anastasya.konovalova on 27.08.2014.
 */
public class CommonStatisticsLoader extends OneTimeLoader<List<TimeStatisticContainer>>
{
	private static final String COLUMN_CRASHES = "crashes_count";
	private static final String COLUMN_LOADS = "app_loads_count";
	private static final String COLUMN_ERROR = "CAST (crashes_count AS REAL)/(CAST (app_loads_count AS REAL))";

	private PersistenceFacade persistenceFacade;

	private Date date;

	private List<TimeStatisticContainer> timeStatisticContainers;

	public CommonStatisticsLoader(Context context)
	{
		super(context);
	}

	@Override
	public List<TimeStatisticContainer> loadInBackground()
	{
		persistenceFacade = PersistenceFacade_.getInstance_(getContext());
		timeStatisticContainers = new ArrayList<TimeStatisticContainer>();

		date = new Date();
		for ( TimeType timeType : TimeType.values())
		{
			TimeStatisticContainer statisticContainer = new TimeStatisticContainer();
			statisticContainer.setTimeType(getContext().getString(timeType.getTimeType()));
			statisticContainer.setFastStatisticItemList(getFastStatisticList(getDate(date,
					getContext().getResources().getInteger(timeType.getDaysCount()))));

			timeStatisticContainers.add(statisticContainer);
		}

		return timeStatisticContainers;
	}

	private List<FastStatisticItem> getFastStatisticList(long time)
	{
		List<FastStatisticItem> fastStatisticItemList = new ArrayList<FastStatisticItem>();

		fastStatisticItemList.add(persistenceFacade.getFastStatisticItem(String.valueOf(time),
				StatisticType.DOWNLOAD_LEADER));

		fastStatisticItemList.add(persistenceFacade.getFastStatisticItem(String.valueOf(time),
				StatisticType.CRASHES_COUNT));

    	fastStatisticItemList.add(persistenceFacade.getFastStatisticItem(String.valueOf(time),
				StatisticType.ERROR_PERCENT));

		return fastStatisticItemList;
	}

	private long getDate(Date dat, int value)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dat);
		if(value == 1)
		{
			return 1;
		}
		else
		{
			calendar.add(Calendar.DAY_OF_MONTH, value);
			return calendar.getTime().getTime();
		}

	}
}
