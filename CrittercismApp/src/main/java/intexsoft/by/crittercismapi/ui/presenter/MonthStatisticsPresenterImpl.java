package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.ui.view.MonthStatisticsView;

/**
 * Created by vadim on 07.10.2014.
 */
@EBean
public class MonthStatisticsPresenterImpl implements MonthStatisticPresenter {

    private MonthStatisticsView monthStatisticsView;

    private final EventObserver.Receiver dailyStatisticsReceiver = new EventObserver.Receiver() {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
            monthStatisticsView.dataLoaded();
        }
    };

    @Override
    public void init(MonthStatisticsView view)
    {
        this.monthStatisticsView = view;
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
