package com.k.javine.warehousemanage.data;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.k.javine.warehousemanage.utils.CommonUtils;

import java.io.Serializable;
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
public class StockItem extends Product implements Serializable{
    // TODO: 2019-05-14 应该定义一些对象来管理颜色和尺码，用纯数据结构操作起来不方便
    private HashMap<String, Integer> colorMap;
    private TotalHashMap<String, SelectTreeMap<String, Integer>> colorSizeMap;
    private int totalCount;
    private float totalMoney;
    private SpannableStringBuilder colorString;
    private String contentJson; // 用于保存到数据库: 该商品的颜色分布,尺寸分布

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }


    public int getTotalCount() {
        return totalCount;
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalSelectCount() {
        int totalSelected = 0;
        for (Map.Entry<String, SelectTreeMap<String, Integer>> entry : colorSizeMap.entrySet()) {
            if (entry != null && entry.getValue() != null) {
                totalSelected += entry.getValue().getSelectCount();
            }
        }
        return totalSelected;
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

    public TotalHashMap<String, SelectTreeMap<String, Integer>> getColorSizeMap() {
        return colorSizeMap;
    }

    public void setColorSizeMap(TotalHashMap<String, SelectTreeMap<String, Integer>> colorSizeMap, boolean isGenerateJson) {
        this.colorSizeMap = colorSizeMap;
        if (colorMap == null) {
            colorMap = new HashMap<>();
        } else {
            colorMap.clear();
        }
        totalCount = 0;
        for (Map.Entry<String, SelectTreeMap<String, Integer>> entry : colorSizeMap.entrySet()) {
            int count = getMapValueCount(entry.getValue());
            colorMap.put(entry.getKey(), count);
            totalCount += count;
        }
        this.colorSizeMap.setTotal(totalCount);
        totalMoney = totalCount * getPrice();
        if (isGenerateJson) {
            contentJson = generateContentJsonStr();
        }
    }

    public void resetSelectedState() {
        if (colorSizeMap != null) {
            for (Map.Entry<String, SelectTreeMap<String, Integer>> entry : colorSizeMap.entrySet()){
                if (entry.getValue() != null) {
                    entry.getValue().resetSelect();
                }
            }
        }
    }

    public void setColorSizeOptions(String colorOptions, String sizeOptions) {
        if (colorOptions == null || sizeOptions == null) {
            return;
        }

        colorMap = new HashMap<>();
        colorSizeMap = new TotalHashMap<>();
        TreeMap<String, Integer> sizeMap = new TreeMap<>();
        String[] sizeArray = sizeOptions.split(",");
        for (String size : sizeArray) {
            sizeMap.put(size, 0);
        }

        String[] colorArray = colorOptions.split(",");
        for (String color : colorArray) {
            colorMap.put(color, 0);
            colorSizeMap.put(color, new SelectTreeMap<String, Integer>(sizeMap));
        }
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

    private TotalHashMap<String, SelectTreeMap<String, Integer>> getColorSizeMapFromJson(String jsonStr) {
        Gson gson = new Gson();
        Type type = new TypeToken<TotalHashMap<String, SelectTreeMap<String, Integer>>>(){}.getType();
        return gson.fromJson(jsonStr, type);
    }

    public SpannableStringBuilder getColorString(Context context) {
        if (colorString == null) {
            SpannableStringBuilder colorBuilder = new SpannableStringBuilder();
            for (Map.Entry<String, Integer> entry : colorMap.entrySet()) {
                int startOffset = colorBuilder.length();
                colorBuilder.append("  ").append(entry.getKey())
                        .append(" :  ")
                        .append(String.valueOf(entry.getValue()))
                        .append("\n");
                colorBuilder.setSpan(new ForegroundColorSpan(Color.BLACK), startOffset, colorBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                colorBuilder.setSpan(new AbsoluteSizeSpan(CommonUtils.Dp2Px(context, 16)), startOffset, colorBuilder.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                colorBuilder.append(getSizeString(colorSizeMap.get(entry.getKey())));
            }
            colorString = colorBuilder;
        }
        return colorString;
    }

    private SpannableStringBuilder getSizeString(Map<String, Integer> sizeMap) {
        SpannableStringBuilder sizeBuilder = new SpannableStringBuilder();
        int i = 1;
        for (Map.Entry<String, Integer> entry : sizeMap.entrySet()) {
            int startOffset = sizeBuilder.length();
            sizeBuilder.append("    ").append(entry.getKey()).append(" : ");
            int endOffset = sizeBuilder.length();

            sizeBuilder.append(String.valueOf(entry.getValue()));

            if (i % 3 != 0) {
                sizeBuilder.append("    ");
            } else {
                sizeBuilder.append("\n");
            }
            i++;
            sizeBuilder.setSpan(new ForegroundColorSpan(0xCC000000), startOffset, endOffset, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        sizeBuilder.append("\n\n");
        return sizeBuilder;
    }

    public String getContentJson() {
        if (contentJson == null && colorSizeMap != null) {
            contentJson = generateContentJsonStr();
        }
        return contentJson;
    }

    public void setContentJson(String contentJson) {
        if (contentJson == null) {
            setColorSizeOptions(getColors(), getSizes());
        } else {
            this.contentJson = contentJson;
            colorSizeMap = getColorSizeMapFromJson(contentJson);
            setColorSizeMap(colorSizeMap, false);
        }
    }
}
