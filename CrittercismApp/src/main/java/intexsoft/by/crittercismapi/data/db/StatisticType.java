package intexsoft.by.crittercismapi.data.db;

import intexsoft.by.crittercismapi.R;

/**
 * Created by Евгений on 24.10.2014.
 */

public enum StatisticType
{
		DOWNLOAD_LEADER(R.string.download_leader_type, R.string.download_column_name)
				{
					@Override
					public String formatStatisticsValue(float value)
					{
						return String.format("%.0f", value);
					}
				},
		CRASHES_COUNT(R.string.crashes_count_type, R.string.crashes_column_name)
				{
					@Override
					public String formatStatisticsValue(float value)
					{
						return String.format("%.0f", value);
					}
				},
		ERROR_PERCENT(R.string.error_percent_type, R.string.error_column_name)
				{
					@Override
					public String formatStatisticsValue(float value)
					{
						return String.format("%.3f", value);
					}
				};

	private int statisticType;
	private int columnName;

	public int getColumnName()
	{
		return columnName;
	}

	StatisticType(int statisticType, int columnName)
	{
		this.statisticType = statisticType;
		this.columnName = columnName;
	}

	public abstract String formatStatisticsValue(float value);

	public int getStatisticType()
	{
		return statisticType;
	}
}
