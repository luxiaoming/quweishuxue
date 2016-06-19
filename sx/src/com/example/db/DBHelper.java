package com.example.db;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.model.chengji;

public class DBHelper extends SQLiteOpenHelper {

	public static final String DATABASE_NAME = "chengji.db";
	public static final String TABLE_NAME = "chengji";
	public static final String TIME = "time";
	public static final String TISHU = "zongshu"; // 总的答题数目
	public static final String OK = "zhengque"; // 正确数目
	public static final String ERROR = "cuowu"; // 错误数目

	public DBHelper(Context context) {
		super(context, DATABASE_NAME, null, 1);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL("create table chengji "
				+ "(time integer, zongshu text,zhengque text,cuowu text)");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS contacts");
		onCreate(db);
	}

	public boolean insertContact(String time, String zongshu, String zhengque,
			String cuowu) {
		SQLiteDatabase db = this.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put("time", time);
		contentValues.put("zongshu", zongshu);
		contentValues.put("zhengque", zhengque);
		contentValues.put("cuowu", cuowu);
		db.insert("chengji", null, contentValues);
		return true;
	}

	public ArrayList<chengji> getAllDatas() {
		ArrayList<chengji> array_list = new ArrayList<chengji>();
		SQLiteDatabase db = this.getReadableDatabase();
		Cursor res = db.rawQuery("select * from chengji", null);
		if (res == null)
			return null;
		res.moveToFirst();
		while (res.isAfterLast() == false) {
			chengji item = new chengji();
			item.setTime(res.getString(res.getColumnIndex(TIME)));
			item.setZongshu(res.getString(res.getColumnIndex(TISHU)));
			item.setZhengque(res.getString(res.getColumnIndex(OK)));
			item.setCuowu(res.getString(res.getColumnIndex(ERROR)));
			array_list.add(item);
			res.moveToNext();
		}
		return array_list;
	}
}