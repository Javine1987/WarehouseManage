package com.k.javine.warehousemanage.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.greenrobot.greendao.database.Database;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-7-30
 */
public class ProductOpenHelper extends DaoMaster.DevOpenHelper{

    public ProductOpenHelper(Context context, String name) {
        super(context, name);
    }

    public ProductOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
    }
}
