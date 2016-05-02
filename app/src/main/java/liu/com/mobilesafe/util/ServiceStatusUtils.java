package liu.com.mobilesafe.util;

import android.app.ActivityManager;
import android.content.Context;

import java.util.List;

/**
 * Created by Administrator on 2016/4/30.
 */
public class ServiceStatusUtils {


    /**
     * 判断服务是否正在运行
     *
     * @param serviceName
     * @param ctx
     * @return
     */
    public static boolean isServiceRunning(String serviceName, Context ctx) {
        // 活动管理器
        ActivityManager am = (ActivityManager) ctx
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> runningServices = am.getRunningServices(100);// 获取运行的服务,参数表示最多返回的数量

        for (ActivityManager.RunningServiceInfo runningServiceInfo : runningServices) {
            String className = runningServiceInfo.service.getClassName();
            if (className.equals(serviceName)) {
                return true;// 判断服务是否运行
            }
        }

        return false;
    }
}
