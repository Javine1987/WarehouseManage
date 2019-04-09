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
    private List<Product> mProductList = new ArrayList<>();

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
            item.setName("欣页1153");

            HashMap<String, TreeMap<String, Integer>> colorSizeMap = new HashMap<>();

            TreeMap<String, Integer> tempMap = new TreeMap<>();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setPrice(120.0f);
            mStockItemList.add(item);

            item = new StockItem();
            item.setId("XY-1353");
            item.setName("欣页1353");
            tempMap = new TreeMap<>();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setPrice(120.0f);

            mStockItemList.add(item);
            item = new StockItem();
            item.setId("ZY-1358");
            item.setName("欣页1358");
            tempMap = new TreeMap<>();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setPrice(120.0f);
            mStockItemList.add(item);
            item = new StockItem();
            item.setId("Zz-110");
            item.setName("欣页110");
            tempMap = new TreeMap<>();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("黑色", tempMap);

            tempMap.clear();
            tempMap.put("170", 15);
            tempMap.put("175", 15);
            tempMap.put("180", 10);
            tempMap.put("185", 10);
            tempMap.put("190", 10);
            colorSizeMap.put("红色", tempMap);

            item.setColorSizeMap(colorSizeMap);
            item.setPrice(120.0f);
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

    public List<Product> getProducts() {
        if (mProductList.isEmpty()) {
            Product product = new Product();
            product.setId("XY-1153");
            product.setName("欣页");
            product.setPrice(120.0f);
            product.setSizes("70,75,80,85,90");
            product.setColors("黑色,红色,白色,蓝色");
        }
        return mProductList;
    }

    public void delProduct(Product product) {
        mProductList.remove(product);
    }

    public void addProduct(Product product) {
        mProductList.add(product);
        if (mStockItemList != null) {
            mStockItemList.add(convertProduct2StockItem(product));
        }
    }

    public StockItem convertProduct2StockItem(Product product) {
        StockItem stockItem = new StockItem();
        stockItem.setName(product.getName());
        stockItem.setPrice(product.getPrice());
        stockItem.setId(product.getId());
        stockItem.setTotalCount(0);
        stockItem.setColorSizeOptions(product.getColors(), product.getSizes());
        return stockItem;
    }

}
