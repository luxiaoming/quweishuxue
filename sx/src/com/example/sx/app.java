package com.example.sx;

import android.app.Application;

import com.example.sx.utils.Helper;

public class app extends Application {
	private Helper sphelper;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		sphelper = new Helper(this);
	}

	public Helper getHelper() {
		return sphelper;
	}
}
