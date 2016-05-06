package liu.com.mobilesafe.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.util.MD5Utils;
import liu.com.mobilesafe.util.PrefUtils;
import liu.com.mobilesafe.util.ToastUtils;


/**
 * 主页面
 *
 * @author Kevin
 *
 */
/**
 * 主页面
 *
 * @author Kevin
 *
 */
public class HomeActivity extends Activity {

    private GridView gvHome;

    private String[] mHomeNames = new String[] { "手机防盗", "通讯卫士", "软件管理",
            "进程管理", "流量统计", "手机杀毒", "缓存清理", "高级工具", "设置中心" };

    private int[] mImageIds = new int[] { R.drawable.home_safe,
            R.drawable.home_callmsgsafe, R.drawable.home_apps,
            R.drawable.home_taskmanager, R.drawable.home_netmanager,
            R.drawable.home_trojan, R.drawable.home_sysoptimize,
            R.drawable.home_tools, R.drawable.home_settings };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actvity_home);

        gvHome = (GridView) findViewById(R.id.gv_home);
        gvHome.setAdapter(new HomeAdapter());

        gvHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        // 手机防盗
                        showSafeDialog();
                        break;
                    case 1:
                        startActivity(new Intent(getApplicationContext(),BlackNumberActivity.class));
                        break;
                    case 2:
                        // 软件管理
                        startActivity(new Intent(getApplicationContext(),
                                AppManagerActivity.class));
                        break;
                    case 7:
                        startActivity(new Intent(getApplicationContext(),AToolsActivity.class));
                        break;

                    case 8:
                        // 设置中心
                        startActivity(new Intent(getApplicationContext(),
                                SettingActivity.class));
                        break;

                    default:
                        break;
                }
            }
        });
    }

    /**
     * 手机防盗弹窗
     */
    protected void showSafeDialog() {
        String pwd = PrefUtils.getString("password", null, this);
        if (!TextUtils.isEmpty(pwd)) {
            // 输入密码弹窗
            showInputPwdDialog();
        } else {
            // 设置密码弹窗
            showSetPwdDialog();
        }
    }

    /**
     * 输入密码弹窗
     */
    private void showInputPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_input_pwd, null);// 给dialog设定特定布局
        // dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);// 去掉上下左右边距, 兼容2.x版本

        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pwd = etPwd.getText().toString().trim();
                if (!TextUtils.isEmpty(pwd)) {
                    String savePwd = PrefUtils.getString("password", null,
                            getApplicationContext());
                    if (MD5Utils.encode(pwd).equals(savePwd)) {
                        // 密码正确
                        dialog.dismiss();

                        // 跳到手机防盗
                        startActivity(new Intent(getApplicationContext(),
                                LostAndFindActivity.class));
                    } else {
                        ToastUtils.showToast(getApplicationContext(), "密码错误!");
                    }
                } else {
                    ToastUtils.showToast(getApplicationContext(), "输入内容不能为空!");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    /**
     * 设置密码弹窗
     */
    private void showSetPwdDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final AlertDialog dialog = builder.create();
        View view = View.inflate(this, R.layout.dialog_set_pwd, null);// 给dialog设定特定布局
        // dialog.setView(view);
        dialog.setView(view, 0, 0, 0, 0);// 去掉上下左右边距, 兼容2.x版本

        Button btnOK = (Button) view.findViewById(R.id.btn_ok);
        Button btnCancel = (Button) view.findViewById(R.id.btn_cancel);

        final EditText etPwd = (EditText) view.findViewById(R.id.et_pwd);
        final EditText etPwdConfirm = (EditText) view
                .findViewById(R.id.et_pwd_confirm);

        btnOK.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String pwd = etPwd.getText().toString().trim();
                String pwdConfirm = etPwdConfirm.getText().toString().trim();

                if (!TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdConfirm)) {
                    if (pwd.equals(pwdConfirm)) {
                        // 保存密码
                        PrefUtils.putString("password", MD5Utils.encode(pwd),
                                getApplicationContext());
                        dialog.dismiss();

                        // 跳到手机防盗
                        startActivity(new Intent(getApplicationContext(),
                                LostAndFindActivity.class));
                    } else {
                        ToastUtils.showToast(getApplicationContext(),
                                "两次密码不一致!");
                    }
                } else {
                    ToastUtils.showToast(getApplicationContext(), "输入内容不能为空!");
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class HomeAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mHomeNames.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = View.inflate(getApplicationContext(),
                    R.layout.list_item_home, null);

            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            ImageView ivIcon = (ImageView) view.findViewById(R.id.iv_icon);

            tvName.setText(mHomeNames[position]);
            ivIcon.setImageResource(mImageIds[position]);

            return view;
        }

    }
}
