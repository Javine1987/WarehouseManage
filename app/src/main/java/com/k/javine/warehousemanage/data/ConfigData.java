package com.k.javine.warehousemanage.data;

import java.util.ArrayList;
import java.util.List;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-4-4
 */
public class ConfigData {

    private static final List<String> sColorList = new ArrayList<>();
    private static final List<String> sSizeList = new ArrayList<>();

    /**
     * 返回所以可供选择的颜色
     */
    public static List<String> getAllColorOptions() {
        // TODO: 19-4-4 加载用户添加的颜色选项 - 1
        if (sColorList.size() == 0) {
            sColorList.add("黑色");
            sColorList.add("白色");
            sColorList.add("红色");
            sColorList.add("绿色");
            sColorList.add("黄色");
            sColorList.add("蓝色");
        }
        return sColorList;
    }

    public static List<String> getAllSizeOptions() {
        // TODO: 19-4-16 加载用户添加的颜色选项 如果用户编辑过则采用保存的用户修改过的选项 - 1
        if (sSizeList.size() == 0) {
            sSizeList.add("70");
            sSizeList.add("75");
            sSizeList.add("80");
            sSizeList.add("85");
            sSizeList.add("90");
        }
        return sSizeList;
    }

    public static void addColorOption(String color) {
        sColorList.add(color);
        saveOptions(AppConstant.S_KEY_COLOR_OPTIONS, sColorList);
    }

    public static void deleteColorOption(String color) {
        sColorList.remove(color);
        saveOptions(AppConstant.S_KEY_COLOR_OPTIONS, sColorList);
    }

    public static void addSizeOption(String size) {
        sSizeList.add(size);
        saveOptions(AppConstant.S_KEY_SIZE_OPTIONS, sSizeList);
    }

    public static void deleteSizeOption(String size) {
        sSizeList.remove(size);
        saveOptions(AppConstant.S_KEY_SIZE_OPTIONS, sSizeList);
    }

    private static void saveOptions(String key, List<String> options) {
        // TODO: 19-4-16 将options转成Json,保存到SharedPreferences中 - 1
    }
}
