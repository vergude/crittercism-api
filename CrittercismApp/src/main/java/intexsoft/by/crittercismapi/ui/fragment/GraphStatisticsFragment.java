package intexsoft.by.crittercismapi.ui.fragment;

import android.app.Fragment;

import org.androidannotations.annotations.EFragment;

import intexsoft.by.crittercismapi.R;

@EFragment(R.layout.fragment_graph_statistics)
public class GraphStatisticsFragment extends Fragment
{
    public static final String TAG = MainFragment.class.getSimpleName();
    public static GraphStatisticsFragment build()
    {
        return GraphStatisticsFragment_.builder().build();
    }

}
