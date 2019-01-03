package com.clearliang.frameworkdemo.utils;

import android.content.Context;
import android.view.MotionEvent;
import android.view.View;

import com.clearliang.frameworkdemo.R;

/**
 * Created by ClearLiang on 2018/12/29
 * <p>
 * Function :移动View的工具类
 */
public class MoveViewUtil {
    private static MoveViewUtil moveViewUtil;

    private MoveViewUtil() {
    }

    public static MoveViewUtil getInstance() {
        if (moveViewUtil == null) {
            moveViewUtil = new MoveViewUtil();
        }
        return moveViewUtil;
    }

    private OnTouchListener onTouchListener;

    public interface OnTouchListener {
        void onTouchDown(MotionEvent motionEvent, int startX, int startY);

        void onTouchMove(MotionEvent motionEvent);

        void onTouchUp(MotionEvent motionEvent);
    }

    public void setOnTouchListener(OnTouchListener onTouchListener) {
        this.onTouchListener = onTouchListener;
    }

    public MoveViewUtil initMove(final Context context, final View btn) {

        btn.setOnTouchListener(new View.OnTouchListener() {
            private int startY;
            private int startX;

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //LogUtils.e("打印操作：", "按下了");
                        btn.setBackgroundColor(context.getResources().getColor(R.color.app_color_description));
                        //获取当前按下的坐标
                        startX = (int) motionEvent.getRawX();
                        startY = (int) motionEvent.getRawY();
                        onTouchListener.onTouchDown(motionEvent, startX, startY);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        //获取移动后的坐标
                        int moveX = (int) motionEvent.getRawX();
                        int moveY = (int) motionEvent.getRawY();
                        //拿到手指移动距离的大小
                        int move_bigX = moveX - startX;
                        int move_bigY = moveY - startY;
                        //LogUtils.e("打印操作：", "\nX移动了" + move_bigX + "\nY移动了" + move_bigY);
                        //拿到当前控件未移动的坐标
                        int left = btn.getLeft();
                        int top = btn.getTop();
                        left += move_bigX;
                        top += move_bigY;
                        int right = left + btn.getWidth();
                        int bottom = top + btn.getHeight();
                        btn.layout(left, top, right, bottom);
                        startX = moveX;
                        startY = moveY;
                        onTouchListener.onTouchMove(motionEvent);
                        break;

                    case MotionEvent.ACTION_UP:
                        //LogUtils.e("打印操作：", "抬起了");
                        onTouchListener.onTouchUp(motionEvent);
                        break;
                }
                return true;
            }
        });

        return moveViewUtil;
    }
}
