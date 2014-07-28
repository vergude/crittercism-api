package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.remote.response.AppData;

/**
 * Created by vadim on 28.07.2014.
 */

public class AppInfoAdapter extends ArrayAdapter<AppData>
{

    private ArrayList<AppData> mAppDataCurrentDate;
    private LayoutInflater mInflater;

    public AppInfoAdapter(Context context, int resource, ArrayList<AppData> appData)
    {
        super(context, resource, appData);
        this.mAppDataCurrentDate = appData;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        AppData appData = getItem(position);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.appinfo_item, parent, false);

            viewHolder.tvNameApp=(TextView)convertView.findViewById(R.id.tvNameApp);
            viewHolder.tvStartsDay=(TextView)convertView.findViewById(R.id.tvStartsDay);
            viewHolder.tvCrashes=(TextView)convertView.findViewById(R.id.tvCrashes);
            viewHolder.tvCrashesPercent=(TextView)convertView.findViewById(R.id.tvCrashesPercent);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvNameApp.setText(appData.getAppName());
        viewHolder.tvStartsDay.setText(Integer.toString(appData.getStartsPerDay()));
        viewHolder.tvCrashes.setText(Integer.toString(appData.getCrashes()));
        viewHolder.tvCrashesPercent.setText(Integer.toString(appData.getCrashPercent()));

        return convertView;
    }

    public static class ViewHolder
    {
        TextView tvNameApp,tvStartsDay,tvCrashes,tvCrashesPercent;
    }
}
