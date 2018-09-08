package com.suchhard.crashhelper;

import android.content.Context;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * @author Mr.Ye
 * @description 友好处理crash
 * @datetime 2018/08/14 18:08
 * @email superrhye@163.com
 */

public class CrashHelper implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "CrashFriendlyHandler";
    private static final String DEFAULT_MESSAGE = "程序出现异常，即将退出～";
    private static final long DEFAULT_TIME = 3000L;

    private static CrashHelper crashHelper = new CrashHelper();

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    private Context mContext;

    private CallBack mCallback;

    /**
     * 闪退/异常关闭提示语
     */
    private CharSequence mHintMessage;

    /**
     * 到关闭前停留时间
     */
    private long mTime;

    private CrashHelper() {
    }

    public static CrashHelper getInstance() {
        return crashHelper;
    }

    public CrashHelper setMessage(CharSequence hint) {
        this.mHintMessage = hint;
        return crashHelper;
    }

    public CrashHelper setTime(long time) {
        this.mTime = time;
        return crashHelper;
    }

    /**
     * 初始化
     */
    public void init(Context context, @NonNull CallBack callBack) {
        this.mContext = context;
        this.mCallback = callBack;
        // 获取系统默认的UncaughtException处理器
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        // 设置该CrashHandler为程序的默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 当UncaughtException发生时会转入该函数来处理
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(mTime == 0 ? DEFAULT_TIME : mTime);
            } catch (InterruptedException e) {
                Log.e(TAG, "InterruptedException : ", e);
            }

            //退出前防止重新启动，调用act管理清除所有栈，暴露出去自行清空栈内act
//            APP.actLifeManager.removeAllActivity();
            mCallback.onCrash();

            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @return true 如果处理了该异常信息返回true，否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(mContext, TextUtils.isEmpty(mHintMessage) ? DEFAULT_MESSAGE : mHintMessage, Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        return true;
    }
}
