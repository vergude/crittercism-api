package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.ui.view.StatisticsView;
import org.androidannotations.annotations.EBean;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
@EBean
public class StatisticsPresenterImpl implements StatisticsPresenter
{

    private StatisticsView statisticsView;

    private final EventObserver.Receiver dailyStatisticsReceiver = new EventObserver.Receiver()
    {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
			statisticsView.dataLoaded();
        }
    };

    @Override
    public void init(StatisticsView statistics)
	{
        this.statisticsView = statistics;
    }


	@Override
    public void onStart()
    {
        EventObserver.register(getContext(), dailyStatisticsReceiver, DailyStatisticsLoadedEvent.class);
    }

    @Override
    public void onStop()
    {
        EventObserver.unregister(getContext(), dailyStatisticsReceiver);
    }

    private Context getContext()
    {
        return CrittercismApplication.getApplication().getApplicationContext();
    }

}
