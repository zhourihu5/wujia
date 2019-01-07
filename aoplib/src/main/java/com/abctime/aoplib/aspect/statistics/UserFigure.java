package com.abctime.aoplib.aspect.statistics;

/**
 * author:Created by xmren on 2018/7/18.
 * email :renxiaomin@100tal.com
 */

public class UserFigure {
    public String accountID;
    public String accountType = "1";
    public String name;

    public UserFigure(String accountID, String name) {
        this.accountID = accountID;
        this.name = name;
    }

    public static UserFigure createUser(String accountID, String name) {
        return new UserFigure(accountID, name);
    }
}
