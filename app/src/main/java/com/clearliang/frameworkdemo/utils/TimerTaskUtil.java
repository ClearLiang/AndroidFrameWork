package com.clearliang.frameworkdemo.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;

/**
 * @作者 ClearLiang
 * @日期 2018/5/18
 * @描述 定时器
 **/

public class TimerTaskUtil {
    private TimerCallBack timerCallBack = null;
    private CountDownCallBack countDownCallBack = null;
    private CountDownSecondCallBack countDownSecondCallBack = null;
    private boolean timerRun = false;
    private boolean bInterval = false;      //间隔
    private long curMsecond = 0;
    private String hour = "", minute = "", second = "";
    private CountDownTimer countDownTimer = null;

    static volatile TimerTaskUtil defaultInstance;


    public static TimerTaskUtil getInstence() {
        if (defaultInstance == null) {
            synchronized (Object.class) {
                if (defaultInstance == null) {
                    defaultInstance = new TimerTaskUtil();
                    return defaultInstance;
                }
            }
        }
        return defaultInstance;
    }


    /**
     * 回调接口定义
     */
    public interface TimerCallBack {
        void callback();
    }

    public interface CountDownCallBack {
        void onTick(String hour, String minute, String second);

        void onFinish();

    }


    public interface CountDownSecondCallBack {
        void onRemindTime(long second);

        void onFinish();
    }


    public void setTimeClickListener(CountDownCallBack countDownCallBack) {
        this.countDownCallBack = countDownCallBack;
    }

    public void setCountDownSecondCallBack(CountDownSecondCallBack countDownSecondCallBack) {
        this.countDownSecondCallBack = countDownSecondCallBack;
    }

    @SuppressLint("HandlerLeak")
    private Handler timerHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (timerCallBack != null) {
                timerCallBack.callback();
            }
            timerRun = false;
            if (bInterval) {
                startTimer(curMsecond, timerCallBack);
                bInterval = true;
            }
            super.handleMessage(msg);
        }

    };

    private Runnable keyRunnable = new Runnable() {
        @Override
        public void run() {
            timerHandler.sendEmptyMessage(0);
        }
    };

    /**
     * 关闭定时器
     */
    public void killTimer() {
        bInterval = false;
        timerRun = false;
        try {
            timerHandler.removeCallbacks(keyRunnable);
        } catch (Exception e) {

        }

    }

    /**
     * 启动延时器
     *
     * @param msecond 毫秒
     * @param cb      回调函数
     */
    public void startTimer(int msecond, TimerCallBack cb) {
        killTimer();
        curMsecond = msecond;
        timerRun = true;
        timerCallBack = cb;
        timerHandler.postDelayed(keyRunnable, curMsecond);
    }

    /**
     * 启动延时器
     *
     * @param msecond 毫秒
     * @param cb      回调函数
     */
    public void startTimer(long msecond, TimerCallBack cb) {
        killTimer();
        timerRun = true;
        timerCallBack = cb;
        timerHandler.postDelayed(keyRunnable, msecond);
    }

    /**
     * 启动定时器
     *
     * @param msecond 毫秒
     * @param cb      回调函数
     */
    public void startInterval(int msecond, TimerCallBack cb) {
        startTimer(msecond, cb);
        bInterval = true;
    }

    /**
     * 获取定时器是否运行
     */
    public boolean isRunning() {
        return timerRun;
    }

    /**
     * 启动倒计时
     */

    public void startCountDown(CountDownTimer countDownTimer) {
        countDownTimer.start();
    }

    /**
     * 启动倒计时
     */

    public CountDownTimer startCountDown(long seconds) {
        closeCountDown();
        timerRun = true;
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long currentTime = millisUntilFinished / 1000L;
                //时
                if (currentTime / 3600 < 10) {
                    hour = "0" + currentTime / 3600;
                } else {
                    hour = String.valueOf(currentTime / 3600);
                }
                //分
                if (((currentTime % 3600) / 60) < 10) {
                    minute = "0" + (currentTime % 3600) / 60;
                } else {
                    minute = String.valueOf((currentTime % 3600) / 60);
                }
                //秒
                if (((currentTime % 3600) % 60) < 10) {
                    second = "0" + (currentTime % 3600) % 60;
                } else {
                    second = String.valueOf((currentTime % 3600) % 60);
                }

                if (countDownCallBack != null) {
                    countDownCallBack.onTick(hour, minute, second);
                }
            }

            @Override
            public void onFinish() {
                countDownCallBack.onFinish();
                closeCountDown();
            }
        };
        return countDownTimer;
    }

    /**
     * 启动倒计时 返回秒数
     */

    public CountDownTimer startSecondCountDown(long seconds) {
        closeCountDown();
        timerRun = true;
        countDownTimer = new CountDownTimer(seconds * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                countDownSecondCallBack.onRemindTime(millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                closeCountDown();
            }
        };
        return countDownTimer;
    }

    /**
     * 关闭倒计时
     */
    public void closeCountDown() {
        timerRun = false;
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
        if (countDownCallBack != null) {
            countDownCallBack.onFinish();
        }
        if (countDownSecondCallBack != null) {
            countDownSecondCallBack.onFinish();
        }
    }
}
