package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import android.database.Cursor;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.ui.interactor.BuildGraphInteractor;
import intexsoft.by.crittercismapi.ui.interactor.BuildGraphInteractorImpl;
import intexsoft.by.crittercismapi.ui.view.GraphActivityView;

/**
 * Created by vadim on 27.10.2014.
 */

@EBean
public class GraphActivityPresenterImpl implements GraphActivityPresenter
{

    private GraphActivityView graphActivityView;
    private BuildGraphInteractor graphInteractor;

    @Bean(BuildGraphInteractorImpl.class)
    BuildGraphInteractor interactor;

    private final EventObserver.Receiver dailyStatisticsReceiver = new EventObserver.Receiver() {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
            graphActivityView.dataLoaded();
        }
    };

    @Override
    public void init(GraphActivityView view)
    {
        this.graphActivityView = view;
    }

    @Override
    public void onStart()
    {
        EventObserver.unregister(getContext(), dailyStatisticsReceiver);
    }

    @Override
    public void onStop()
    {

    }

    private Context getContext()
    {
        return CrittercismApplication.getApplication().getApplicationContext();
    }

    @Override
    public void buildGraph(Cursor cursor)
    {
        graphInteractor.buildGraph(cursor);
    }
}
