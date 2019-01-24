package com.k.javine.warehousemanage.data;

/**
 * @文件描述 : 库存列表item
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-22
 */
public class StockItem extends Product{
    private int totalCount;
    private float totalMoney;

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
}
