package intexsoft.by.crittercismapi.ui.view;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.List;

/**
 * Created by Евгений on 04.08.2014.
 */
public interface AppDetailsErrorView extends BaseView
{
    String getAppId();
	void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems);
}
