package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;

import org.androidannotations.annotations.EBean;

import intexsoft.by.crittercismapi.Constants;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.AppDetailsLoadedEvent;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.service.ErrorGraphService;
import intexsoft.by.crittercismapi.ui.view.AppDetailsErrorView;

/**
 * Created by Евгений on 04.08.2014.
 */
@EBean
public class AppDetailsErrorPresenterImpl implements AppDetailsErrorPresenter
{


	private AppDetailsErrorView appDetailsErrorView;

	private final EventObserver.Receiver appDetailsError = new EventObserver.Receiver()
	{
		@Override
		protected void onReceive(Context context, EventObserver.Event event)
		{
		}
	};


	@Override
	public void init(AppDetailsErrorView view)
	{
		this.appDetailsErrorView = view;
		ErrorGraphService.getAppErrorDetails(appDetailsErrorView.getAppId(), Constants.GRAPH_CRASHES);
	}

	@Override
	public void onStart()
	{
		EventObserver.register(getContext(), appDetailsError, AppDetailsLoadedEvent.class);
	}

	@Override
	public void onStop()
	{
		EventObserver.unregister(getContext(), appDetailsError);
	}

	private Context getContext()
	{
		return CrittercismApplication.getApplication().getApplicationContext();
	}
}
