package com.k.javine.warehousemanage.data;

import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * @文件描述 : 库存列表item
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-22
 */
public class StockItem extends BaseItem{
    private HashMap<String, Integer> colorMap;
    private HashMap<String, TreeMap<String, Integer>> colorSizeMap;
    private float totalMoney;
    private String sizeString;
    private String colorString;
    private String contentJson; // 用于保存到数据库: 该商品的颜色分布,尺寸分布

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

    public void changeColorItem(String key, Integer value) {
        if (colorMap == null) {
            colorMap = new HashMap<>();
        }
        colorMap.put(key, value);
    }

    public HashMap<String, TreeMap<String, Integer>> getColorSizeMap() {
        return colorSizeMap;
    }

    public void setColorSizeMap(HashMap<String, TreeMap<String, Integer>> colorSizeMap) {
        this.colorSizeMap = colorSizeMap;
        if (colorMap == null) {
            colorMap = new HashMap<>();
        } else {
            colorMap.clear();
        }
        totalCount = 0;
        for (Map.Entry<String, TreeMap<String, Integer>> entry : colorSizeMap.entrySet()) {
            int count = getMapValueCount(entry.getValue());
            colorMap.put(entry.getKey(), count);
            totalCount += count;
        }
        totalMoney = totalCount * price;
        contentJson = generateContentJsonStr();
    }

    private Integer getMapValueCount(Map<String, Integer> map) {
        Integer result = 0;
        for (Integer value : map.values()) {
            result += value;
        }
        return result;
    }

    private String generateContentJsonStr(){
        Gson gson = new Gson();
        return gson.toJson(colorSizeMap);
    }

    private HashMap<String, TreeMap<String, Integer>> getColorSizeMapFromJson(String jsonStr) {
        Gson gson = new Gson();
        Type type = new TypeToken<HashMap<String, TreeMap<String, Integer>>>(){}.getType();
        return gson.fromJson(jsonStr, type);
    }

    public String getColorString() {
        if (colorString == null) {
            StringBuilder colorBuilder = new StringBuilder();
            for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
                if (entry.getValue() > 0) {
                    colorBuilder.append(entry.getKey())
                            .append(" :  ")
                            .append(entry.getValue())
                            .append("\n\n");
                    colorBuilder.append(getSizeString(colorSizeMap.get(entry.getKey())));
                }
            }
            colorString = colorBuilder.toString();
        }
        return colorString;
    }

    private String getSizeString(Map<String, Integer> sizeMap) {
        StringBuilder sizeBuilder = new StringBuilder();
        for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
            if (entry.getValue() > 0) {
                sizeBuilder.append(entry.getKey())
                        .append(" : ")
                        .append(entry.getValue())
                        .append(", ");
            }
        }
        sizeBuilder.append("\n\n");
        return sizeBuilder.toString();
    }

    public String getContentJson() {
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        this.contentJson = contentJson;
        colorSizeMap = getColorSizeMapFromJson(contentJson);
    }
}