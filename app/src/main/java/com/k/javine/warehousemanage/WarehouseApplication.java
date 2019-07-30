package com.k.javine.warehousemanage;

import android.app.Application;
import android.content.Context;

import com.k.javine.warehousemanage.data.db.DaoMaster;
import com.k.javine.warehousemanage.data.db.DaoSession;
import com.k.javine.warehousemanage.data.db.ProductDbHelper;

import org.greenrobot.greendao.database.Database;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-7-30
 */
public class WarehouseApplication extends Application {

    private static Context mAppContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mAppContext = this;
    }

    public static Context getAppContext() {
        return mAppContext;
    }
}
