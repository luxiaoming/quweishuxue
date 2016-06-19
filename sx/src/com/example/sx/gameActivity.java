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
	private ImageView exit; // �˳�
	private app mApp;
	private Helper helper;
	private ImageView img_sound;
	private ImageView img_star1;
	private ImageView img_star2;
	private ImageView img_star3;// ��ʾ���ǵȼ�
	private int nandu;// �Ѷ�ֵ��Ĭ��0����
	private int caozuo;// �ǼӼ��˳�
	private int moshi;// �ĸ�ģʽ ��0 ѵ��ģʽ
	private Intent intent;
	private CountdownView mCountdownView;

	private TextView operation;

	private int id_ok;// ��ȷ�𰸵�R.idֵ
	private boolean isOK; // �Ƿ���ȷ��
	private boolean isOKGOing;// �Ƿ��Ѿ������
	private TextView firstQueNo;// ��һ��������
	private TextView secondQueNo;// �ڶ���������

	private <T extends View> T findView(int id) {
		return (T) this.findViewById(id);
	}

	private Button nextIcon;;// ��һ����Ŀ

	private int correct;// ��ȷ������
	private int incorrect;// ����ȷ������
	private TextView text_incorrect;

	// ���б�
	private List<TextView> ViewArray = new ArrayList<TextView>();
	private Button ans1;
	private Button ans2;
	private Button ans3;
	private Button ans4;
	private TextView text_correct;

	private TextView result;
	// ʱ���Ƿ���ʾ����
	private View time_base;

	private click my = new click();

	private ImageView img_game_help;
	private SoundPool soundPool;
	// ����һ�� HashMap ���ڴ����Ƶ���� ID

	HashMap<Integer, Integer> musicId = new HashMap<Integer, Integer>();

	private int shijian;

	private static final Random RANDOM = new Random();

	private int[] intArray = { R.drawable.u_add, R.drawable.u_sub,
			R.drawable.u_mul, R.drawable.u_div };
	int time;; // �洢��ʱ�䣬������ģʽ�Ϳ���ģʽ��ʾ���ʱ��Ҫ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.game);
		exit = findView(R.id.img_game_exit);
		exit.setOnClickListener(this);
		img_sound = findView(R.id.img_game_sound);
		img_sound.setOnClickListener(this);
		// ��ȡappʵ��
		mApp = (app) getApplicationContext();
		// ��ȡ����İ����ӿ�
		helper = mApp.getHelper();

		// ��һ��
		nextIcon = findView(R.id.nextIcon);
		nextIcon.setOnClickListener(this);

		operation = findView(R.id.operation);
		// ��ʼ��ͼ��
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

		// init �Ѷ�ͼ��
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
		// init ������
		firstQueNo = findView(R.id.firstQueNo);
		secondQueNo = findView(R.id.secondQueNo);
		result = findView(R.id.result);
		// ��������
		firstQueNo.setTypeface(EasyFonts.num(this));
		secondQueNo.setTypeface(EasyFonts.num(this));
		result.setTypeface(EasyFonts.num(this));
		// init time base
		time_base = findView(R.id.time_base);

		// init ���б�
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
		// init ��������
		caozuo = getIntent().getIntExtra("caozuo", 0);

		operation.setBackgroundResource(intArray[caozuo]);
		// init ģʽ
		moshi = getIntent().getIntExtra("moshi", 0);
		// init ����ʱ
		//
		// ��ȡ������ʱ����Ϣ
		shijian = getIntent().getIntExtra("shijian", 0);
		int[] time1 = { 3, 5, 7 };// ʱ��ֵ
		final int[] time2 = { 20, 50, 100 };// ʱ��ֵ ����ģʽ��ֵ��20���� 50���� 100����
		if (moshi == 1) {
			time = time1[shijian] * 60 * 1000;
			mCountdownView = (CountdownView) findViewById(R.id.remainTime);
			mCountdownView.start(time);
			mCountdownView
					.setOnCountdownEndListener(new OnCountdownEndListener() {

						@Override
						public void onEnd(CountdownView cv) {
							// TODO Auto-generated method stub
							// ����ʱ���˵Ĵ���
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
							// ����ʱ���˵Ĵ���
							showdialog2();
							// ����ʱ�䵽�ˣ���¼�¿��Խ��
							new DBHelper(gameActivity.this).insertContact(
									time2[shijian] + "����",
									(correct + incorrect) + "��", correct + "��",
									incorrect + "��");
						}
					});
		} else {
			time_base.setVisibility(View.INVISIBLE);
		}

		// init ����
		soundPool = new SoundPool(12, 0, 0);

		// ͨ�� load ��������ָ����Ƶ�����������ص���Ƶ ID ���� musicId ��

		musicId.put(1, soundPool.load(this, R.raw.yes, 1));

		musicId.put(2, soundPool.load(this, R.raw.wrong, 1));

		// init ����
		initshuti();
	}

	public class click implements View.OnClickListener {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

			if (v.getId() == id_ok) {
				// �������ȷ��,isOKGOing �������ù���
				if (!isOKGOing) {
					// ��dui�ˣ�����
					correct++;
					text_correct.setText(correct + "");
					isOKGOing = true;

				}
				// ������ȷ��
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
					// ����ˣ�����
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

	// �Ѷ�+ - ��ʱ���ֵ
	int[] addArray = { 10, 20, 30 };
	int[] mulArray = { 5, 10, 15 };

	public static void swap(List<Integer> list, int a, int b) {
		int c = list.get(a);
		list.set(a, list.get(b));
		list.set(b, c);

	}

	// �����������Ŀ�������������������ȷ���������ͬ�Ľ����ϡ�

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
			dialog_title.setText("ѵ��ģʽ");
			dialog_content.setText("����ѡ���Ѷȵȼ������������Ӧ��ѵ��.");
		} else if (moshi == 1) {
			dialog_title.setText("����ģʽ");
			dialog_content.setText("�ڹ涨ʱ��(3/5/7����)�ڽ��д��⣬���һ���һ�֣����һ��۳�һ��.");
		} else {
			dialog_title.setText("����ģʽ");
			dialog_content.setText("�ڹ涨ʱ��(20/50/100����)�ڽ��д��⣬���һ���һ�֣����һ��۳�һ��.");
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
				initshuti();// ���ݳ�ʼ��
				mCountdownView.start(time);// ��ʱ����ʼ��
				// ��ʼ�������ʣ����¿�ʼ
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
				// �˳��˽���
			}
		});

		dialog_title.setText("ʱ�䵽");
		dialog_content.setText("����ʱ��:" + time + "����\n" + "С������һ������"
				+ (correct + incorrect) + "����\n" + "��ȷ��" + correct + "�� "
				+ "����" + incorrect + "��");

	}

	private void initshuju() {
		// ��ʼ������
		id_ok = -1;// ��ȷ�𰸵�R.idֵ

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
		isOKGOing = false;// �Ƿ��Ѿ������
	}

	private void initshuti() {

		// ���������
		List<Integer> list = random_shuffle(4, 4);
		if (moshi != 0) {
			caozuo = RANDOM.nextInt(4);
			operation.setBackgroundResource(intArray[caozuo]);
		}
		switch (caozuo) {
		case 0:// +
		{ // �������ֵ������������
			int first = RANDOM.nextInt(addArray[nandu]);
			int second = RANDOM.nextInt(addArray[nandu]);
			int sum = first + second;
			// ��ʼ������ֵ����ʾ�ڽ�����
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// �������������
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(sum);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(sum * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// �����ʾ�����б�
			int index = 0;
			for (Integer i : s) {
				// �õ�����
				TextView item = ViewArray.get(list.get(index));

				if (i == sum) {
					id_ok = item.getId();// ��һ���洢����ȷ�𰸣����µ�ǰ������ȷ�𰸵�view
					// id�������ж���Ҫ���ֵ
				}
				// ���ô��б�����
				item.setText(i + "");
				index++;
			}
			//
		}
			break;
		case 1:// -
		{ // �������ֵ������������
			int first = RANDOM.nextInt(addArray[nandu]);
			int second = RANDOM.nextInt(addArray[nandu]);
			int sum = first + second;
			int sub = first;
			first = sum;
			// ��ʼ������ֵ����ʾ�ڽ�����
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// �������������
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(sub);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(sub * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// �����ʾ�����б�
			int index = 0;
			for (Integer i : s) {
				// �õ�����
				TextView item = ViewArray.get(list.get(index));

				if (i == sub) {
					id_ok = item.getId();
					// id�������ж���Ҫ���ֵ
				}
				// ���ô��б�����
				item.setText(i + "");
				index++;
			}
		}
			break;
		case 2:// *
		{ // �������ֵ������������
			int first = RANDOM.nextInt(mulArray[nandu]);
			int second = RANDOM.nextInt(mulArray[nandu]);
			int mul = first * second;
			// ��ʼ������ֵ����ʾ�ڽ�����
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// �������������
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(mul);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(mul * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// �����ʾ�����б�
			int index = 0;
			for (Integer i : s) {
				// �õ�����
				TextView item = ViewArray.get(list.get(index));

				if (i == mul) {
					id_ok = item.getId();
					// id�������ж���Ҫ���ֵ
				}
				// ���ô��б�����
				item.setText(i + "");
				index++;
			}
		}
			break;
		case 3:// /
		{ // �������ֵ������������
			int first = RANDOM.nextInt(mulArray[nandu]);
			int second = RANDOM.nextInt(mulArray[nandu]) + 1;
			int mul = first * second;
			int div = first;
			first = mul;
			// ��ʼ������ֵ����ʾ�ڽ�����
			firstQueNo.setText(first + "");
			secondQueNo.setText(second + "");
			// �������������
			HashSet<Integer> s = new HashSet<Integer>();
			s.add(div);
			while (s.size() < 4) {
				int i = RANDOM.nextInt(div * 2 + 4);
				if (!s.contains(i)) {
					s.add(i);
				}
			}
			// �����ʾ�����б�
			int index = 0;
			for (Integer i : s) {
				// �õ�����
				TextView item = ViewArray.get(list.get(index));

				if (i == div) {
					id_ok = item.getId();
					// id�������ж���Ҫ���ֵ
				}
				// ���ô��б�����
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
		if (helper.getMusiconoff()) {// ������ˣ��򲥷�
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
			finish();// �˳�
			break;
		case R.id.img_game_sound:
			// �л�����
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
