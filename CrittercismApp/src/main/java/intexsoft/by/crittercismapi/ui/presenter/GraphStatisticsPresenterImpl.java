package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.EBean;

import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.ui.interactor.BuildGraphInteractor;
import intexsoft.by.crittercismapi.ui.view.GraphStatisticsView;

/**
 * Created by vadim on 25.10.2014.
 */

@EBean
public class GraphStatisticsPresenterImpl implements GraphStatisticsPresenter
{

    private GraphStatisticsView graphStatisticsView;

    private final EventObserver.Receiver dailyStatisticsReceiver = new EventObserver.Receiver()
    {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
            graphStatisticsView.dataLoaded();
        }
    };

    @Override
    public void init(GraphStatisticsView view)
    {
        this.graphStatisticsView = view;
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

    @Override
    public void buildConcreteGraph(Cursor cursor, String selectedColumnName, Context context, BuildGraphInteractor interactor)
    {
        graphStatisticsView.showGraph(interactor.buildGraph(cursor, selectedColumnName, context));
    }

}
