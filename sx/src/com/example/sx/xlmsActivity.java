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

public class xlmsActivity extends Activity implements OnClickListener {
	private ImageView exit; // �˳�
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private View lin_setting_oprate; // �������ѡ������Ĳ���
	private View lin_setting_level; // �������ѡ���ѶȵĲ���
	private ImageView img_oprate;

	private ImageView img_star1;
	private ImageView img_star2;
	private ImageView img_star3;// ��ʾ���ǵȼ�
	private int nandu;// �Ѷ�ֵ��Ĭ��0����
	private int caozuo;// �ǼӼ��˳�

	private Button taining_confirm;// ������ѵ

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tainingsetting);
		exit = findView(R.id.img_exit_training);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_sound_trainig);
		img_sound.setOnClickListener(this);
		lin_setting_oprate = findView(R.id.lin_setting_oprate);
		lin_setting_oprate.setOnClickListener(this);
		lin_setting_level = findView(R.id.lin_setting_level);
		lin_setting_level.setOnClickListener(this);
		taining_confirm = findView(R.id.taining_confirm);
		taining_confirm.setOnClickListener(this);
		img_oprate = findView(R.id.img_oprate);
		img_star1 = findView(R.id.img_star1);
		img_star2 = findView(R.id.img_star2);
		img_star3 = findView(R.id.img_star3);
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
	private void showPopupWindow(View view, boolean oprate) {

		// һ���Զ���Ĳ��֣���Ϊ��ʾ������
		GridView contentView = (GridView) LayoutInflater.from(this).inflate(
				R.layout.window, null);
		final PopupWindow popupWindow = new PopupWindow(contentView,
				WindowManager.LayoutParams.MATCH_PARENT, util.dp2px(this, 80),
				true);

		if (oprate) {
			final int[] intArray = { R.drawable.plus_setting,
					R.drawable.minus_setting, R.drawable.multi_setting,
					R.drawable.division_setting };
			String[] strArray = { "�ӷ�", "����", "�˷�", "����" };
			List<HashMap<String, GridViewItem>> hashMapList = new ArrayList<HashMap<String, GridViewItem>>();
			// ��������
			for (int i = 0; i < 4; i++) {
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
					img_oprate.setImageResource(intArray[position]);
					caozuo = position;// �洢����λ�ã�0��+ �Դ�����
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

				Log.i("mengdd", "onTouch : ");

				return false;
				// �����������true�Ļ���touch�¼���������
				// ���غ� PopupWindow��onTouchEvent�������ã���������ⲿ�����޷�dismiss
			}
		});

		// ���������PopupWindow�ı����������ǵ���ⲿ������Back�����޷�dismiss����
		// �Ҿ���������API��һ��bug
		popupWindow.setBackgroundDrawable(getResources().getDrawable(
				R.drawable.bk_ground1));
		// ���úò���֮����show
		popupWindow.showAsDropDown(view);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.img_exit_training:
			finish();// �˳�
			break;
		case R.id.img_sound_trainig:
			// �л�����
			boolean on = helper.getMusiconoff();
			if (on) {
				img_sound.setImageResource(R.drawable.sound_off);
			} else {
				img_sound.setImageResource(R.drawable.sound_on);
			}
			helper.setMusiconoff(!on);
			break;
		case R.id.lin_setting_oprate:
			showPopupWindow(lin_setting_oprate, true);
			break;
		case R.id.lin_setting_level:
			showPopupWindow(lin_setting_level, false);
			break;
		case R.id.taining_confirm:
			Intent game = new Intent(this, gameActivity.class);
			game.putExtra("nandu", nandu);
			game.putExtra("caozuo", caozuo);
			game.putExtra("moshi", 0);// 0ѵ��ģʽ
			startActivity(game);
			break;
		}
	}
}
