package intexsoft.by.crittercismapi.ui.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import intexsoft.by.crittercismapi.R;

/**
 * Created by vadim on 24.07.2014.
 */

public class NavigationDrawerAdapter extends ArrayAdapter<String>
{
	private final String[] mItems;
	private LayoutInflater mInflater;

	public NavigationDrawerAdapter(Context context, String[] items)
	{
		super(context, R.layout.drawer_item, items);
		this.mItems = items;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{
		ViewHolder viewHolder;
		if (convertView == null)
		{
			viewHolder = new ViewHolder();
			convertView = mInflater.inflate(R.layout.drawer_item, parent, false);
			viewHolder.textView = (TextView) convertView.findViewById(R.id.tvDrawer);
			convertView.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.textView.setText(mItems[position]);

		return convertView;
	}

	private static class ViewHolder
	{
		TextView textView;
	}
}
