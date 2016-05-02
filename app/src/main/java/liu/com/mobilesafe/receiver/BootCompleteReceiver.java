package liu.com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import liu.com.mobilesafe.util.PrefUtils;


/**
 * Created by Administrator on 2016/4/27.
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        boolean protect= PrefUtils.getBoolean("protect",false,context);
        if (!protect){//如果 没有开启手机防盗，则直接返回

            return;
        }

        String saveSim=PrefUtils.getString("bind_sim",null,context);
        if (!TextUtils.isEmpty(saveSim)){
            //获取当前的SIM卡，和保存的sim卡进行比较
            TelephonyManager tm= (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);

            String currentSim=tm.getSimSerialNumber()+"xxx";//当前的sim卡
            if (!saveSim.endsWith(currentSim)){
                System.out.println("sim卡已经变化!发送报警短信!");
                String safePhone=PrefUtils.getString("save_phone", "", context);
                SmsManager sm=SmsManager.getDefault();
                sm.sendTextMessage(safePhone,null,"sim card changed!!!",null,null);
            }
        }

    }
}
