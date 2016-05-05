package liu.com.mobilesafe.activity;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.service.AddressService;
import liu.com.mobilesafe.service.BlacknumberService;
import liu.com.mobilesafe.util.PrefUtils;
import liu.com.mobilesafe.util.ServiceStatusUtils;
import liu.com.mobilesafe.view.SettingItemClickView;
import liu.com.mobilesafe.view.SettingItemView;


public class SettingActivity extends Activity {

    private SettingItemView sivUpdate;
    private SettingItemView sivAddress;
    private SettingItemView sivBlackNumber;
    private SettingItemClickView sicStyle;
    private SettingItemClickView sicLocation;

    private String[] mItems = new String[] { "半透明", "活力橙", "卫士蓝", "金属灰", "苹果绿" };;

    // private SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        // sp = getSharedPreferences("config", MODE_PRIVATE);
        initUpdate();
        initAddress();
        initAddressStyle();
        initAddressLocation();
        initBlacknumber();
    }

    /**
     * 黑名单设置
     */
    private void initBlacknumber() {

        sivBlackNumber= (SettingItemView) findViewById(R.id.siv_black_number);

        //判断服务是否开启，运行时才勾选，否则不勾选
        boolean serviceRunning=ServiceStatusUtils.isServiceRunning("liu.com.mobilesafe.service.BlacknumberService",this);
        sivBlackNumber.setChecked(serviceRunning);

        sivBlackNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent service=new Intent(getApplicationContext(), BlacknumberService.class);

                if (sivBlackNumber.isChecked()){


                    sivBlackNumber.setChecked(false);
                    stopService(service);

                }else {

                    sivBlackNumber.setChecked(true);
                    stopService(service);
                }
            }
        });
    }

    /**
     * 归属地位置修改
     */
    private void initAddressLocation() {
        sicLocation = (SettingItemClickView) findViewById(R.id.sic_location);
        sicLocation.setTitle("归属地提示框位置");
        sicLocation.setDesc("设置归属地提示框的显示位置");
        sicLocation.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // 跳到位置修改的页面
                startActivity(new Intent(getApplicationContext(),
                        DragViewActivity.class));
            }
        });
    }

    /**
     * 初始化归属地样式设置
     */
    private void initAddressStyle() {
        sicStyle = (SettingItemClickView) findViewById(R.id.sic_style);
        sicStyle.setTitle("归属地提示框风格");

        // 获取已保存的样式
        int style = PrefUtils.getInt("address_style", 0, this);
        sicStyle.setDesc(mItems[style]);

        sicStyle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                showChooseDialog();
            }
        });
    }

    /**
     * 归属地风格选择弹窗
     */
    protected void showChooseDialog() {
        // 获取已保存的样式
        int style = PrefUtils.getInt("address_style", 0, this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("归属地提示框风格");
        builder.setIcon(R.drawable.ic_launcher);
        builder.setSingleChoiceItems(mItems, style,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 保存当前风格
                        PrefUtils.putInt("address_style", which,
                                getApplicationContext());
                        dialog.dismiss();

                        sicStyle.setDesc(mItems[which]);// 更新描述
                    }

                });

        builder.setNegativeButton("取消", null);
        builder.show();
    }

    /**
     * 归属地显示设置
     */
    private void initAddress() {
        sivAddress = (SettingItemView) findViewById(R.id.siv_address);

        // 判断服务是否运行, 运行时才勾选,否则不勾选
        boolean serviceRunning = ServiceStatusUtils.isServiceRunning(
                "liu.com.mobilesafe.service.AddressService", this);
        sivAddress.setChecked(serviceRunning);

        sivAddress.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent service = new Intent(getApplicationContext(),
                        AddressService.class);
                if (sivAddress.isChecked()) {
                    sivAddress.setChecked(false);
                    stopService(service);// 关闭归属地显示的服务
                } else {
                    sivAddress.setChecked(true);
                    startService(service);// 开启归属地显示的服务
                }
            }
        });
    }

    /**
     * 初始化自动更新设置
     */
    private void initUpdate() {
        sivUpdate = (SettingItemView) findViewById(R.id.siv_update);
        // sivUpdate.setTitle("自动更新设置");

        boolean autoUpdate = PrefUtils.getBoolean("auto_update", true, this);
        // if (autoUpdate) {
        // //sivUpdate.setDesc("自动更新已开启");
        // sivUpdate.setChecked(true);
        // } else {
        // //sivUpdate.setDesc("自动更新已关闭");
        // sivUpdate.setChecked(false);
        // }
        sivUpdate.setChecked(autoUpdate);

        sivUpdate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (sivUpdate.isChecked()) {
                    sivUpdate.setChecked(false);
                    // sivUpdate.setDesc("自动更新已关闭");
                    // sp.edit().putBoolean("auto_update", false).commit();
                    PrefUtils.putBoolean("auto_update", false,
                            getApplicationContext());
                } else {
                    sivUpdate.setChecked(true);
                    // sivUpdate.setDesc("自动更新已开启");
                    // sp.edit().putBoolean("auto_update", true).commit();
                    PrefUtils.putBoolean("auto_update", true,
                            getApplicationContext());
                }
            }
        });
    }

}
