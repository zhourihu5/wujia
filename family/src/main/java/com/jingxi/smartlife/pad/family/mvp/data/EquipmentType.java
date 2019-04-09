package com.jingxi.smartlife.pad.family.mvp.data;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-25
 * description ：
 */
public enum EquipmentType {

    //灯
    LINGHT(1),
    //窗帘
    CURTAIN(2),
    //红外线
    INFRAED_RAY(3),
    //烟雾感应
    SMOKE(4),
    //燃气
    FUEL_GAS(5),
    //空调
    AIR(6),
    //地暖
    LAND(7),
    //新风
    WIND(8),
    //门锁
    DOOR(9),
    //摄像头
    CARMERA(10);


    private int type;

    EquipmentType(int type) {
        this.type = type;
    }

    public int type() {
        return type;
    }
}
