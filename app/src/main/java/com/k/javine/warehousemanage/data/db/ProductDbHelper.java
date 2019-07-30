package com.k.javine.warehousemanage.data.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.k.javine.warehousemanage.WarehouseApplication;
import com.k.javine.warehousemanage.data.Product;
import com.k.javine.warehousemanage.data.StockItem;

import org.greenrobot.greendao.database.Database;

import java.util.ArrayList;
import java.util.List;

/**
 * @文件描述 : 数据库辅助类
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-21
 */
public class ProductDbHelper{
    private static final String DB_NAME = "product-db";
    private static DaoSession mDaoSession;
    private static DaoMaster mDaoMaster;
    private static Context mAppContext;
    private static ProductDbHelper mInstance;

    public static ProductDbHelper getInstance() {
        if (mInstance == null) {
            synchronized (ProductDbHelper.class) {
                if (mInstance == null) {
                    mInstance = new ProductDbHelper(WarehouseApplication.getAppContext());
                }
            }
        }
        return mInstance;
    }

    private ProductDbHelper(Context context) {
        mAppContext = context.getApplicationContext();
    }

    private synchronized DaoMaster getDaoMaster() {
        if (mDaoMaster == null) {
            ProductOpenHelper helper = new ProductOpenHelper(mAppContext, DB_NAME);
            mDaoMaster = new DaoMaster(helper.getWritableDb());
        }
        return mDaoMaster;
    }

    public DaoSession getDaoSession() {
        if (mDaoSession == null) {
            if (mDaoMaster == null) {
                mDaoMaster = getDaoMaster();
            }
            if (mDaoMaster == null) return null;
            mDaoSession = mDaoMaster.newSession();
        }
        return mDaoSession;
    }

    // 库存相关的数据库操作
    public void saveStockItemDb(StockItem item) {
        getDaoSession().getStockEntityDao().insert(convertStockItemToEntity(item));
    }

    public void deleteStockItemDb(StockItem item) {
        getDaoSession().getStockEntityDao().delete(convertStockItemToEntity(item));
    }

    public List<StockItem> queryAllStockItemFromDb() {
        List<StockEntity> entities = getDaoSession().getStockEntityDao()
                .queryBuilder()
                .orderDesc(StockEntityDao.Properties.TimeStampId)
                .list();
        List<StockItem> items = null;
        if (entities != null) {
            items = new ArrayList<>();
            for (StockEntity entity : entities) {
                items.add(convertEntity2StockItem(entity));
            }
        }
        return items;
    }

    public StockEntity convertStockItemToEntity(StockItem item) {
        return new StockEntity(
                item.getId(),
                item.getTimeStampId(),
                item.getName(),
                item.getSizes(),
                item.getColors(),
                item.getContentJson(),
                item.getPrice(),
                item.getTotalCount()
        );
    }
    public StockItem convertEntity2StockItem(StockEntity entity) {
        StockItem item = new StockItem();
        item.setId(entity.getId());
        item.setTimeStampId(entity.getTimeStampId());
        item.setPrice(entity.getPrice());
        item.setSizes(entity.getSizes());
        item.setColors(entity.getColors());
        item.setTotalCount(entity.getCount());
        item.setName(entity.getName());
        item.setContentJson(entity.getDetail());
        return item;
    }


}
