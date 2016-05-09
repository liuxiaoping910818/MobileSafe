package liu.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.service.AutoKillService;
import liu.com.mobilesafe.util.PrefUtils;
import liu.com.mobilesafe.util.ServiceStatusUtils;

public class ProcessSettingActivity extends AppCompatActivity {

    private CheckBox cbShowSystem;
    private CheckBox cbAutoKill;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_process_setting);

        cbShowSystem= (CheckBox) findViewById(R.id.cb_show_system);
        cbAutoKill= (CheckBox) findViewById(R.id.cb_auto_kill);

        boolean showSystem= PrefUtils.getBoolean("show_system",true,this);

        if (showSystem){

            cbShowSystem.setChecked(true);
            cbShowSystem.setTag("显示系统进程");

        }else {

            cbShowSystem.setChecked(false);
            cbShowSystem.setText("不显示系统进程");
        }

        cbShowSystem.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked){

                    cbShowSystem.setText("显示系统进程");
                    PrefUtils.putBoolean("show_system",true,getApplicationContext());

                }else {

                    cbShowSystem.setText("不显示系统进程 ");
                    PrefUtils.putBoolean("不显示系统进程",false,getApplicationContext());
                }
            }
        });

        boolean serviceRunning= ServiceStatusUtils.isServiceRunning("",this);
        if (serviceRunning){

            cbAutoKill.setChecked(true);
            cbAutoKill.setText("锁屏清理已开启");
        }else {

            cbAutoKill.setChecked(false);
            cbAutoKill.setText("锁屏清理已关闭");
        }

        cbAutoKill.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Intent service=new Intent(getApplicationContext(),AutoKillService.class);
                if (isChecked) {
                    cbAutoKill.setText("锁屏清理已开启");
                    startService(service);
                } else {
                    cbAutoKill.setText("锁屏清理已关闭");
                    stopService(service);
                }
            }
        });
    }
}
