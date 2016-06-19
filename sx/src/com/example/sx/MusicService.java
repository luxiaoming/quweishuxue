package com.example.sx;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class MusicService extends Service {
	// Ϊ��־�������ñ�ǩ
	private static String TAG = "MusicService";
	// �������ֲ���������
	private MediaPlayer mPlayer;

	// �÷��񲻴�����Ҫ������ʱ�����ã�����startService()����bindService()��������ʱ���ø÷���
	@Override
	public void onCreate() {
		// Toast.makeText(this, "MusicSevice onCreate()"
		// , Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onCreate()");

		mPlayer = MediaPlayer.create(getApplicationContext(), R.raw.back);
		// ���ÿ����ظ�����
		mPlayer.setLooping(true);

		super.onCreate();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		// Toast.makeText(this, "MusicSevice onStart()"
		// , Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onStart()");

		mPlayer.start();
		int Volume = ((app) getApplicationContext()).getHelper()
				.getMusicVolume();
		mPlayer.setVolume(Volume / 10.0f, Volume / 10.0f);
		super.onStart(intent, startId);
	}

	@Override
	public void onDestroy() {
		// Toast.makeText(this, "MusicSevice onDestroy()"
		// , Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onDestroy()");

		mPlayer.stop();

		super.onDestroy();
	}

	// ��������ͨ��bindService ����֪ͨ��Serviceʱ�÷���������
	@Override
	public IBinder onBind(Intent intent) {
		// Toast.makeText(this, "MusicSevice onBind()"
		// , Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onBind()");

		mPlayer.start();
		return null;
	}

	// ��������ͨ��unbindService����֪ͨ��Serviceʱ�÷���������
	@Override
	public boolean onUnbind(Intent intent) {
		// Toast.makeText(this, "MusicSevice onUnbind()"
		// , Toast.LENGTH_SHORT).show();
		Log.e(TAG, "MusicSerice onUnbind()");

		mPlayer.stop();

		return super.onUnbind(intent);
	}

}
