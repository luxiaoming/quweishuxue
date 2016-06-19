package com.example.sx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.db.DBHelper;
import com.example.sx.utils.Helper;
import com.example.sx.utils.easyfont.EasyFonts;
import com.example.sx.view.CountdownView;
import com.example.sx.view.CountdownView.OnCountdownEndListener;

public class gameActivity extends Activity implements OnClickListener {
	private ImageView exit; // 退出
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private ImageView img_star1;
	private ImageView img_star2;
	private ImageView img_star3;// 显示星星等级
	private int nandu;// 难度值，默认0，简单
	private int caozuo;// 是加减乘除
	private int moshi;// 哪个模式 ，0 训练模式
	private Intent intent;
	private CountdownView mCountdownView;

	private TextView operation;

	private int id_ok;// 正确答案的R.id值
	private boolean isOK; // 是否正确了
	private boolean isOKGOing;// 是否已经答过了
	private TextView firstQueNo;// 第一个操作数
	private TextView secondQueNo;// 第二个操作数

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	private Button nextIcon;;// 下一个题目

	private int correct;// 正确答题数
	private int incorrect;// 不正确答题数
	private TextView text_incorrect;

	// 答案列表
	private List<TextView> ViewArray = new ArrayList<TextView>();
	private Button ans1;
	private Button ans2;
	private Button ans3;
	private Button ans4;
	private TextView text_correct;

	private TextView result;
	// 时间是否显示操作
	private View time_base;

	private click my = new click();

	private ImageView img_game_help;
	private SoundPool soundPool;
	// 定义一个 HashMap 用于存放音频流的 ID

	HashMap<Integer, Integer> musicId = new HashMap<Integer, Integer>();

	private int shijian;

	private static final Random RANDOM = new Random();

	private int[] intArray = { R.drawable.u_add, R.drawable.u_sub,
			R.drawable.u_mul, R.drawable.u_div };
	int time;; // 存储下时间，在速算模式和考试模式显示结果时需要

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		exit = findView(R.id.img_game_exit);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_game_sound);
		img_sound.setOnClickListener(this);
		// 获取app实例
		mApp = (app) getApplicationContext();
		// 获取里面的帮助接口
		helper = mApp.getHelper();

		// 换一题
		nextIcon = findView(R.id.nextIcon);
		nextIcon.setOnClickListener(this);

		operation = findView(R.id.operation);
		// 初始化图标
		boolean on = helper.getMusiconoff();
		if (on) {
			img_sound.setImageResource(R.drawable.sound_on);
		} else {
			img_sound.setImageResource(R.drawable.sound_off);
		}
		img_game_help = findView(R.id.img_game_help);
		img_game_help.setOnClickListener(this);
		intent = new Intent(this, MusicService.class);

		text_incorrect = findView(R.id.text_incorrect);
		text_correct = findView(R.id.text_correct);

		// init 难度图标
		nandu = getIntent().getIntExtra("nandu", 0);
		img_star1 = findView(R.id.star1);
		img_star2 = findView(R.id.star2);
		img_star3 = findView(R.id.star3);

		if (nandu == 0) {
			img_star1.setVisibility(View.VISIBLE);
			img_star2.setVisibility(View.GONE);
			img_star3.setVisibility(View.GONE);
		} else if (nandu == 1) {
			img_star1.setVisibility(View.VISIBLE);
			img_star2.setVisibility(View.VISIBLE);
			img_star3.setVisibility(View.GONE);
		} else if (nandu == 2) {
			img_star1.setVisibility(View.VISIBLE);
			img_star2.setVisibility(View.VISIBLE);
			img_star3.setVisibility(View.VISIBLE);
		}
		// init 操作数
		firstQueNo = findView(R.id.firstQueNo);
		secondQueNo = findView(R.id.secondQueNo);
		result = findView(R.id.result);
		// 设置字体
		firstQueNo.setTypeface(EasyFonts.num(this));
		secondQueNo.setTypeface(EasyFonts.num(this));
		result.setTypeface(EasyFonts.num(this));
		// init time base
		time_base = findView(R.id.time_base);

		// init 答案列表
		ans1 = findView(R.id.ans1);
		ans2 = findView(R.id.ans2);
		ans3 = findView(R.id.ans3);
		ans4 = findView(R.id.ans4);
		ans1.setOnClickListener(my);
		ans2.setOnClickListener(my);
		ans3.setOnClickListener(my);
		ans4.setOnClickListener(my);

		ViewArray.add(ans1);
		ViewArray.add(ans2);
		ViewArray.add(ans3);
		ViewArray.add(ans4);
		// init 操作动作
		caozuo = getIntent().getIntExtra("caozuo", 0);

		operation.setBackgroundResource(intArray[caozuo]);
		// init 模式
		moshi = getIntent().getIntExtra("moshi", 0);
		// init 倒计时
		//
		// 获取到速算时间信息
		shijian = getIntent().getIntExtra("shijian", 0);
		int[] time1 = { 3, 5, 7 };// 时间值
		final int[] time2 = { 20, 50, 100 };// 时间值 考试模式的值，20分钟 50分钟 100分钟
		if (moshi == 1) {
			time = time1[shijian] * 60 * 1000;
			mCountdownView = (CountdownView) findViewById(R.id.remainTime);
			mCountdownView.start(time);
			mCountdownView
					.setOnCountdownEndListener(new OnCountdownEndListener() {

						@Override
						public void onEnd(CountdownView cv) {
							// TODO Auto-generated method stub
							// 倒计时到了的处理
							showdialog2();
						}
					});
		} else if (moshi == 2) {
			//
			time = time2[shijian] * 60 * 1000;
			mCountdownView = (CountdownView) findViewById(R.id.remainTime);
			mCountdownView.start(time);
			mCountdownView
					.setOnCountdownEndListener(new OnCountdownEndListener() {

						@Override
						public void onEnd(CountdownView cv) {
							// TODO Auto-generated method stub
							// 倒计时到了的处理
							showdialog2();
							// 考试时间到了，记录下考试结果
							new DBHelper(gameActivity.this).insertContact(
									time2[shijian] + "分钟",
									(correct + incorrect) + "个", correct + "个",
									incorrect + "个");
						}
					});
		} else {
			time_base.setVisibility(View.INVISIBLE);
		}

		// init 声音
		soundPool = new SoundPool(12, 0, 0);

		// 通过 load 方法加载指定音频流，并将返回的音频 ID 放入 musicId 中

		musicId.put(1, soundPool.load(this, R.raw.yes, 1));

		musicId.put(2, soundPool.load(this, R.raw.wrong, 1));

		// init 数题
		initshuti();
	}

	public class click implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == id_ok) {
				// 如果是正确答案,isOKGOing 代表设置过了
				if (!isOKGOing) {
					// 答dui了，计数
					correct++;
					text_correct.setText(correct + "");
					isOKGOing = true;

				}
				// 设置正确答案
				result.setText(((TextView) v).getText());
				result.setBackgroundResource(0);
				for (View view : ViewArray) {
					((TextView) view).setBackgroundResource(R.drawable.wrong);
					((TextView) view).setText("");
					((TextView) view).setClickable(false);
				}
				((Button) v).setBackgroundResource(R.drawable.correct);
				if (helper.getMusiconoff())
					soundPool.play(musicId.get(1), 1, 1, 0, 0, 1);
			} else {

				if (!isOKGOing) {
					// 答错了，计数
					incorrect++;
					text_incorrect.setText(incorrect + "");
					isOKGOing = true;

				}
				((TextView) v).setClickable(false);
				((TextView) v).setText("");
				((TextView) v).setBackgroundResource(R.drawable.wrong);
				if (helper.getMusiconoff())
					soundPool.play(musicId.get(2), 1, 1, 0, 0, 1);
			}
		}
	};

	// 难度+ - 的时候给值
	int[] addArray = { 10, 20, 30 };
	int[] mulArray = { 5, 10, 15 };

	public static void swap(List<Integer> list, int a, int b) {
		int c = list.get(a);
		list.set(a, list.get(b));
		list.set(b, c);

	}

	// 生成随机数，目的是随机出来，好让正确答案随机到不同的界面上。

	public static List<Integer> random_shuffle(int num, int thresold) {

		Random rd = new Random();
		List<Integer> result = new ArrayList<Integer>(thresold);
		for (int i = 0; i < thresold; i++)
			result.add(i);
		for (int i = thresold; i > 0; i--)
			swap(result, i - 1, rd.nextInt(thresold));
		return result.subList(0, num);
	}

	private void showdialog() {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.util_custom_dialog);
		TextView dialog_title = (TextView) window
				.findViewById(R.id.dialog_title);
		TextView dialog_content = (TextView) window
				.findViewById(R.id.dialog_content);
		Button button_ok = (Button) window.findViewById(R.id.button_ok);
		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
			}
		});
		if (moshi == 0) {
			dialog_title.setText("训练模式");
			dialog_content.setText("可以选择难度等级和运算进行相应的训练.");
		} else if (moshi == 1) {
			dialog_title.setText("速算模式");
			dialog_content.setText("在规定时间(3/5/7分钟)内进行答题，答对一题得一分，答错一题扣除一分.");
		} else {
			dialog_title.setText("考试模式");
			dialog_content.setText("在规定时间(20/50/100分钟)内进行答题，答对一题得一分，答错一题扣除一分.");
		}
	}

	private void showdialog2() {
		final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
		alertDialog.show();
		Window window = alertDialog.getWindow();
		window.setContentView(R.layout.util_custom_dialog2);
		TextView dialog_title = (TextView) window
				.findViewById(R.id.dialog_title);
		TextView dialog_content = (TextView) window
				.findViewById(R.id.dialog_content);
		Button button_ok = (Button) window.findViewById(R.id.button_ok);
		Button button_retry = (Button) window.findViewById(R.id.button_retry);

		button_retry.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				initshuti();// 数据初始化
				mCountdownView.start(time);// 定时器初始化
				// 初始化答题率，重新开始
				text_incorrect.setText("0");
				text_correct.setText("0");
			}
		});

		button_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog.dismiss();
				gameActivity.this.finish();
				// 退出此界面
			}
		});

		dialog_title.setText("时间到");
		dialog_content.setText("本次时间:" + time + "分钟\n" + "小朋友您一共答了"
				+ (correct + incorrect) + "道题\n" + "正确：" + correct + "道 "
				+ "错误：" + incorrect + "道");

	}

	private void initshuju() {
		// 初始化数据
		id_ok = -1;// 正确答案的R.id值

		if (!isOKGOing) {
			incorrect++;
			text_incorrect.setText(incorrect + "");
		}
		for (TextView view : ViewArray) {
			view.setBackgroundResource(R.drawable.answer_botton);
			view.setClickable(true);
		}

		result.setText("");
		result.setBackgroundResource(R.drawable.u_question_mark);
		isOKGOing = false;// 是否已经答过了
	}

	private void initshuti() {

		// 生成随机数
		List<Integer> list = random_shuffle(4, 4);
		if (moshi != 0) {
			caozuo = RANDOM.nextInt(4);
			operation.setBackgroundResource(intArray[caozuo]);
		}
		switch (caozuo) {
		case 0:// +
		{ // 算出两个值来，计算出结果
			int first = RANDOM.nextInt(addArray[nandu]);
			int second = RANDOM.nextInt(addArray[nandu]);
			int sum = first + second;
			// 初始化两个值，显示在界面上
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// 生成三个随机答案
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(sum);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(sum * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// 随机显示到答案列表
			int index = 0;
			for (Integer i : s) {
				// 得到界面
				TextView item = ViewArray.get(list.get(index));

				if (i == sum) {
					id_ok = item.getId();// 第一个存储着正确答案，存下当前持有正确答案的view
					// id，后续判断需要这个值
				}
				// 设置答案列表数据
				item.setText(i + "");
				index++;
			}
			//
		}
			break;
		case 1:// -
		{ // 算出两个值来，计算出结果
			int first = RANDOM.nextInt(addArray[nandu]);
			int second = RANDOM.nextInt(addArray[nandu]);
			int sum = first + second;
			int sub = first;
			first = sum;
			// 初始化两个值，显示在界面上
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// 生成三个随机答案
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(sub);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(sub * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// 随机显示到答案列表
			int index = 0;
			for (Integer i : s) {
				// 得到界面
				TextView item = ViewArray.get(list.get(index));

				if (i == sub) {
					id_ok = item.getId();
					// id，后续判断需要这个值
				}
				// 设置答案列表数据
				item.setText(i + "");
				index++;
			}
		}
			break;
		case 2:// *
		{ // 算出两个值来，计算出结果
			int first = RANDOM.nextInt(mulArray[nandu]);
			int second = RANDOM.nextInt(mulArray[nandu]);
			int mul = first * second;
			// 初始化两个值，显示在界面上
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// 生成三个随机答案
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(mul);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(mul * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// 随机显示到答案列表
			int index = 0;
			for (Integer i : s) {
				// 得到界面
				TextView item = ViewArray.get(list.get(index));

				if (i == mul) {
					id_ok = item.getId();
					// id，后续判断需要这个值
				}
				// 设置答案列表数据
				item.setText(i + "");
				index++;
			}
		}
			break;
		case 3:// /
		{ // 算出两个值来，计算出结果
			int first = RANDOM.nextInt(mulArray[nandu]);
			int second = RANDOM.nextInt(mulArray[nandu]) + 1;
			int mul = first * second;
			int div = first;
			first = mul;
			// 初始化两个值，显示在界面上
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// 生成三个随机答案
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(div);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(div * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// 随机显示到答案列表
			int index = 0;
			for (Integer i : s) {
				// 得到界面
				TextView item = ViewArray.get(list.get(index));

				if (i == div) {
					id_ok = item.getId();
					// id，后续判断需要这个值
				}
				// 设置答案列表数据
				item.setText(i + "");
				index++;
			}
		}
			break;
		default:
			break;
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (helper.getMusiconoff()) {// 如果开了，则播放
			startService(intent);
		}
		if (mCountdownView != null)
			mCountdownView.restart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		stopService(intent);
		if (mCountdownView != null)
			mCountdownView.pause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.nextIcon:
			initshuju();
			initshuti();
			break;
		case R.id.img_game_help:
			showdialog();
			break;
		case R.id.img_game_exit:
			finish();// 退出
			break;
		case R.id.img_game_sound:
			// 切换设置
			boolean on = helper.getMusiconoff();
			if (on) {
				img_sound.setImageResource(R.drawable.sound_off);
				stopService(intent);
			} else {
				img_sound.setImageResource(R.drawable.sound_on);
				startService(intent);
			}
			helper.setMusiconoff(!on);
			break;
		}
	}
}
