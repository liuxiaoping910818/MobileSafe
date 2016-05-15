package liu.com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import liu.com.mobilesafe.engine.ProcessInfoProvider;

/**
 * 一键清理广播
 */

public class KillReceiver extends BroadcastReceiver {
    public KillReceiver() {
    }

    @Override
    public void onReceive(Context context, Intent intent) {


        ProcessInfoProvider.killAll(context);
    }
}
