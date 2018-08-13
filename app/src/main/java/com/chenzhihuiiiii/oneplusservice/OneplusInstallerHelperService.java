package com.chenzhihuiiiii.oneplusservice;

import android.accessibilityservice.AccessibilityService;
import android.app.ActivityManager;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Copyright (C) 2018, PING AN TECHNOLOGIES CO., LTD.
 * OnePlusInstallerHelperService
 * Description
 *
 * @author chenzhihui193
 * Ver 1.0, 2018/8/9, chenzhihui193, Create file
 */

public class OneplusInstallerHelperService extends AccessibilityService {
    static final String TAG = "hahahaha";

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        log(event.getPackageName().toString());
        if (!"com.google.android.packageinstaller".equals(event.getPackageName())) {
            return;
        }
        log("onAccessibilityEvent");

        AccessibilityNodeInfo rootNode = getRootInActiveWindow();
        if (rootNode == null) {
            log("rootNode: null");
            return;
        }
        findAndClickView(rootNode);
    }

    /**
     * 查找按钮并点击
     */
    private void findAndClickView(AccessibilityNodeInfo rootNode) {
        List<AccessibilityNodeInfo> nodeInfoList = new ArrayList<>();
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("安装"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("完成"));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("install));
        nodeInfoList.addAll(rootNode.findAccessibilityNodeInfosByText("done));
        for (AccessibilityNodeInfo nodeInfo : nodeInfoList) {
            nodeInfo.performAction(AccessibilityNodeInfo.ACTION_CLICK);
        }
    }


    /**
     * @param info
     */
    public void traverseChilds(AccessibilityNodeInfo info) {
        if (info.getChildCount() == 0) {
            log("windowId:" + info.getWindowId() + " widget:" + info.getClassName()
                    + " showDialog:" + info.canOpenPopup() + " text:" + info.getText());
        } else {
            for (int i = 0; i < info.getChildCount(); i++) {
                if (info.getChild(i) != null) {
                    traverseChilds(info.getChild(i));
                }
            }
        }
    }


    @Override
    public void onInterrupt() {
        log("onInterrupt");
    }

    @Override
    protected void onServiceConnected() {
        super.onServiceConnected();
        log("onServiceConnected");
    }

    private void log(String content) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.RunningTaskInfo info = manager.getRunningTasks(1).get(0);
        String shortClassName = info.topActivity.getShortClassName();    //类名
        String className = info.topActivity.getClassName();              //完整类名
        String packageName = info.topActivity.getPackageName();
        Log.e(TAG, className + " " + content);
    }
}

