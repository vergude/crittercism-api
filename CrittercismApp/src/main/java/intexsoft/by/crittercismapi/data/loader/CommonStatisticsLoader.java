package intexsoft.by.crittercismapi.data.loader;

import android.content.Context;
import intexsoft.by.crittercismapi.data.loader.data.CommonStatisticsData;

/**
 * Created by anastasya.konovalova on 27.08.2014.
 */
public class CommonStatisticsLoader extends OneTimeLoader<CommonStatisticsData>
{
	public CommonStatisticsLoader(Context context)
	{
		super(context);
	}

	@Override
	public CommonStatisticsData loadInBackground()
	{
		CommonStatisticsData commonStatisticsData = new CommonStatisticsData();
		commonStatisticsData.setMostCrashesByMonthAppName("Dentsply Android");

		return commonStatisticsData;
	}
}
