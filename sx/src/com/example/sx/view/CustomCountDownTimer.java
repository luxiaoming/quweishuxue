package com.example.sx.view;

import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;

/**
 * 娴ｈ法鏁ndroid.os.CountDownTimer閻ㄥ嫭绨惍锟�
 * 1. 鐎电懓娲栫拫鍍秐Tick閸嬫矮绨＄紒鍡楃毈鐠嬪啯鏆ｉ敍灞藉嚒鐟欙絽鍠呴張锟介崥锟�1缁夋帊绗夋导姘拷鎺曨吀閺冭泛鍩�0閿涘矁顩︾粵澶婄窡2缁夋帗澧犻崶鐐剁殶onFinish
 * 2. 濞ｈ濮炴禍鍡曠娴滄稖鍤滅�规矮绠熼弬瑙勭《
 * Created by iWgang on 15/10/18.
 * https://github.com/iwgang/CountdownView
 */
public abstract class CustomCountDownTimer {
    private static final int MSG = 1;
    private final long mMillisInFuture;
    private final long mCountdownInterval;
    private long mStopTimeInFuture;
    private long mPauseTimeInFuture;
    private boolean isStop = false;
    private boolean isPause = false;

    /**
     * @param millisInFuture    閹锟芥帟顓搁弮鑸垫闂傦拷
     * @param countDownInterval 閸婃帟顓搁弮鍫曟？闂呮梹妞傞梻锟�
     */
    public CustomCountDownTimer(long millisInFuture, long countDownInterval) {
        // 鐟欙絽鍠呯粔鎺撴殶閺堝妞傛导姘瀵拷婵姘ㄩ崙蹇撳箵娴滐拷2缁夋帡妫舵０姗堢礄婵★拷10缁夋帗锟界粯鏆熼惃鍕剁礉閸掓艾绱戞慨瀣皑8999閿涘瞼鍔ч崥搴㈢梾閺堝绗夋导姘▔缁�锟�9缁夋帪绱濋惄瀛樺复閸掞拷8缁夋帪绱�
        if (countDownInterval > 1000) millisInFuture += 15;
        mMillisInFuture = millisInFuture;
        mCountdownInterval = countDownInterval;
    }

    private synchronized CustomCountDownTimer start(long millisInFuture) {
        isStop = false;
        if (millisInFuture <= 0) {
            onFinish();
            return this;
        }
        mStopTimeInFuture = SystemClock.elapsedRealtime() + millisInFuture;
        mHandler.sendMessage(mHandler.obtainMessage(MSG));
        return this;
    }

    /**
     * 瀵拷婵锟芥帟顓搁弮锟�
     */
    public synchronized final void start() {
        start(mMillisInFuture);
    }

    /**
     * 閸嬫粍顒涢崐鎺曨吀閺冿拷
     */
    public synchronized final void stop() {
        isStop = true;
        mHandler.removeMessages(MSG);
    }

    /**
     * 閺嗗倹妞傞崐鎺曨吀閺冿拷
     * 鐠嬪啰鏁@link #restart()}閺傝纭堕柌宥嗘煀瀵拷婵拷
     */
    public synchronized final void pause() {
        if (isStop) return ;

        isPause = true;
        mPauseTimeInFuture = mStopTimeInFuture - SystemClock.elapsedRealtime();
        mHandler.removeMessages(MSG);
    }

    /**
     * 闁插秵鏌婂锟芥慨锟�
     */
    public synchronized final void restart() {
        if (isStop || !isPause) return ;

        isPause = false;
        start(mPauseTimeInFuture);
    }

    /**
     * 閸婃帟顓搁弮鍫曟？闂呮柨娲栫拫锟�
     * @param millisUntilFinished 閸撯晙缍戝В顐ゎ潡閺侊拷
     */
    public abstract void onTick(long millisUntilFinished);

    /**
     * 閸婃帟顓搁弮鍓佺波閺夌喎娲栫拫锟�
     */
    public abstract void onFinish();


    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            synchronized (CustomCountDownTimer.this) {
                if (isStop || isPause) {
                    return;
                }

                final long millisLeft = mStopTimeInFuture - SystemClock.elapsedRealtime();
                if (millisLeft <= 0) {
                    onFinish();
                } else {
                    long lastTickStart = SystemClock.elapsedRealtime();
                    onTick(millisLeft);

                    // take into account user's onTick taking time to execute
                    long delay = lastTickStart + mCountdownInterval - SystemClock.elapsedRealtime();

                    // special case: user's onTick took more than interval to
                    // complete, skip to next interval
                    while (delay < 0) delay += mCountdownInterval;

                    sendMessageDelayed(obtainMessage(MSG), delay);
                }
            }
        }
    };
}
