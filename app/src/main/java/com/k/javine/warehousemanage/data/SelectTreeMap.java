package com.k.javine.warehousemanage.data;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * @文件描述 : 包含一个选择Map的TreeMap
 * @文件作者 : KuangYu
 * @创建时间 : 19-6-13
 */
public class SelectTreeMap<K, V> extends TreeMap<K, V> {
    //每一个key对应的被选择的数量
    private HashMap<K, Integer> mSelectedMap;

    public SelectTreeMap(){
        super();
        mSelectedMap = new HashMap<>();
    }

    public SelectTreeMap(Map<? extends K, ? extends V> m) {
        super(m);
        mSelectedMap = new HashMap<>();
    }

    public void selectKey(K key, Integer value) {
        mSelectedMap.put(key, value);
    }

    public Integer getSelectValue(K key) {
        return mSelectedMap.get(key);
    }

    public void resetSelect() {
        mSelectedMap.clear();
    }

    public HashMap<K, Integer> getSelectedMap() {
        return mSelectedMap;
    }

    public int getSelectCount() {
        int count = 0;
        for (Integer i : mSelectedMap.values()){
            count += i;
        }
        return count;
    }
}
