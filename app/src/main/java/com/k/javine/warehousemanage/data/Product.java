package com.k.javine.warehousemanage.data;

import android.text.TextUtils;

/**
 * 商品分类
 */
public class Product {
    private String id;    // 商品id
    private String name;  // 商品名称
    private String sizes; // 商品包括的尺寸种类 例如: s,m,l,xl
    private String colors; // 商品包括的颜色种类 例如: 黑色,蓝色,红色,白色
    private String desc;  // 商品描述
    private float price;  // 商品单价
    private long timeStampId; //商品唯一id 时间戳

    public long getTimeStampId() {
        return timeStampId;
    }

    public void setTimeStampId(long timeStampId) {
        this.timeStampId = timeStampId;
    }

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

    public String getSizes() {
        return sizes;
    }

    public void setSizes(String sizes) {
        this.sizes = sizes;
    }

    public String getColors() {
        return colors;
    }

    public void setColors(String colors) {
        this.colors = colors;
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
        if (obj instanceof Product) {
            return timeStampId == ((Product) obj).timeStampId;
        } else {
            return super.equals(obj);
        }
    }
}
