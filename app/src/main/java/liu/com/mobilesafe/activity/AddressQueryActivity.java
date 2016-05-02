package liu.com.mobilesafe.activity;

import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.dao.AddressDao;
import liu.com.mobilesafe.util.ToastUtils;


/**
 * 归属地查询
 */
public class AddressQueryActivity extends AppCompatActivity {

    private EditText etNumber;
    private Button   btnStart;
    private TextView tvResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_query);

        etNumber=(EditText) findViewById(R.id.et_number);
        btnStart=(Button) findViewById(R.id.btn_start);
        tvResult= (TextView) findViewById(R.id.tv_result);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String number=etNumber.getText().toString().trim();
                if (!TextUtils.isEmpty(number)){

                    String address= AddressDao.getAddress(number);
                    tvResult.setText(address);
                }else {

                    //添加抖动的效果
                    //ToastUtils.showToast(getApplicationContext(),"输入的内容不能为空");

                    Animation shake= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shake);
                   /* shake.setInterpolator(new Interpolator() {自定义插补器
                        @Override
                        public float getInterpolation(float x) {
                            float y=x;
                            return y;
                        }
                    });*/

                    etNumber.startAnimation(shake);
                    vibrator();
                }
            }
        });

        //监听文本框的变化,
        etNumber.addTextChangedListener(new TextWatcher() {

            //发生变化后调用
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            //变化 之前
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            //变化后调用
            @Override
            public void afterTextChanged(Editable s) {

                String address=AddressDao.getAddress(s.toString());
                tvResult.setText(address);
            }
        });
    }
    /**
     * 振动 需要权限:android.permission.VIBRATE
     */
    public  void vibrator(){

        //振动器
        Vibrator vibrator= (Vibrator) getSystemService(VIBRATOR_SERVICE);
        // 参1:振动模式,奇数位表示休息时间,偶数为表示振动时间
        // 参2:-1表示不重复,
        // 0表示从第0个位置开始重复
        vibrator.vibrate(new long[]{1000,2000,2000,3000},2);
       // vibrator.cancel();

    }

}
