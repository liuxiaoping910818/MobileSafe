package liu.com.mobilesafe.service;

import android.app.PendingIntent;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.text.format.Formatter;
import android.widget.RemoteViews;

import java.util.Timer;
import java.util.TimerTask;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.engine.ProcessInfoProvider;
import liu.com.mobilesafe.receiver.MyWidget;

/**
 * 定时更新widget
 *
 * @author Kevin
 *
 */
public class UpdateWidgetService extends Service {

    private Timer mTimer;
    private AppWidgetManager mAWM;
    private InnerScreenReceiver mReceiver;
    public UpdateWidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mAWM=AppWidgetManager.getInstance(this);
        startTimer();

        //监听屏幕开启和关闭广播
        mReceiver=new InnerScreenReceiver();
        IntentFilter filer=new IntentFilter();
        filer.addAction(Intent.ACTION_SCREEN_ON);
        filer.addAction(Intent.ACTION_SCREEN_OFF);
        registerReceiver(mReceiver,filer);



    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);

    }

    protected void updateWidget(){


        ComponentName provider=new ComponentName(this, MyWidget.class);

        RemoteViews views=new RemoteViews(getPackageName(), R.layout.my_widget);
        // 更新TextView文字
        views.setTextViewText(R.id.tv_running_num, "正在运行软件:"
                + ProcessInfoProvider.getRunningProcessNum(this));
        views.setTextViewText(
                R.id.tv_avail_memory,
                "可用内存:"
                        + Formatter.formatFileSize(this,
                        ProcessInfoProvider.getAvailMemory(this)));

        //设置一键清理点击, PendingIntent:延时intent,即不确定何时去调用其
        Intent clearIntent=new Intent();
        clearIntent.setAction("Liu.com.SmartSms.KILL");
        PendingIntent clearPendingIntent=PendingIntent.getBroadcast(this,0,clearIntent,PendingIntent.FLAG_CANCEL_CURRENT);

        mAWM.updateAppWidget(provider,views);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        mTimer.cancel();
        mTimer=null;

        unregisterReceiver(mReceiver);
        mReceiver=null;
    }

    private void startTimer() {

        mTimer=new Timer();
        mTimer.schedule(new TimerTask() {
            @Override
            public void run() {

                updateWidget();

            }
        },0,5000);
    }

    class  InnerScreenReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_SCREEN_ON)){

                startTimer();
            }else {

                mTimer.cancel();
                mTimer=null;
            }
        }
    }
}
