package com.wujia.businesslib.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
@Table("table_app_list")
public class AppPackageBean {

    public AppPackageBean() {
    }
    public AppPackageBean(String name) {
        this.name = name;
    }

    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    public String name;
}
