package liu.com.mobilesafe.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.telephony.SmsMessage;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.service.LocationService;


/**
 * 拦截短信
 *
 * <receiver android:name=".receiver.SmsReceiver" > <intent-filter
 * android:priority="2147483647" > <action
 * android:name="android.provider.Telephony.SMS_RECEIVED" /> </intent-filter>
 * </receiver>
 *
 * 需要权限:<uses-permission android:name="android.permission.RECEIVE_SMS" />
 *
 * @author Kevin
 *
 */
public class SmsReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {


        Object[] objs= (Object[]) intent.getExtras().get("pdus");
        for (Object obj: objs){

            SmsMessage sms= SmsMessage.createFromPdu((byte[]) obj);

            String originatingAddress=sms.getOriginatingAddress();
            String messageBody=sms.getMessageBody();

            System.out.println("号码:" + originatingAddress + ";内容:"
                    + messageBody);

            if ("#*alarm*#".equals(messageBody)){

                MediaPlayer player=MediaPlayer.create(context, R.raw.ylzs);
                // 播放媒体音乐的音量和手机铃声音量无关
                player.setVolume(1f,1f);// 音量最大, 基于系统音量的比值
                player.setLooping(true);//单曲循环
                player.start();

                // 4.4+版本上,无法拦截短信, 调此方法没有, 比如当前应用时默认短信应用才可以
                // 操作短信数据库, 删除数据库相关短信内容, 间接达到删除短信目的
                abortBroadcast();

            }else if ("#*location*#".equals(messageBody)){

                System.out.print("手机定位");
                //启动位置监听服务
                context.startService(new Intent(context, LocationService.class));
                abortBroadcast();//中断短信
            }else if ("#*lockscreen*#".equals(messageBody)){

                System.out.print("手机定位");
                abortBroadcast();//中断短信
            }else if ("#*wipedata*#".equals(messageBody)){

                System.out.print("手机定位");
                abortBroadcast();//中断短信
            }

        }



    }
}
