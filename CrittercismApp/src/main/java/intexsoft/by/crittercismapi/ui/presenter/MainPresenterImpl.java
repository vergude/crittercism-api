package intexsoft.by.crittercismapi.ui.presenter;

import android.content.Context;
import intexsoft.by.crittercismapi.CrittercismApplication;
import intexsoft.by.crittercismapi.event.EventObserver;
import intexsoft.by.crittercismapi.ui.view.BaseView;
import org.androidannotations.annotations.EBean;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
@EBean
public class MainPresenterImpl implements MainPresenter
{

    private final EventObserver.Receiver geoPointsReceiver = new EventObserver.Receiver()
    {
        @Override
        protected void onReceive(Context context, EventObserver.Event event)
        {

        }
    };



    @Override
    public void init(BaseView view)
	{

    }

    @Override
    public void onStart()
    {
        //EventObserver.register(getContext(), geoPointsReceiver, LoginPerformedEvent.class);
    }

    @Override
    public void onStop()
    {
        //EventObserver.unregister(getContext(), geoPointsReceiver);
    }

    private Context getContext()
    {
        return CrittercismApplication.getApplication().getApplicationContext();
    }

}
