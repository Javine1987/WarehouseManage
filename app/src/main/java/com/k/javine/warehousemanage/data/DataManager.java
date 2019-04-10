package com.k.javine.warehousemanage.data;

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

    public List<StockItem> getStockItems() {
        mIsStockModify = false;
        if (mStockItemList.size() == 0) {
            StockItem item = new StockItem();
            item.setId("XY-1153");
            item.setName("欣页1153");

            HashMap<String, TreeMap<String, Integer>> colorSizeMap = new HashMap<>();

            TreeMap<String, Integer> tempMap = new TreeMap<>();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("红色", tempMap);
            item.setColors("黑色,红色");
            item.setSizes("70,75,80,85,90");

            item.setColorSizeMap(colorSizeMap);
            item.setPrice(120.0f);
            mStockItemList.add(item);

            item = new StockItem();
            item.setId("XY-1353");
            item.setName("欣页1353");
            tempMap = new TreeMap<>();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setColors("黑色,红色");
            item.setSizes("70,75,80,85,90");
            item.setPrice(120.0f);

            mStockItemList.add(item);
            item = new StockItem();
            item.setId("ZY-1358");
            item.setName("欣页1358");
            tempMap = new TreeMap<>();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setColors("黑色,红色");
            item.setSizes("70,75,80,85,90");
            item.setPrice(120.0f);
            mStockItemList.add(item);
            item = new StockItem();
            item.setId("Zz-110");
            item.setName("欣页110");
            tempMap = new TreeMap<>();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("70", 15);
            tempMap.put("75", 15);
            tempMap.put("80", 10);
            tempMap.put("85", 10);
            tempMap.put("90", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setColors("黑色,红色");
            item.setSizes("70,75,80,85,90");
            item.setPrice(120.0f);
            mStockItemList.add(item);
        }
        return mStockItemList;
    }

    public void delStockItem(StockItem item) {
        mIsStockModify = true;
        mStockItemList.remove(item);
    }

    public void addStockItem(StockItem item) {
        mIsStockModify = true;
        mStockItemList.add(item);
    }

    public void addStockItem(Product product) {
        mIsStockModify = true;
        if (mStockItemList != null) {
            mStockItemList.add(convertProduct2StockItem(product));
        }
    }

    public boolean isStockModify() {
        return mIsStockModify;
    }

    public StockItem convertProduct2StockItem(Product product) {
        StockItem stockItem = new StockItem();
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
