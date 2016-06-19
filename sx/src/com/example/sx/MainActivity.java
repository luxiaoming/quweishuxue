package com.example.sx;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

import com.example.db.DBHelper;
import com.example.model.chengji;
import com.example.sx.utils.Helper;

public class MainActivity extends Activity implements OnClickListener {

	private ImageView exit; // �˳�
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private ImageView tab_2_img; // ����ģʽ
	private ImageView tab_3_img; // ����ģʽ
	private ImageView tab_1_img; // ѵ��ģʽ
	private ImageView tab_4_img;// �ɼ���

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);// ���ز���

		tab_1_img = findView(R.id.tab_1_img);
		tab_1_img.setOnClickListener(this);// ע�����¼�
		tab_2_img = findView(R.id.tab_2_img);
		tab_2_img.setOnClickListener(this);// ע�����¼�
		tab_3_img = findView(R.id.tab_3_img);
		tab_3_img.setOnClickListener(this);// ע�����¼�
		tab_4_img = findView(R.id.tab_4_img);
		tab_4_img.setOnClickListener(this);// ע�����¼�

		exit = findView(R.id.img_exit);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_sound);
		img_sound.setOnClickListener(this);

		// ��ȡappʵ��
		mApp = (app) getApplicationContext();
		// ��ȡ����İ����ӿ�
		helper = mApp.getHelper();

		//
		// new DBHelper(this).insertContact("5����", 10 + "", 5 + "",5+"");
		// ArrayList<chengji> datas = new DBHelper(this).getAllDatas();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// �������ѵ��ģʽ
		case R.id.tab_1_img:
			startActivity(new Intent(this, xlmsActivity.class));
			break;
		// �����������ģʽ
		case R.id.tab_2_img:
			startActivity(new Intent(this, ssmsActivity.class));
			break;
		// ������뿼��ģʽ
		case R.id.tab_3_img:
			startActivity(new Intent(this, ksmsActivity.class));
			break;
		// ������뿼��ģʽ
		case R.id.tab_4_img:
			startActivity(new Intent(this, cjdActivity.class));
			break;
		case R.id.img_exit:
			finish();// �˳�
			break;
		case R.id.img_sound:
			// �л�����
			boolean on = helper.getMusiconoff();
			if (on) {
				img_sound.setImageResource(R.drawable.sound_off);
			} else {
				img_sound.setImageResource(R.drawable.sound_on);
			}
			helper.setMusiconoff(!on);
			break;
		}
	}

}
