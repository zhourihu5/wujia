package com.abctime.businesslib.data;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by xmren on 2018/5/18.
 */

public class UserEntity implements Parcelable, Serializable {

    /**
     * member_id : 1
     * stars : 42
     * open_id :
     * nickname :
     * phone : 18612619318
     * baby_name : 1
     * baby_sex : 2
     * baby_birthday : 123
     * head_img : https://qnfile.abctime.com/b_head_1_1504176921.jpeg
     * uuid : EF18508E-B7DD-4A27-BA86-80348DA0BCB3
     * grade : 1
     * type : 1
     * subcribe : 1
     * school_type : 2
     * token : 5a9fb7ebec548
     * level : 4
     * expire_time : 1547222399
     * auto_renew : 0
     * create_time :
     * notice : 01500278375
     * start_date : 2018-01-11
     * end_date : 2019-01-11
     * qrcode_time : 0
     * spare_date : 263
     * is_showPay : 1
     * buy_notice : 0
     * unlockNum : 3
     * book_nums : 21本
     * lookList : ["1293","154","64","1864","56","76","71","112","55","78","77","233","428","446","187","90","373","323","62","82","65","74","81","92","677","563","58","1278","1334","1325","1324","1335","1322","1311","1318","1295","1329","1328","1331","1347","1346","1340","1292","1337","1296","1316","1307","1323","1291","1344","1339","1341","1277","1320","1299","1276","1275","1315","1303","1317","1282","1283","1338","1342","1302","1285","1297","1273","1271","1348","1349","1309","1314","1288","1281","1304","1286","1306","1300","1326","1305","1287","1301","1343","1332","1272","1350","1298","1274","1333","1321","1327","1330","1312","1319","1267","1313","1310","1308","1290","1270","1289","1284","1279","1294","161","442"]
     * goldList : []
     * task_book : {"book_id":1774,"book_name":"Muscles","pic":"https://qnfile.abctime.com/book_1774.png"}
     */
    public int member_id;
    public int stars;
    public String open_id;
    public String nickname;
    public String phone;
    public String baby_name;
    public int baby_sex;
    public String baby_birthday;
    public String head_img;
    public String uuid;
    public int grade;
    public int type;
    public int subcribe;//是否是VIP
    public int school_type;
    public String token;
    public int level;
    public int expire_time;
    public int auto_renew;
    public int create_time;
    public String notice;
    public String start_date;
    public String end_date;
    public int qrcode_time;//等于0，未使用过扫码
    public int qrcode_start_time;
    public int qrcode_end_time;
    public int surplus_date;//扫码剩余时间
    public String buyLevel;
    public int spare_date;//VIP或试用期剩余时间
    public int is_showPay;
    public int buy_notice;
    public int unlockNum;
    public String book_nums;
    public TaskBookBean task_book;
    public List<String> lookList;
    public List<String> goldList;
    public String max_coupon_price;
    public List<LevelListBean> levelList;

    public static class LevelListBean implements Parcelable, Serializable {

        /**
         * cid : 0
         * start_time : 1528439949
         * end_time : 1562601599
         * spareDate : 250
         */

        public String cid;
        public int start_time;
        public int end_time;
        public int spareDate;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.cid);
            dest.writeInt(this.start_time);
            dest.writeInt(this.end_time);
            dest.writeInt(this.spareDate);
        }

        public LevelListBean() {
        }

        protected LevelListBean(Parcel in) {
            this.cid = in.readString();
            this.start_time = in.readInt();
            this.end_time = in.readInt();
            this.spareDate = in.readInt();
        }

        public static final Creator<LevelListBean> CREATOR = new Creator<LevelListBean>() {
            @Override
            public LevelListBean createFromParcel(Parcel source) {
                return new LevelListBean(source);
            }

            @Override
            public LevelListBean[] newArray(int size) {
                return new LevelListBean[size];
            }
        };
    }

    public static class TaskBookBean implements Parcelable, Serializable {
        /**
         * book_id : 1774
         * book_name : Muscles
         * pic : https://qnfile.abctime.com/book_1774.png
         */
        public int book_id;
        public String book_name;
        public String pic;

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(this.book_id);
            dest.writeString(this.book_name);
            dest.writeString(this.pic);
        }

        public TaskBookBean() {
        }

        protected TaskBookBean(Parcel in) {
            this.book_id = in.readInt();
            this.book_name = in.readString();
            this.pic = in.readString();
        }

        public static final Creator<TaskBookBean> CREATOR = new Creator<TaskBookBean>() {
            @Override
            public TaskBookBean createFromParcel(Parcel source) {
                return new TaskBookBean(source);
            }

            @Override
            public TaskBookBean[] newArray(int size) {
                return new TaskBookBean[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.member_id);
        dest.writeInt(this.stars);
        dest.writeString(this.open_id);
        dest.writeString(this.nickname);
        dest.writeString(this.phone);
        dest.writeString(this.baby_name);
        dest.writeInt(this.baby_sex);
        dest.writeString(this.baby_birthday);
        dest.writeString(this.head_img);
        dest.writeString(this.uuid);
        dest.writeInt(this.grade);
        dest.writeInt(this.type);
        dest.writeInt(this.subcribe);
        dest.writeInt(this.school_type);
        dest.writeString(this.token);
        dest.writeInt(this.level);
        dest.writeInt(this.expire_time);
        dest.writeInt(this.auto_renew);
        dest.writeInt(this.create_time);
        dest.writeString(this.notice);
        dest.writeString(this.start_date);
        dest.writeString(this.end_date);
        dest.writeInt(this.qrcode_time);
        dest.writeInt(this.qrcode_start_time);
        dest.writeInt(this.qrcode_end_time);
        dest.writeInt(this.surplus_date);
        dest.writeString(this.buyLevel);
        dest.writeInt(this.spare_date);
        dest.writeInt(this.is_showPay);
        dest.writeInt(this.buy_notice);
        dest.writeInt(this.unlockNum);
        dest.writeString(this.book_nums);
        dest.writeString(this.max_coupon_price);
        dest.writeParcelable(this.task_book, flags);
        dest.writeStringList(this.lookList);
        dest.writeStringList(this.goldList);
        dest.writeTypedList(this.levelList);
    }

    public UserEntity() {
    }

    protected UserEntity(Parcel in) {
        this.member_id = in.readInt();
        this.stars = in.readInt();
        this.open_id = in.readString();
        this.nickname = in.readString();
        this.phone = in.readString();
        this.baby_name = in.readString();
        this.baby_sex = in.readInt();
        this.baby_birthday = in.readString();
        this.head_img = in.readString();
        this.uuid = in.readString();
        this.grade = in.readInt();
        this.type = in.readInt();
        this.subcribe = in.readInt();
        this.school_type = in.readInt();
        this.token = in.readString();
        this.level = in.readInt();
        this.expire_time = in.readInt();
        this.auto_renew = in.readInt();
        this.create_time = in.readInt();
        this.notice = in.readString();
        this.start_date = in.readString();
        this.end_date = in.readString();
        this.qrcode_time = in.readInt();
        this.qrcode_start_time = in.readInt();
        this.qrcode_end_time = in.readInt();
        this.surplus_date = in.readInt();
        this.spare_date = in.readInt();
        this.buyLevel = in.readString();
        this.is_showPay = in.readInt();
        this.buy_notice = in.readInt();
        this.unlockNum = in.readInt();
        this.book_nums = in.readString();
        this.max_coupon_price = in.readString();
        this.task_book = in.readParcelable(TaskBookBean.class.getClassLoader());
        this.lookList = in.createStringArrayList();
        this.goldList = in.createStringArrayList();
        this.levelList = in.createTypedArrayList(LevelListBean.CREATOR);
    }

    public static final Parcelable.Creator<UserEntity> CREATOR = new Parcelable.Creator<UserEntity>() {
        @Override
        public UserEntity createFromParcel(Parcel source) {
            return new UserEntity(source);
        }

        @Override
        public UserEntity[] newArray(int size) {
            return new UserEntity[size];
        }
    };

    public boolean isGuestLogin() {
        return TextUtils.isEmpty(phone) || type == 0;
    }

    public boolean isVipUser() {
        return subcribe == 1;
    }

    public boolean isUsedExpire() {
        return spare_date <= 0;
    }

    public boolean isUsedQrcode() {
        return qrcode_start_time != 0;
    }

    public boolean isQrcodeExpire() {
        return surplus_date <= 0;
    }


    public boolean ifHasOverallVip() {
        if (levelList != null) {
            for (UserEntity.LevelListBean item :
                    levelList) {
                if (item.cid != null && TextUtils.equals(item.cid, "0")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Author: shenbingkai
     * CreateDate: 2018-12-05 11:00
     * Description: 判断是否已购买当前level
     */
    public boolean hasLevel(String cid) {
        if (levelList != null) {
            for (UserEntity.LevelListBean item :
                    levelList) {
                if (item.cid != null && (TextUtils.equals(item.cid, cid) || TextUtils.equals(item.cid, "0"))) {
                    return true;
                }
            }
        }
        return false;
    }

}
