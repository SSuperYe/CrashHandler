package com.suchhard.crashhandler;

import android.app.Application;

import com.suchhard.crashhelper.ActivityLifeManager;
import com.suchhard.crashhelper.CallBack;
import com.suchhard.crashhelper.CrashHelper;

/**
 * @author Mr.Ye
 * @description
 * @datetime 2018/08/14 18:26
 * @email superrhye@163.com
 */

public class APP extends Application {

    public static ActivityLifeManager activityLifeManager = new ActivityLifeManager();

    @Override
    public void onCreate() {
        super.onCreate();
        registerActivityLifecycleCallbacks(activityLifeManager);
        CrashHelper.getInstance()
                .setMessage("程序炸了，正在退出……")
                .setTime(5000)
                .init(this, new CallBack() {
                    @Override
                    public void onCrash() {
                        //可自行清空栈内activity
                        activityLifeManager.removeAllActivity();
                    }
                });
    }
}
