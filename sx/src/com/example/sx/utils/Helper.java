package com.example.sx.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class Helper {
	Context context;
	SharedPreferences.Editor editor;
	SharedPreferences sp;

	public Helper(Context paramContext) {
		this.context = paramContext;
		this.sp = this.context.getSharedPreferences("def", 0);
		this.editor = this.sp.edit();
	}

	public SharedPreferences getSharedPreferences() {
		return this.sp;
	}

	public void putValue(String paramString1, String paramString2) {
		this.editor = this.sp.edit();
		this.editor.putString(paramString1, paramString2);
		this.editor.commit();
	}

	public void putValue(String paramString1, boolean paramString2) {
		this.editor = this.sp.edit();
		this.editor.putBoolean(paramString1, paramString2);
		this.editor.commit();
	}

	public void putValue(String paramString1, int paramString2) {
		this.editor = this.sp.edit();
		this.editor.putInt(paramString1, paramString2);
		this.editor.commit();
	}

	public String getStrValue(String paramString1) {
		return sp.getString(paramString1, "");
	}

	public boolean getMusiconoff() {
		return getbooleanValue("music",true);
	}

	public void setMusiconoff(boolean is) {
		putValue("music", is);
	}

	public int getMusicVolume() {
		return getIntValue("Volume",5);
	}

	public void setMusicVolume(int Volume) {
		putValue("Volume", Volume);
	}

	public int getScore() {
		return getIntValue("Score");
	}

	public void setScore(int Score) {
		putValue("Score", Score);
	}

	public boolean getbooleanValue(String paramString1) {
		return sp.getBoolean(paramString1, false);
	}

	public boolean getbooleanValue(String paramString1, boolean value) {
		return sp.getBoolean(paramString1, value);
	}

	public int getIntValue(String paramString1, int value) {
		return sp.getInt(paramString1, value);
	}

	public int getIntValue(String paramString1) {
		return sp.getInt(paramString1, 0);
	}
}

/*
 * Location: C:\Users\Administrator\Desktop\classes_dex2jar.jar Qualified Name:
 * com.syc.sycmoblieframe.util.SharedPreferencesHelper JD-Core Version: 0.6.2
 */