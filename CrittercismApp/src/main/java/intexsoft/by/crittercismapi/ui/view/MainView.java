package intexsoft.by.crittercismapi.ui.view;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.List;

/**
 * Created by Евгений on 22.07.2014.
 */
public interface MainView extends BaseView
{
	void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems);
}
