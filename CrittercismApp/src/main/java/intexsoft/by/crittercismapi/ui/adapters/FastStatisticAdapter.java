package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;
import intexsoft.by.crittercismapi.data.db.FastStatisticItem;
import intexsoft.by.crittercismapi.data.db.TimeStatisticContainer;

import java.util.List;

/**
 * Created by Евгений on 28.10.2014.
 */
public class FastStatisticAdapter extends BaseAdapter
{
	private Context context;
	private List<TimeStatisticContainer> timeStatisticContainers;
	private LayoutInflater inflater;

	public FastStatisticAdapter(Context context, List<TimeStatisticContainer> statisticContainerList)
	{
		this.context = context;
		timeStatisticContainers = statisticContainerList;
		inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount()
	{
		return timeStatisticContainers.size();
	}

	@Override
	public Object getItem(int position)
	{
		return timeStatisticContainers.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;

		int result = 0;

		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = inflater.inflate(R.layout.fast_statistic_items, parent, false);
			viewHolder.textCategory = (TextView) convertView.findViewById(R.id.Category);
			viewHolder.statisticContainer = (LinearLayout) convertView.findViewById(R.id.fastStatisticIte);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textCategory.setText(timeStatisticContainers.get(position).getTimeType());
		viewHolder.statisticContainer.removeAllViews();

		for (FastStatisticItem fastStatisticItem : timeStatisticContainers.get(position).getFastStatisticItemList())
		{
			View view = inflater.inflate(R.layout.fast_statistic_detail, parent, false);
			((TextView) view.findViewById(R.id.appNameLeader)).setText(fastStatisticItem.getAppName());
			((TextView) view.findViewById(R.id.detailLeader)).setText(fastStatisticItem.getCountType()
					+ " : " + fastStatisticItem.getCountResult());
			result++;
			if((result) == timeStatisticContainers.get(position).getFastStatisticItemList().size())
			{
				view.findViewById(R.id.dividerLine).setVisibility(View.INVISIBLE);
			}
			viewHolder.statisticContainer.addView(view);
		}

		return convertView;
	}

	public static class ViewHolder
	{
		TextView textCategory;
		LinearLayout statisticContainer;
	}
}
