package liu.com.mobilesafe.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/5/9.
 */
public class ProcessInfo {

    public String name;
    public  String packageName;
    public Drawable icon;
    public  long memory;

    public boolean isUser;//true表示用户进程
    public boolean isChecked;//表示当前Item是否勾选
}
