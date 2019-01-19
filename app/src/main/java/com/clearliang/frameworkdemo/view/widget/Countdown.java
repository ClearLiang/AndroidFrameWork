package com.clearliang.frameworkdemo.view.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.clearliang.frameworkdemo.R;
import com.clearliang.frameworkdemo.utils.TranUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ClearLiang on 2019/1/18
 * <p>
 * Function :倒计时
 */
public class Countdown extends LinearLayout {
    // 倒计时的根布局
    private RelativeLayout rl1,rl2;
    // 左边的图片
    private ImageView leftImage;
    // 中间的文字
    private TextView textView;

    private int countTime = 0;
    private MyRunnable myRunnable;
    private OnStateListener onStateListener;

    public Countdown(Context context, AttributeSet attrs) {
        super(context, attrs);

        /**加载布局文件*/
        LayoutInflater.from(context).inflate(R.layout.widget_countdown, this, true);
        rl1 = findViewById(R.id.rl_1);
        rl2 = findViewById(R.id.rl_2);
        leftImage = findViewById(R.id.iv_icon);
        textView = findViewById(R.id.tv_text);

        /**获取属性值*/
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCountdown);
        int borderWidth = (int) typedArray.getDimension(R.styleable.MyCountdown_border_width, 2);
        int borderColor = typedArray.getColor(R.styleable.MyCountdown_border_color, Color.BLACK);
        int backgroundColor = typedArray.getColor(R.styleable.MyCountdown_background_color, Color.WHITE);
        int textSize = (int) typedArray.getDimension(R.styleable.MyCountdown_text_size, 18);
        int textColor = typedArray.getColor(R.styleable.MyCountdown_text_color, Color.GRAY);
        boolean isShowText = typedArray.getBoolean(R.styleable.MyCountdown_show_right_text, true);
        int leftImageId = typedArray.getResourceId(R.styleable.MyCountdown_left_image, R.mipmap.icon_tabbar_lab);
        boolean isShowImage = typedArray.getBoolean(R.styleable.MyCountdown_show_left_image, true);

        /**设置值*/
        setBorderWidth(borderWidth);
        setBorderColor(borderColor);
        setBgColor(backgroundColor);
        setTextSize(textSize);
        setTextColor(textColor);
        setIsShowText(isShowText);
        setLeftImageId(leftImageId);
        setIsShowImage(isShowImage);

    }

    // 设置边框宽度
    public void setBorderWidth(int borderWidth) {
        setMargins(rl2,borderWidth,borderWidth,borderWidth,borderWidth);
    }

    // 设置边框颜色
    public void setBorderColor(int borderColor) {
        rl1.setBackgroundColor(borderColor);
    }

    // 设置控件背景颜色
    public void setBgColor(int backgroundColor) {
        rl2.setBackgroundColor(backgroundColor);
    }

    // 设置时间字体大小
    public void setTextSize(int textSize) {
        textView.setTextSize(textSize);
    }

    // 设置时间字体颜色
    public void setTextColor(int textColor) {
        textView.setTextColor(textColor);
    }

    // 设置是否显示时间
    public void setIsShowText(boolean isShowText) {
        textView.setVisibility(isShowText ? VISIBLE : GONE);
    }

    // 设置图片资源id
    public void setLeftImageId(int leftImageId) {
        leftImage.setBackgroundResource(leftImageId);
    }

    // 设置是否显示图片
    public void setIsShowImage(boolean isShowImage) {
        leftImage.setVisibility(isShowImage ? VISIBLE : GONE);
    }

    private void setMargins (View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public void setDelayTime (int delayTime) {
        countTime = delayTime;
        textView.setText(TranUtil.seconds2Data(countTime));
    }

    public void startTimer (OnStateListener onStateListener) {
        if(countTime != 0){
            myRunnable = new MyRunnable();
            new Thread(myRunnable).start();
            ToastUtils.showShort("开始倒计时");
            this.onStateListener = onStateListener;
        }else {
            ToastUtils.showShort("请设置时长");
        }

    }

    public void endTimer () {
        handler.removeCallbacks(myRunnable);
        LogUtils.e("倒计时结束");
    }

    // 获取时间控件
    public TextView getTimer() {
        return textView;
    }

    // 获取图片控件
    public ImageView getImage() {
        return leftImage;
    }

    // 获取跟布局控件
    public RelativeLayout getRelativeLayout(){
        return rl2;
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what) {
                case 1:
                    countTime--;
                    onStateListener.onTimeListener(countTime);
                    textView.setText(TranUtil.seconds2Data(countTime));
            }
            super.handleMessage(msg);
        }
    };

    public class MyRunnable implements Runnable{
        public void run() {
            while (countTime>1) {
                try {
                    Thread.sleep(1000);
                    Message message = new Message();
                    message.what = 1;
                    handler.sendMessage(message);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            endTimer();
        }
    }

    //监听接口
    public interface OnStateListener {
        /**
         * 时间监听
         */
        void onTimeListener(int remaining);

    }

}
