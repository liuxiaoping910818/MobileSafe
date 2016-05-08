package liu.com.mobilesafe.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import liu.com.mobilesafe.domain.ChildInfo;
import liu.com.mobilesafe.domain.GroupInfo;

/**
 * Created by Administrator on 2016/5/8.
 */
public class CommonNumberDao {
    private static final String PATH = "/data/data/liu.com.mobilesafe/files/commonnum.db";

    /**
     * 获取常用号码组的信息
     */
    public static ArrayList<GroupInfo> getCommonNumberGroups(){

        // 打开数据库, 只支持从data/data目录打开,
        // 不能从assets打开
        SQLiteDatabase database=SQLiteDatabase.openDatabase(PATH,null,SQLiteDatabase.OPEN_READONLY);

        Cursor cursor=database.query("classlist",new String[]{"name","idx"},null,null,null,null,null);

        ArrayList<GroupInfo> list=new ArrayList<>();
        while (cursor.moveToNext()){

            GroupInfo info=new GroupInfo();
            String name=cursor.getString(0);
            String idx=cursor.getString(1);

            info.name=name;
            info.idx=idx;
            info.children=getCommonNumberChildren(idx, database);

            list.add(info);


        }

        cursor.close();
        database.close();


        return list;
    }

    /**
     * 获取某个组孩子的信息
     *
     * @param idx
     * @param database
     * @return
     */
    public static ArrayList<ChildInfo> getCommonNumberChildren(String idx,
                                                               SQLiteDatabase database) {
        Cursor cursor = database.query("table" + idx, new String[] { "number",
                "name" }, null, null, null, null, null);

        ArrayList<ChildInfo> list = new ArrayList<ChildInfo>();
        while (cursor.moveToNext()) {
            ChildInfo info = new ChildInfo();
            String number = cursor.getString(0);
            String name = cursor.getString(1);
            info.number = number;
            info.name = name;

            list.add(info);

        }

        cursor.close();
        return list;
    }

}
