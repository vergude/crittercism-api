package intexsoft.by.crittercismapi.ui.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ItemClick;
import org.androidannotations.annotations.ViewById;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.ui.adapters.NavigationDrawerAdapter;

@EActivity(R.layout.activity_statistics)
public class StatisticsActivity extends Activity {
    @ViewById(R.id.statistic_left_drawer)
    ListView leftDrawer;

    @AfterViews
    void initDrawer()
    {
        String[] names = getResources().getStringArray(R.array.drawer);
        NavigationDrawerAdapter adapter = new NavigationDrawerAdapter(this,names);
        leftDrawer.setAdapter(adapter);
    }

    @ItemClick(R.id.statistic_left_drawer)
    public void drawerItemClick(int position) {
        if(position==0)
        {
            MainActivity_.intent(this).start();
        }
    }
}
