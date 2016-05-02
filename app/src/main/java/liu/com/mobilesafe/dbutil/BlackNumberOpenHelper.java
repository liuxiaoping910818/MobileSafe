package liu.com.mobilesafe.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/2.
 */
public class BlackNumberOpenHelper extends SQLiteOpenHelper{

    public BlackNumberOpenHelper(Context context){

        super(context,"blacknumber.db",null,1);
    }
    public BlackNumberOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql="create table blacknumber (_id integer primary key autoincrement,number vachar(20),mode integer)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
