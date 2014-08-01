package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.util.List;

/**
 * Created by vadim on 30.07.2014.
 */
public class AppInfoAdapter extends ArrayAdapter {

    private LayoutInflater mInflater;

    public AppInfoAdapter(Context context, int resource, List<DailyStatisticsItem> dailyStatisticsItems) {
        super(context, resource, dailyStatisticsItems);
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        DailyStatisticsItem dailyStatisticsItems = (DailyStatisticsItem) getItem(position);
        ViewHolder viewHolder;
        if (convertView == null)
        {
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.appinfo_item, parent, false);

            viewHolder.tvAppName=(TextView)convertView.findViewById(R.id.tvAppName);
            viewHolder.tvCrashesCount=(TextView)convertView.findViewById(R.id.tvCrashesCount);
            viewHolder.tvAppLoadsCount=(TextView)convertView.findViewById(R.id.tvAppLoadsCount);
			viewHolder.tvAppErrorPersent = (TextView)convertView.findViewById(R.id.tvAppErrorPersent);
            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.tvAppName.setText(dailyStatisticsItems.getApplication().getName());
        viewHolder.tvCrashesCount.setText(Integer.toString(dailyStatisticsItems.getCrashesCount()));
        viewHolder.tvAppLoadsCount.setText(Integer.toString(dailyStatisticsItems.getAppLoadsCount()));
		viewHolder.tvAppErrorPersent.setText(Integer.toString(dailyStatisticsItems.getErrorsPercent()) + "%");

        return convertView;
    }

    public static class ViewHolder
    {
		TextView tvAppName, tvCrashesCount, tvAppLoadsCount, tvAppErrorPersent;
    }
}
