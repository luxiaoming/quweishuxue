package com.example.sx;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.db.DBHelper;
import com.example.model.chengji;
import com.example.model.chengjiAdapter;
import com.example.sx.utils.Helper;

public class cjdActivity extends Activity implements OnClickListener {
	private app mApp;
	private Helper helper;
	private ListView cjdlist;// �ɼ����б�
	private chengjiAdapter adapter;
	private ImageView exit; // �˳�
	private ImageView img_sound;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cjd_main);// ���ز���

		cjdlist = (ListView) findViewById(R.id.list);
		// ��ȡ�ɼ��б�
		ArrayList<chengji> datas = new DBHelper(this).getAllDatas();
		// ������
		adapter = new chengjiAdapter(this, datas);
		// ����
		cjdlist.setAdapter(adapter);
		
		exit = findView(R.id.img_exit);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_sound);
		img_sound.setOnClickListener(this);
		
		// ��ȡappʵ��
		mApp = (app) getApplicationContext();
		// ��ȡ����İ����ӿ�
		helper = mApp.getHelper();
	}
	
	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
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
