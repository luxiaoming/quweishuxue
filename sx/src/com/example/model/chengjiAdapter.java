package com.example.model;

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
public class chengjiAdapter extends BaseAdapter {
	private List<chengji> list;
	private chengji tempGridViewItem;
	private LayoutInflater layoutInflater;
	private Context context;

	public chengjiAdapter(Context context, List<chengji> list) {
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
			view = layoutInflater.inflate(R.layout.cjd_item, null);
			chengji item = list.get(position);// 拿到当前成绩
			TextView shijian = (TextView) view.findViewById(R.id.shijian);
			TextView zongshu = (TextView) view.findViewById(R.id.zongshu);
			TextView zhengqu = (TextView) view.findViewById(R.id.zhengqu);
			TextView cuowu = (TextView) view.findViewById(R.id.cuowu);
			shijian.setText(item.getTime());
			zongshu.setText(item.getZongshu());
			zhengqu.setText(item.getZhengque());
			cuowu.setText(item.getCuowu());

		}
		return view;
	}
}
