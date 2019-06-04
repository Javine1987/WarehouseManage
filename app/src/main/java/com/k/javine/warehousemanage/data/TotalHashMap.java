package com.k.javine.warehousemanage.data;

import java.util.HashMap;
import java.util.Map;

/**
 * @文件描述 : 可计算总数的HashMap, 计算最小单位的数量
 * @文件作者 : KuangYu
 * @创建时间 : 19-6-4
 */
public class TotalHashMap<K,V> extends HashMap<K, V>{
    private int total;

    public TotalHashMap(){
        super();
    }

    public TotalHashMap(Map<? extends K, ? extends V> m){
        super(m);
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
