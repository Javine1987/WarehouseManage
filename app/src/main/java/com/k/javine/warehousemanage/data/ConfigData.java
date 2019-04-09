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
    static {
        sColorList.add("黑色");
        sColorList.add("白色");
        sColorList.add("红色");
        sColorList.add("绿色");
        sColorList.add("黄色");
        sColorList.add("蓝色");

        sSizeList.add("70");
        sSizeList.add("75");
        sSizeList.add("80");
        sSizeList.add("85");
        sSizeList.add("90");
    }
    /**
     * 返回所以可供选择的颜色
     */
    public static List<String> getAllColorOptions() {
        // TODO: 19-4-4 加载用户添加的颜色选项
        return sColorList;
    }

    public static List<String> getAllSizeOptions() {
        return sSizeList;
    }
}
