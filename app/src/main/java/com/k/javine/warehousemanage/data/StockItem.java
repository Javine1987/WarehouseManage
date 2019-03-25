package com.k.javine.warehousemanage.data;

import android.content.Intent;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @文件描述 : 库存列表item
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-22
 */
public class StockItem extends BaseItem{
    private HashMap<String, Integer> colorMap;
    private HashMap<String, Integer> sizeMap;
    private int totalCount;
    private float totalMoney;
    private String sizeString;
    private String colorString;

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    public HashMap<String, Integer> getColorMap() {
        return colorMap;
    }

    public void setColorMap(HashMap<String, Integer> colorMap) {
        this.colorMap = colorMap;
    }

    public HashMap<String, Integer> getSizeMap() {
        return sizeMap;
    }

    public void setSizeMap(HashMap<String, Integer> sizeMap) {
        this.sizeMap = sizeMap;
    }

    public void changeSizeItem(String size, Integer value) {
        if (sizeMap == null) {
            sizeMap = new HashMap<>();
        }
        sizeMap.put(size, value);
    }

    public void changeColorItem(String key, Integer value) {
        if (colorMap == null) {
            colorMap = new HashMap<>();
        }
        colorMap.put(key, value);
    }

    public String getSizeString() {
        if (sizeString == null) {
            StringBuilder sizeBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
                if (entry.getValue() > 0) {
                    sizeBuilder.append(entry.getKey())
                            .append(" :  ")
                            .append(entry.getValue())
                            .append("\n");
                }
            }
            sizeString = sizeBuilder.toString();
        }
        return sizeString;
    }

    public String getColorString() {
        if (colorString == null) {
            StringBuilder sizeBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
                if (entry.getValue() > 0) {
                    sizeBuilder.append(entry.getKey())
                            .append(" :  ")
                            .append(entry.getValue())
                            .append("\n");
                }
            }
            colorString = sizeBuilder.toString();
        }
        return colorString;
    }
}
