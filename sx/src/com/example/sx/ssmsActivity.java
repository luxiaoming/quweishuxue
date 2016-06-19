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
	private ImageView tab_1_img; // ѵ��ģʽ
	private ImageView exit; // �˳�
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private View time_setting_time; // �������ѡ��ʱ��Ĳ���
	private View time_setting_level; // �������ѡ���ѶȵĲ���
	private ImageView img_timing;

	private ImageView img_star1;
	private ImageView img_star2;
	private ImageView img_star3;// ��ʾ���ǵȼ�
	private int nandu;// �Ѷ�ֵ��Ĭ��0����
	private int shijian;// ��3 -5 - 7����

	private Button timemode_confirm;// ��������ģʽ

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
		// ��ȡappʵ��
		mApp = (app) getApplicationContext();
		// ��ȡ����İ����ӿ�
		helper = mApp.getHelper();

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		// ��ʼ��ͼ��
		boolean on = helper.getMusiconoff();
		if (on) {
			img_sound.setImageResource(R.drawable.sound_on);
		} else {
			img_sound.setImageResource(R.drawable.sound_off);
		}

	}

	// ��һ���߼������������ǲ�������ѡ������׳̶�
	private void showPopupWindow(View view, boolean istime) {

		// һ���Զ���Ĳ��֣���Ϊ��ʾ������
		GridView contentView = (GridView) LayoutInflater.from(this).inflate(
				R.layout.window, null);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				WindowManager.LayoutParams.MATCH_PARENT, util.dp2px(this, 80),
				true);

		if (istime) {
			final int[] intArray = { R.drawable.time_20, R.drawable.time_30,
					R.drawable.time_50, };
			String[] strArray = { "3����", "5����", "7����" };
			List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
			// ��������
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
					shijian = position;// �洢����λ�ã�0��3���� 1 ��5���� 2 ���߷���
					popupWindow.dismiss();
				}

			});
		} else {
			String[] strArray = { "����", "�м�", "�߼�" };
			List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
			// ��������
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
		// ���úò���֮����show
		popupWindow.showAsDropDown(view);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_exit_time:
			finish();// �˳�
			break;
		case R.id.img_sound_time:
			// �л�����
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
			game.putExtra("moshi", 1);// 0ѵ��ģʽ 1 ����ģʽ 2 ����ģʽ
			startActivity(game);
			break;
		}
	}
}
