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

	private ImageView exit; // 退出
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private ImageView tab_2_img; // 速算模式
	private ImageView tab_3_img; // 考试模式
	private ImageView tab_1_img; // 训练模式
	private ImageView tab_4_img;// 成绩单

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);// 加载布局

		tab_1_img = findView(R.id.tab_1_img);
		tab_1_img.setOnClickListener(this);// 注册点击事件
		tab_2_img = findView(R.id.tab_2_img);
		tab_2_img.setOnClickListener(this);// 注册点击事件
		tab_3_img = findView(R.id.tab_3_img);
		tab_3_img.setOnClickListener(this);// 注册点击事件
		tab_4_img = findView(R.id.tab_4_img);
		tab_4_img.setOnClickListener(this);// 注册点击事件

		exit = findView(R.id.img_exit);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_sound);
		img_sound.setOnClickListener(this);

		// 获取app实例
		mApp = (app) getApplicationContext();
		// 获取里面的帮助接口
		helper = mApp.getHelper();

		//
		// new DBHelper(this).insertContact("5分钟", 10 + "", 5 + "",5+"");
		// ArrayList<chengji> datas = new DBHelper(this).getAllDatas();
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		// 点击进入训练模式
		case R.id.tab_1_img:
			startActivity(new Intent(this, xlmsActivity.class));
			break;
		// 点击进入速算模式
		case R.id.tab_2_img:
			startActivity(new Intent(this, ssmsActivity.class));
			break;
		// 点击进入考试模式
		case R.id.tab_3_img:
			startActivity(new Intent(this, ksmsActivity.class));
			break;
		// 点击进入考试模式
		case R.id.tab_4_img:
			startActivity(new Intent(this, cjdActivity.class));
			break;
		case R.id.img_exit:
			finish();// 退出
			break;
		case R.id.img_sound:
			// 切换设置
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
