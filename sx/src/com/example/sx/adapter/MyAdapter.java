package com.example.sx.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sx.R;

/**
 * 
 * @author Administrator
 * 
 */
public class MyAdapter extends BaseAdapter {
	private List<HashMap<String, GridViewItem>> list;
	private GridViewItem tempGridViewItem;
	private LayoutInflater layoutInflater;
	private Context context;

	public MyAdapter(Context context, List<HashMap<String, GridViewItem>> list) {
		this.context = context;
		this.list = list;
		layoutInflater = LayoutInflater.from(context);
	}

	/**
	 * 数据总数
	 */
	@Override
	public int getCount() {

		return list.size();
	}

	/**
	 * 获取当前数据
	 */
	@Override
	public Object getItem(int position) {

		return list.get(position);
	}

	@Override
	public long getItemId(int position) {

		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View view = null;
		if (layoutInflater != null) {
			view = layoutInflater
					.inflate(R.layout.activity_gridview_item, null);
			ImageView imageView = (ImageView) view
					.findViewById(R.id.imageView1);
			TextView title = (TextView) view.findViewById(R.id.title);

			tempGridViewItem = list.get(position).get("item");
			imageView.setImageResource(tempGridViewItem.getBitmap());
			title.setText(tempGridViewItem.getTitle());
		}
		return view;
	}
}
