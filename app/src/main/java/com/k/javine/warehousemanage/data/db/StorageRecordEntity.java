package com.k.javine.warehousemanage.data.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

/**
 * @文件描述 : 入库记录表 实体类
 * @文件作者 : KuangYu
 * @创建时间 : 19-7-30
 */
@Entity(
        nameInDb = "STORAGE_RECORD",
        active = true
)
public class StorageRecordEntity {
    @Id
    private int id; //记录id
    @Property(nameInDb = "TIME")
    private long time; //创建记录时间戳
    @Property(nameInDb = "MONEY")
    private int money; //入库金额
    @Property(nameInDb = "COUNT")
    private int count; //入库数量
    @Property(nameInDb = "DETAIL")
    private String detail; //入库详情:包括产品,颜色,尺寸,数量等
    @Property(nameInDb = "DESC")
    private String desc; //备注 - 单据仅备注可修改
/** Used to resolve relations */
@Generated(hash = 2040040024)
private transient DaoSession daoSession;
/** Used for active entity operations. */
@Generated(hash = 1102436589)
private transient StorageRecordEntityDao myDao;
@Generated(hash = 1924253333)
public StorageRecordEntity(int id, long time, int money, int count,
        String detail, String desc) {
    this.id = id;
    this.time = time;
    this.money = money;
    this.count = count;
    this.detail = detail;
    this.desc = desc;
}
@Generated(hash = 1679006222)
public StorageRecordEntity() {
}
public int getId() {
    return this.id;
}
public void setId(int id) {
    this.id = id;
}
public long getTime() {
    return this.time;
}
public void setTime(long time) {
    this.time = time;
}
public int getMoney() {
    return this.money;
}
public void setMoney(int money) {
    this.money = money;
}
public int getCount() {
    return this.count;
}
public void setCount(int count) {
    this.count = count;
}
public String getDetail() {
    return this.detail;
}
public void setDetail(String detail) {
    this.detail = detail;
}
public String getDesc() {
    return this.desc;
}
public void setDesc(String desc) {
    this.desc = desc;
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
@Generated(hash = 1726561213)
public void __setDaoSession(DaoSession daoSession) {
    this.daoSession = daoSession;
    myDao = daoSession != null ? daoSession.getStorageRecordEntityDao() : null;
}
}
