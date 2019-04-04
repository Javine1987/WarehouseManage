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
    static {
        sColorList.add("黑色");
        sColorList.add("白色");
        sColorList.add("红色");
        sColorList.add("绿色");
        sColorList.add("黄色");
        sColorList.add("蓝色");
    }
    /**
     * 返回所以可供选择的颜色
     */
    public static List<String> getAllColorOptions() {
        // TODO: 19-4-4 加载用户添加的颜色选项
        return sColorList;
    }
}
