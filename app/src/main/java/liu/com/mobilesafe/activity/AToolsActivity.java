package liu.com.mobilesafe.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.io.File;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.util.SmsUtils;
import liu.com.mobilesafe.util.ToastUtils;


public class AToolsActivity extends AppCompatActivity {

    private ProgressBar pbProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atools);
        pbProgress = (ProgressBar) findViewById(R.id.pb_progress);
    }

    public  void appLock(View view){

        startActivity(new Intent(this,AppLockActivity.class));
    }

    /**
     * 电话归属地
     */
    public void addressQuery(View view) {
        startActivity(new Intent(this, AddressQueryActivity.class));
    }

    /**
     * 短信备份,采用回调机制来来进行短信备份：具体逻辑是，先从数据库中查出数据 ，然后判断sd卡是否持载，存在的话奖短信以xml文件的方式存入sd卡中
     *
     * @param view
     */
    public void smsBackup(View view) {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setMessage("正在备份短信...");
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);// 水平方向进度条,可以展示进度
            dialog.show();

            new Thread() {
                public void run() {
                    File output = new File(Environment
                            .getExternalStorageDirectory().getAbsolutePath()
                            + "/sms66.xml");
                    SmsUtils.smsBackup(getApplicationContext(), output,
                            new SmsUtils.SmsCallback() {

                                @Override
                                public void preSmsBackup(int count) {
                                    dialog.setMax(count);
                                    pbProgress.setMax(count);
                                }

                                @Override
                                public void onSmsBackup(int progress) {
                                    dialog.setProgress(progress);
                                    pbProgress.setProgress(progress);
                                }
                            });

                    dialog.dismiss();
                }

                ;
            }.start();
        } else {
            ToastUtils.showToast(this, "sdcard不存在!");
        }
    }


    /**
     *
     * 常用号码查询
     * @param view
     */

    public void commonNumberQuery(View view) {

        startActivity(new Intent(this,CommonNumberActivity.class));
    }
}
