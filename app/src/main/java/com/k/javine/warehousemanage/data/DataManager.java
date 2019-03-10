package com.k.javine.warehousemanage.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-2-27
 */
public class DataManager {

    private static DataManager mInstance;
    private List<StockItem> mStockItemList = new ArrayList<>();

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
        if (mStockItemList.size() == 0) {
            StockItem item = new StockItem();
            item.setId("XY-1153");
            item.setName("欣页");
            HashMap<String, Integer> tempMap = new HashMap<>();
            tempMap.put("170", 50);
            tempMap.put("175", 50);
            tempMap.put("180", 30);
            tempMap.put("185", 40);
            tempMap.put("190", 30);
            item.setSizeMap(tempMap);
            tempMap = new HashMap<>();
            tempMap.put("黑色", 60);
            tempMap.put("红色", 60);
            tempMap.put("灰色", 80);
            item.setColorMap(tempMap);
            item.setPrice(120.0f);
            item.setTotalCount(200);
            item.setTotalMoney(200*120);
            mStockItemList.add(item);
            item = new StockItem();
            item.setId("XY-1353");
            item.setName("欣页");
            tempMap = new HashMap<>();
            tempMap.put("170", 60);
            tempMap.put("175", 50);
            tempMap.put("180", 30);
            tempMap.put("185", 30);
            tempMap.put("190", 30);
            item.setSizeMap(tempMap);
            tempMap = new HashMap<>();
            tempMap.put("黑色", 70);
            tempMap.put("红色", 70);
            tempMap.put("灰色", 60);
            item.setColorMap(tempMap);
            item.setPrice(120.0f);
            item.setTotalCount(200);
            item.setTotalMoney(200*120);
            mStockItemList.add(item);
            item = new StockItem();
            item.setId("ZY-1353");
            item.setName("欣页");
            tempMap = new HashMap<>();
            tempMap.put("170", 60);
            tempMap.put("175", 50);
            tempMap.put("180", 30);
            tempMap.put("185", 30);
            tempMap.put("190", 30);
            item.setSizeMap(tempMap);
            tempMap = new HashMap<>();
            tempMap.put("黑色", 70);
            tempMap.put("红色", 70);
            tempMap.put("灰色", 60);
            item.setColorMap(tempMap);
            item.setPrice(120.0f);
            item.setTotalCount(200);
            item.setTotalMoney(200*120);
            mStockItemList.add(item);
            item = new StockItem();
            item.setId("Zz-1353");
            item.setName("欣页");
            tempMap = new HashMap<>();
            tempMap.put("170", 60);
            tempMap.put("175", 50);
            tempMap.put("180", 30);
            tempMap.put("185", 30);
            tempMap.put("190", 30);
            item.setSizeMap(tempMap);
            tempMap = new HashMap<>();
            tempMap.put("黑色", 70);
            tempMap.put("红色", 70);
            tempMap.put("灰色", 60);
            item.setColorMap(tempMap);
            item.setPrice(120.0f);
            item.setTotalCount(200);
            item.setTotalMoney(200*120);
            mStockItemList.add(item);
        }
        return mStockItemList;
    }

    public void delStockItem(StockItem item) {
        mStockItemList.remove(item);
    }

    public void addStockItem(StockItem item) {
        mStockItemList.add(item);
    }

}
