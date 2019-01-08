package com.clearliang.frameworkdemo.testfile;

import android.accessibilityservice.AccessibilityService;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import com.blankj.utilcode.util.LogUtils;

import java.util.List;

/**
 * 控制无障碍服务
 * Created by mazaiting on 2017/8/18.
 */
public class AccessibilityOperator {
    private static final String TAG = "AccessibilityOperator";
    private static AccessibilityOperator mInstance;

    private AccessibilityOperator() {
    }

    public static AccessibilityOperator getInstance() {
        if (mInstance == null) {
            synchronized (AccessibilityOperator.class) {
                if (mInstance == null) {
                    mInstance = new AccessibilityOperator();
                }
            }
        }
        return mInstance;
    }

    private AccessibilityService mAccessibilityService;
    private AccessibilityEvent mAccessibilityEvent;

    /**
     * 更新事件
     *
     * @param service
     * @param event
     */
    public void updateEvent(AccessibilityService service, AccessibilityEvent event) {
        if (mAccessibilityService == null && service != null) {
            mAccessibilityService = service;
        }
        if (event != null) {
            mAccessibilityEvent = event;
        }
    }

    public void getNotic() {
        /*if ( mAccessibilityEvent.getEventType() == AccessibilityEvent.TYPE_NOTIFICATION_STATE_CHANGED ) {
            //通知栏事件
        } else {
            //非通知栏事件    处理其他事件
        }

        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();

        List<AccessibilityNodeInfo> listItemNodes = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/apr");
        if ( listItemNodes.isEmpty() ) {
            //反正不是在首页 不理会
        } else {
            //在首页
        }

        if (nodeInfo!=null){
            List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByText(text);
        }

        List<AccessibilityNodeInfo> hongbaoList = nodeInfo.findAccessibilityNodeInfosByText("微信红包");
        List<AccessibilityNodeInfo> weikaiList = nodeInfo.findAccessibilityNodeInfosByViewId("com.tencent.mm:id/aeb");
        Log.e("looter","发现红包检测数量 : " + hongbaoList.size());
        for (int i = 0; i < weikaiList.size(); i++) {
            if (weikaiList.get(i).getText().equals("领取红包")){
                AccessibilityNodeInfo curNodeInfo = weikaiList.get(i);
                curNodeInfo.getParent().performAction(AccessibilityNodeInfo.ACTION_CLICK);
            }
        }*/


    }

    /**
     * 根据Text搜索所有符合条件的节点，模糊搜索方式
     *
     * @param text
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public boolean clickText(String text) {
        AccessibilityNodeInfo nodeInfo = getRootNodeInfo();

        if (nodeInfo != null) {
            List<AccessibilityNodeInfo> nodeInfos = nodeInfo.findAccessibilityNodeInfosByText(text);
            return performClick(nodeInfos);
        }

        return false;
    }

    /**
     * 获取根节点
     *
     * @return
     */
    private AccessibilityNodeInfo getRootNodeInfo() {
        LogUtils.e(TAG, "getRootNodeInfo: ");
        AccessibilityEvent curEvent = mAccessibilityEvent;
        AccessibilityNodeInfo nodeInfo = null;
        if (Build.VERSION.SDK_INT >= 16) {
            if (mAccessibilityService != null) {
                // 获得窗体根节点
                nodeInfo = mAccessibilityService.getRootInActiveWindow();
            }
        } else {
            nodeInfo = curEvent.getSource();
        }
        return nodeInfo;
    }

    /**
     * 模拟点击
     *
     * @param nodeInfos
     * @return true 成功； false 失败。
     */
    private boolean performClick(List<AccessibilityNodeInfo> nodeInfos) {
        if (nodeInfos != null && !nodeInfos.isEmpty()) {// 判断是否非空
            AccessibilityNodeInfo nodeInfo;
            for (int i = 0; i < nodeInfos.size(); i++) {
                nodeInfo = nodeInfos.get(i);// 获得要点击的View
                // 进行模拟点击
                if (nodeInfo.isEnabled()) {// 如果可以点击
                    return nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
                }
            }
        }
        return false;
    }
}