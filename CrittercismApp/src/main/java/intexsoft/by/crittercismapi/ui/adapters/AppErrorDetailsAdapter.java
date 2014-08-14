package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.bean.DailyStatisticsItem;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

/**
 * Created by Евгений on 11.08.2014.
 */
public class AppErrorDetailsAdapter extends ArrayAdapter
{
	private LayoutInflater mInflater;
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d, MMM (E)", Locale.ENGLISH);

	public AppErrorDetailsAdapter(Context context, int resource, List<DailyStatisticsItem> dailyStatisticsItems)
	{
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
			convertView = mInflater.inflate(R.layout.app_details_item, parent, false);

			viewHolder.tvAppDate = (TextView) convertView.findViewById(R.id.tvAppDate);
			viewHolder.tvCrashesCount = (TextView) convertView.findViewById(R.id.tvCrashes);
			viewHolder.tvLoadsCount = (TextView) convertView.findViewById(R.id.tvLoads);
			viewHolder.tvAppError = (TextView) convertView.findViewById(R.id.tvAppError);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.tvAppDate.setText(simpleDateFormat.format(dailyStatisticsItems.getDate()).toString());
		viewHolder.tvCrashesCount.setText(Integer.toString(dailyStatisticsItems.getCrashesCount()));
		viewHolder.tvLoadsCount.setText(Integer.toString(dailyStatisticsItems.getAppLoadsCount()));
		viewHolder.tvAppError.setText((String.format("%.3f", dailyStatisticsItems.getErrorsPercent())) + "%");

		return convertView;
	}

	public static class ViewHolder
	{
		TextView tvAppDate;
		TextView tvCrashesCount;
		TextView tvLoadsCount;
		TextView tvAppError;
	}
}
