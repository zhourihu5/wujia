package com.wujia.intellect.terminal.mvp.home.data;

import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 * Author: created by shenbingkai on 2019/2/11 00 19
 * Email:  shenbingkai@gamil.com
 * Description:
 */
@Table("table_member")
public class HomeMeberBean {
    // 指定自增，每个对象需要有一个主键
    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private int id;
    public String phone;



    public HomeMeberBean(String phone) {
        this.phone = phone;
    }

    public HomeMeberBean() {
    }


}
