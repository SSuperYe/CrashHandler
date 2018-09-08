package com.suchhard.crashhelper;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import java.util.LinkedList;
import java.util.List;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/08/14 18:32
 * @email superrhye@163.com
 */

public class ActivityLifeManager implements Application.ActivityLifecycleCallbacks {

    private List<Activity> activityList = new LinkedList<>();


    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        addActivity(activity);
    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {

    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        removeActivity(activity);
    }

    private void addActivity(Activity activity) {
        if (activityList == null) {
            activityList = new LinkedList<>();
        }
        if (!activityList.contains(activity)) {
            activityList.add(activity);
        }
    }

    private void removeActivity(Activity activity) {
        if (activityList != null && activityList.contains(activity)) {
            activityList.remove(activity);
            if (activityList.isEmpty()) {
                activityList = null;
            }
        }
    }

    public void removeAllActivity() {
        for (Activity activity : activityList) {
            if (activity != null && !activity.isFinishing()) {
                activity.finish();
                activity.overridePendingTransition(0, 0);//取消过场动画
            }
        }
    }
}
