package intexsoft.by.crittercismapi.ui.view;

import java.util.List;

import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

/**
 * Created by Евгений on 22.07.2014.
 */
public interface MainView extends BaseView
{
	void setDailyStatisticsItems(List<DailyStatisticsItem> dailyStatisticsItems);
}
