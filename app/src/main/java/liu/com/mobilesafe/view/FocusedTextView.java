package liu.com.mobilesafe.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.TextView;

/**
 * 强制获取焦点的textView
 * Created by Administrator on 2016/4/24.
 */
public class FocusedTextView extends TextView {

    private static final String TAG = "FocusedTextView";

    /**
     * 从代码中new 对象
     * @param context
     */
    public FocusedTextView(Context context) {
        super(context);
    }

    /**
     *
     * @param context
     * @param attrs :TextView的属性集合，系统底层解析时应会调用此方法，xml文件中的各个组件的属性就是属于些集合的
     */
    public FocusedTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.d(TAG, "FocusedTextView: attrs++++++++++++++++++++++++++++++++");

    }

    /**
     *
     * @param context
     */
    public FocusedTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.d(TAG, "FocusedTextView: attrs,defStyleAttr================================");
    }

    //判断是否有获得焦点
    @Override
    public boolean isFocused() {

        return true;
    }
}
