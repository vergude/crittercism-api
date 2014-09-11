package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.data.facade.RemoteFacade;
import intexsoft.by.crittercismapi.event.DailyStatisticsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.manager.LoginManager;
import intexsoft.by.crittercismapi.service.ErrorGraphService;
import intexsoft.by.crittercismapi.ui.view.MainView;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
@EBean
public class MainPresenterImpl implements MainPresenter
{
	@Bean
	RemoteFacade remoteFacade;

    private MainView mainView;

    @Bean
    LoginManager loginManager;

    private final EventObserver.Receiver dailyStatisticsReceiver = new EventObserver.Receiver()
    {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {
			mainView.setDailyStatisticsItems(((DailyStatisticsLoadedEvent)event).getDailyStatisticsItems());
        }
    };

    @Override
    public void init(MainView view)
	{
        this.mainView = view;

		ErrorGraphService.getTodayStatistics();
		ErrorGraphService.saveDataForPeriodIfNeeded();
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
