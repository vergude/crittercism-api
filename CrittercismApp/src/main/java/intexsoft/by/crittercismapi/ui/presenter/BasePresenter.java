package intexsoft.by.crittercismapi.ui.presenter;

import intexsoft.by.crittercismapi.ui.view.BaseView;

/**
 * Created by anastasya.konovalova on 22.06.2014.
 */
public interface BasePresenter<T extends BaseView>
{
    void init(T view);
    void onStart();
    void onStop();
}
