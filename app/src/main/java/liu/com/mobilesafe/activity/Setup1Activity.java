package liu.com.mobilesafe.activity;

import android.content.Intent;
import android.os.Bundle;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.view.SettingItemView;


/**
 * 设置导向1
 */
public class Setup1Activity extends BaseSetupActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup1);
    }

    @Override
    public void showPrevious() {
    }

    @Override
    public void showNext() {
        startActivity(new Intent(this, Setup2Activity.class));
        finish();
        // 两个activity之间切换的动画, 应该放在finish之后运行
        overridePendingTransition(R.anim.anim_in, R.anim.anim_out);
    }
}
