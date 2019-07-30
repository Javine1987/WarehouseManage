package com.k.javine.warehousemanage.data.db;

import android.nfc.tech.NfcA;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.OrderBy;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @文件描述 : 库存表实体类
 * @文件作者 : KuangYu
 * @创建时间 : 19-7-30
 */
@Entity(
        nameInDb = "STOCK_PRODUCT",
        active = true
)
public class StockEntity {
    @Id
    private String id;    // 商品id
    @Property(nameInDb = "TIME_STAMP")
    private long timeStampId; //商品创建时间戳
    @Property(nameInDb = "NAME")
    private String name;  // 商品名称
    @Property(nameInDb = "SIZE")
    private String sizes; // 商品包括的尺寸种类 例如: s,m,l,xl
    @Property(nameInDb = "COLOR")
    private String colors; // 商品包括的颜色种类 例如: 黑色,蓝色,红色,白色
    @Property(nameInDb = "DETAIL")
    private String detail;  // 库存详情:颜色和尺寸的分布情况
    @Property(nameInDb = "PRICE")
    private float price;  // 商品单价
    @Property(nameInDb = "COUNT")
    private int count;
/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;
/** Used for active entity operations. */
@Generated(hash = 186474315)
private transient StockEntityDao myDao;
@Generated(hash = 2124436249)
public StockEntity(String id, long timeStampId, String name, String sizes,
        String colors, String detail, float price, int count) {
    this.id = id;
    this.timeStampId = timeStampId;
    this.name = name;
    this.sizes = sizes;
    this.colors = colors;
    this.detail = detail;
    this.price = price;
    this.count = count;
}
@Generated(hash = 1239721677)
public StockEntity() {
}
public String getId() {
    return this.id;
}
public void setId(String id) {
    this.id = id;
}
public long getTimeStampId() {
    return this.timeStampId;
}
public void setTimeStampId(long timeStampId) {
    this.timeStampId = timeStampId;
}
public String getName() {
    return this.name;
}
public void setName(String name) {
    this.name = name;
}
public String getSizes() {
    return this.sizes;
}
public void setSizes(String sizes) {
    this.sizes = sizes;
}
public String getColors() {
    return this.colors;
}
public void setColors(String colors) {
    this.colors = colors;
}
public String getDetail() {
    return this.detail;
}
public void setDetail(String detail) {
    this.detail = detail;
}
public float getPrice() {
    return this.price;
}
public void setPrice(float price) {
    this.price = price;
}
public int getCount() {
    return this.count;
}
public void setCount(int count) {
    this.count = count;
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 128553479)
public void delete() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.delete(this);
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 1942392019)
public void refresh() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.refresh(this);
}
/**
 * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
 * Entity must attached to an entity context.
 */
@Generated(hash = 713229351)
public void update() {
    if (myDao == null) {
        throw new DaoException("Entity is detached from DAO context");
    }
    myDao.update(this);
}
/** called by internal mechanisms, do not call yourself. */
@Generated(hash = 1530664372)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getStockEntityDao() : null;
}
}
