package com.k.javine.warehousemanage.data;

import android.content.Context;

import com.k.javine.warehousemanage.data.db.ProductDbHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-2-27
 */
public class DataManager {

    private static DataManager mInstance;
    private List<StockItem> mStockItemList = new ArrayList<>();
    private StockItem mTempStockItem;
    private boolean mIsStockModify = false;

    private DataManager() {

    }

    public static DataManager getInstance() {
        if (mInstance == null) {
            synchronized (DataManager.class) {
                if (mInstance == null) {
                    mInstance = new DataManager();
                }
            }
        }
        return mInstance;
    }

    public List<StockItem> getStockItemsFromDB() {
        mIsStockModify = false;
        mStockItemList = ProductDbHelper.getInstance().queryAllStockItemFromDb();
        return mStockItemList;
    }

    public void delStockItem(StockItem item) {
        mIsStockModify = true;
        mStockItemList.remove(item);
        ProductDbHelper.getInstance().deleteStockItemDb(item);
    }

    public void addStockItem(StockItem item) {
        mIsStockModify = true;
        if (mStockItemList != null) {
            mStockItemList.add(item);
        }
        ProductDbHelper.getInstance().saveStockItemDb(item);
    }

    public void addStockItem(Product product) {
        StockItem item = convertProduct2StockItem(product);
        addStockItem(item);
    }

    public void modifyStockItem(StockItem item) {
        mIsStockModify = true;
        // 19-4-16 find item, and save it
        int index = mStockItemList.indexOf(item);
        item.setColorSizeOptions(item.getColors(), item.getSizes());
        if (index > 0) {
            mStockItemList.set(index, item);
        }
        ProductDbHelper.getInstance().updateStockItemDb(item);
    }

    public boolean isStockModify() {
        return mIsStockModify;
    }

    public StockItem convertProduct2StockItem(Product product) {
        StockItem stockItem = new StockItem();
        stockItem.setTimeStampId(product.getTimeStampId());
        stockItem.setName(product.getName());
        stockItem.setPrice(product.getPrice());
        stockItem.setId(product.getId());
        stockItem.setTotalCount(0);
        stockItem.setSizes(product.getSizes());
        stockItem.setColors(product.getColors());
        stockItem.setColorSizeOptions(product.getColors(), product.getSizes());
        return stockItem;
    }

    public void setTempStockItem(StockItem item) {
        mTempStockItem = item;
    }

    public StockItem getTempStockItem() {
        StockItem item = mTempStockItem;
        mTempStockItem = null;
        return item;
    }

}
