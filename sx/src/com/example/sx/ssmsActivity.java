package com.example.sx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;

import com.example.sx.adapter.GridViewItem;
import com.example.sx.adapter.MyAdapter;
import com.example.sx.utils.Helper;
import com.example.sx.utils.util;

public class ssmsActivity extends Activity implements OnClickListener {
	private ImageView tab_1_img; // 训练模式
	private ImageView exit; // 退出
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private View time_setting_time; // 点击出现选择时间的操作
	private View time_setting_level; // 点击出现选择难度的操作
	private ImageView img_timing;

	private ImageView img_star1;
	private ImageView img_star2;
	private ImageView img_star3;// 显示星星等级
	private int nandu;// 难度值，默认0，简单
	private int shijian;// 是3 -5 - 7分钟

	private Button timemode_confirm;// 启动速算模式

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.timemodesetting);
		exit = findView(R.id.img_exit_time);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_sound_time);
		img_sound.setOnClickListener(this);
		time_setting_time = findView(R.id.time_setting_time);
		time_setting_time.setOnClickListener(this);
		time_setting_level = findView(R.id.time_setting_level);
		time_setting_level.setOnClickListener(this);
		timemode_confirm = findView(R.id.timemode_confirm);
		timemode_confirm.setOnClickListener(this);
		img_timing = findView(R.id.img_timing);

		img_star1 = findView(R.id.img_time_star1);
		img_star2 = findView(R.id.img_time_star2);
		img_star3 = findView(R.id.img_time_star3);
		// 获取app实例
		mApp = (app) getApplicationContext();
		// 获取里面的帮助接口
		helper = mApp.getHelper();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// 初始化图标
		boolean on = helper.getMusiconoff();
		if (on) {
			img_sound.setImageResource(R.drawable.sound_on);
		} else {
			img_sound.setImageResource(R.drawable.sound_off);
		}

	}

	// 加一个逻辑，用来处理是操作还是选择的难易程度
	private void showPopupWindow(View view, boolean istime) {

		// 一个自定义的布局，作为显示的内容
		GridView contentView = (GridView) LayoutInflater.from(this).inflate(
				R.layout.window, null);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				WindowManager.LayoutParams.MATCH_PARENT, util.dp2px(this, 80),
				true);

		if (istime) {
			final int[] intArray = { R.drawable.time_20, R.drawable.time_30,
					R.drawable.time_50, };
			String[] strArray = { "3分钟", "5分钟", "7分钟" };
			List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
			// 测试数据
			for (int i = 0; i < 3; i++) {
				GridViewItem item = new GridViewItem(intArray[i], strArray[i]);
				HashMap<String, GridViewItem> tempHashMap = new HashMap<String, GridViewItem>();
				tempHashMap.put("item", item);
				hashMapList.add(tempHashMap);
			}
			MyAdapter myAdapter = new MyAdapter(this, hashMapList);
			contentView.setAdapter(myAdapter);
			contentView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					img_timing.setImageResource(intArray[position]);
					shijian = position;// 存储下来位置，0是3分钟 1 是5分钟 2 是七分钟
					popupWindow.dismiss();
				}

			});
		} else {
			String[] strArray = { "初级", "中级", "高级" };
			List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
			// 测试数据
			for (int i = 0; i < 3; i++) {
				GridViewItem item = new GridViewItem(R.drawable.star,
						strArray[i]);
				HashMap<String, GridViewItem> tempHashMap = new HashMap<String, GridViewItem>();
				tempHashMap.put("item", item);
				hashMapList.add(tempHashMap);
			}
			MyAdapter myAdapter = new MyAdapter(this, hashMapList);
			contentView.setAdapter(myAdapter);
			contentView.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					nandu = position;
					if (position == 0) {
						img_star1.setVisibility(View.VISIBLE);
						img_star2.setVisibility(View.GONE);
						img_star3.setVisibility(View.GONE);
					} else if (position == 1) {
						img_star1.setVisibility(View.VISIBLE);
						img_star2.setVisibility(View.VISIBLE);
						img_star3.setVisibility(View.GONE);
					} else {
						img_star1.setVisibility(View.VISIBLE);
						img_star2.setVisibility(View.VISIBLE);
						img_star3.setVisibility(View.VISIBLE);
					}
					popupWindow.dismiss();
				}

			});
		}

		popupWindow.setTouchable(true);

		popupWindow.setTouchInterceptor(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return false;
			}
		});
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bk_ground1));
		// 设置好参数之后再show
		popupWindow.showAsDropDown(view);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_exit_time:
			finish();// 退出
			break;
		case R.id.img_sound_time:
			// 切换设置
			boolean on = helper.getMusiconoff();
			if (on) {
				img_sound.setImageResource(R.drawable.sound_off);
			} else {
				img_sound.setImageResource(R.drawable.sound_on);
			}
			helper.setMusiconoff(!on);
			break;
		case R.id.time_setting_time:
			showPopupWindow(time_setting_time, true);
			break;
		case R.id.time_setting_level:
			showPopupWindow(time_setting_level, false);
			break;
		case R.id.timemode_confirm:
			Intent game = new Intent(this, gameActivity.class);
			game.putExtra("nandu", nandu);
			game.putExtra("shijian", shijian);
			game.putExtra("moshi", 1);// 0训练模式 1 速算模式 2 考试模式
			startActivity(game);
			break;
		}
	}
}
