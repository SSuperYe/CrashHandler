# CrashHandler[![](https://jitpack.io/v/SSuperYe/CrashHandler.svg)](https://jitpack.io/#SSuperYe/CrashHandler)
友好处理APP闪退后可能存在的重启的问题
## 如何使用<br>
```
请在application里执行
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
```
