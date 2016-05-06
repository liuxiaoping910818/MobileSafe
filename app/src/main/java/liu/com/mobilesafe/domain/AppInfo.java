package liu.com.mobilesafe.domain;

import android.graphics.drawable.Drawable;

/**
 * Created by Administrator on 2016/5/5.
 */
public class AppInfo {

    public String name;
    public String packageName;
    public Drawable icon;

    public boolean isRom;//true表示安装在手机内存中
    public  boolean isUser;//true表示用户应用
}
