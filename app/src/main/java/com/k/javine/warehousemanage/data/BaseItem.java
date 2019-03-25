package com.k.javine.warehousemanage.data;

import android.text.TextUtils;

/**
 * @文件描述 : item基类
 * @文件作者 : KuangYu
 * @创建时间 : 19-3-25
 */
public class BaseItem {

    private String id;
    private String name;
    private int size;
    private String color;
    private String desc;
    private float price;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof BaseItem) {
            return TextUtils.equals(this.id, ((BaseItem) obj).id)
                    && TextUtils.equals(this.name, ((BaseItem) obj).name)
                    && this.size ==((BaseItem) obj).size;
        } else {
            return super.equals(obj);
        }
    }
}
