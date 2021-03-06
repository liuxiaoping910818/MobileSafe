package liu.com.mobilesafe.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;

import java.util.Timer;
import java.util.TimerTask;

import liu.com.mobilesafe.engine.ProcessInfoProvider;

/**
 * 锁屏清理
 */
public class AutoKillService extends Service {

    private InnerScreenOffReceiver mReceiver;
    private Timer mTimer;


    @Override
    public IBinder onBind(Intent intent) {
      return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        mReceiver=new InnerScreenOffReceiver();
        IntentFilter filter=new IntentFilter(Intent.ACTION_SCREEN_OFF);
        //注册一个广播
        registerReceiver(mReceiver,filter);

        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                System.out.print("定时清理");
            }
        },0,5000);


    }
    class InnerScreenOffReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ProcessInfoProvider.killAll(context);
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // 注销广播
        unregisterReceiver(mReceiver);
        mReceiver = null;

        // 停止定时器
        mTimer.cancel();
        mTimer = null;
    }
}
