package liu.com.mobilesafe.engine;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import liu.com.mobilesafe.R;
import liu.com.mobilesafe.domain.ProcessInfo;

/**
 * 进程管理相关信息提供者
 * Created by Administrator on 2016/5/9.
 */
public class ProcessInfoProvider {

    /**
     * 获取运行中进程 数量
     *
     * @param context
     * @return
     */
    public static int getRunningProcessNum(Context context){

        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        //获取运行中的进程集合
        List<RunningAppProcessInfo> runningAppProcess=am.getRunningAppProcesses();

        return runningAppProcess.size();
    }

    /**
     * 获取正在运行的进程集合
     * @param context
     * @return
     */
    public static ArrayList<ProcessInfo> getRunnningProcesses(Context context) {


        ActivityManager am = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningAppProcesses = am
                .getRunningAppProcesses();// 获取运行中进程集合

        PackageManager pm = context.getPackageManager();
        ArrayList<ProcessInfo> list = new ArrayList<ProcessInfo>();
        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
            ProcessInfo info = new ProcessInfo();
            String packageName = runningAppProcessInfo.processName;// 包名
            info.packageName = packageName;

            int pid = runningAppProcessInfo.pid;// 进程id

            android.os.Debug.MemoryInfo[] processMemoryInfo = am
                    .getProcessMemoryInfo(new int[] { pid });// 根据pid返回内存信息

            long memory = processMemoryInfo[0].getTotalPrivateDirty() * 1024;// 获取当前进程占用内存大小
            // 单位是kb
            info.memory = memory;
            try {
                ApplicationInfo applicationInfo = pm.getApplicationInfo(
                        packageName, 0);// 根据包名获取相关应用的信息
                String name = applicationInfo.loadLabel(pm).toString();
                Drawable icon = applicationInfo.loadIcon(pm);

                int flags = applicationInfo.flags;
                if ((flags & ApplicationInfo.FLAG_SYSTEM) == ApplicationInfo.FLAG_SYSTEM) {
                    // 系统进程
                    info.isUser = false;
                } else {
                    // 用户进程
                    info.isUser = true;
                }

                info.name = name;
                info.icon = icon;
            } catch (PackageManager.NameNotFoundException e) {
                // 某些系统进程没有名称和图标,会走此异常
                info.name = info.packageName;
                info.icon = context.getResources().getDrawable(
                        R.drawable.system_default);
                info.isUser = false;
                e.printStackTrace();
            }

            list.add(info);
        }

        return list;
    }

    /**
     * 获取剩余内存
     * @param context
     * @return
     */
    public static long getAvailMemory(Context context){


        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo outInfo=new ActivityManager.MemoryInfo();
        am.getMemoryInfo(outInfo);
        return  outInfo.availMem;
    }

    /**
     * 获取总的内存大小
     * @param context
     * @return
     */
    public static long getTotalMemory(Context context){


        // 此方法不兼容api16一下
        // ActivityManager am = (ActivityManager) ctx
        // .getSystemService(Context.ACTIVITY_SERVICE);
        //
        // MemoryInfo outInfo = new MemoryInfo();
        // am.getMemoryInfo(outInfo);// 获取内存信息
        //
        // return outInfo.totalMem;
        // 为了解决版本兼容问题, 可以读取/proc/meminfo文件中第一行, 获取总内存大小
        try {
            BufferedReader reader = new BufferedReader(new FileReader(
                    "/proc/meminfo"));//读取文件目录
            String readLine = reader.readLine();// 读取第一行内容

            char[] charArray = readLine.toCharArray();
            StringBuffer sb = new StringBuffer();
            for (char c : charArray) {
                if (c >= '0' && c <= '9') {// 判断是否是数字
                sb.append(c);
            }
        }

            String total = sb.toString();// 单位kb,需要转成字节
            return Long.parseLong(total) * 1024;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static void killAll(Context context){

        ActivityManager am= (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<RunningAppProcessInfo> runningProcess=am.getRunningAppProcesses();

        for (RunningAppProcessInfo runningProcessInfo:runningProcess){

            String packageName=runningProcessInfo.processName;
            if (packageName.equals(context.getPackageName()));

        }
    }

}
