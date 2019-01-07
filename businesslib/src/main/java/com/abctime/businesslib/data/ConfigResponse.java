package com.abctime.businesslib.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by xmren on 2018/5/18.
 */

public class ConfigResponse implements Parcelable, Serializable {


    /**
     * level_version : 1
     * library_expiretime : 259200
     * library_all_book_index_list_version : 5
     * scan_status : 1
     * tv_status : 1
     * tv_list_status : 1
     * date : 20180521
     * update_status : 1
     * wx_qrcode_path : https://qnfile.abctime.com/wx_qrcode_2.jpg
     * wechatName : ABCtime002
     * wechatNotice : 亲爱的家长，添加老师微信号 “ABCtime002” 可以获得更多指导和活动信息哦
     * updateAndroid : {"remoteApiVersion":"1","updateType":1,"updateTitle":"新版本来袭！","updateTip":"  新功能全面上线，快来体验吧～～","appURL":"http://qnfile.abctime.com/ABCTime09885server.apk"}
     * androidLevelList : [{"level":"aa","name":"学龄前"},{"level":"LoadingActivity","name":"一年级"},{"level":"B","name":"二年级"},{"level":"C","name":"三年级"},{"level":"D","name":"四年级"},{"level":"E","name":"五年级"},{"level":"F","name":"六年级"}]
     * unlockNum : 3
     * experienceDays : 180
     * wx_smallProgram_path : https://qnfile.abctime.com/wxsmall_qrcode.jpg
     * wechatSmallProgramName : ABCtime美国小学图书馆
     * qrcode_date : 365
     * vip_notice : 开通VIP,享会员权益
     * guest_status : 1
     * guest_pay_status : 0
     */

    public int level_version;
    public int library_expiretime;
    public int library_all_book_index_list_version;
    public int scan_status;
    public int tv_status;
    public int tv_list_status;
    public String date;
    public int update_status;
    public String wx_qrcode_path;
    public String wechatName;
    public String wechatNotice;
    public String mobileRegular;
    public UpdateInfoBean updateAndroid;
    public int unlockNum;
    public int experienceDays;
    public String wx_smallProgram_path;
    public String wechatSmallProgramName;
    public int qrcode_date;
    public String vip_notice;
    public int guest_status;
    public int guest_pay_status;
    public List<LevelListBean> androidLevelList;
    public Map<String, String> level_grade;
    public ArrayList<String> gradeList;
    public int limit_date;
    public MainActive activity;

    public static class UpdateInfoBean implements Parcelable, Serializable {
        /**
         * remoteApiVersion : 1
         * updateType : 1
         * updateTitle : 新版本来袭！
         * updateTip :   新功能全面上线，快来体验吧～～
         * appURL : http://qnfile.abctime.com/ABCTime09885server.apk
         */

        public String remoteApiVersion;
        public int updateType;
        public String updateTitle;
        public String updateTip;
        public String appURL;
        public int _upSource = 0;       //前端扩展字段，默认应用内更新，其它跳市场


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.remoteApiVersion);
            dest.writeInt(this.updateType);
            dest.writeString(this.updateTitle);
            dest.writeString(this.updateTip);
            dest.writeString(this.appURL);
        }

        public UpdateInfoBean() {
        }

        protected UpdateInfoBean(Parcel in) {
            this.remoteApiVersion = in.readString();
            this.updateType = in.readInt();
            this.updateTitle = in.readString();
            this.updateTip = in.readString();
            this.appURL = in.readString();
        }

        public static final Creator<UpdateInfoBean> CREATOR = new Creator<UpdateInfoBean>() {
            @Override
            public UpdateInfoBean createFromParcel(Parcel source) {
                return new UpdateInfoBean(source);
            }

            @Override
            public UpdateInfoBean[] newArray(int size) {
                return new UpdateInfoBean[size];
            }
        };
    }

    public static class LevelListBean implements Parcelable, Serializable {
        /**
         * level : aa
         * name : 学龄前
         */

        public String level;
        public String name;


        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.level);
            dest.writeString(this.name);
        }

        public LevelListBean() {
        }

        protected LevelListBean(Parcel in) {
            this.level = in.readString();
            this.name = in.readString();
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

    public static class MainActive implements Parcelable, Serializable {
        public String url;
        public String icon;
        public int show;

        protected MainActive(Parcel in) {
            url = in.readString();
            icon = in.readString();
            show = in.readInt();
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(url);
            dest.writeString(icon);
            dest.writeInt(show);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        public static final Creator<MainActive> CREATOR = new Creator<MainActive>() {
            @Override
            public MainActive createFromParcel(Parcel in) {
                return new MainActive(in);
            }

            @Override
            public MainActive[] newArray(int size) {
                return new MainActive[size];
            }
        };
    }


    public boolean isHaveNewData() {
        String libraryVersion = DataManager.getLibraryVersion();
        if (this.library_all_book_index_list_version > Integer.valueOf(libraryVersion)) {
            return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.level_version);
        dest.writeInt(this.library_expiretime);
        dest.writeInt(this.library_all_book_index_list_version);
        dest.writeInt(this.scan_status);
        dest.writeInt(this.tv_status);
        dest.writeInt(this.tv_list_status);
        dest.writeString(this.date);
        dest.writeInt(this.update_status);
        dest.writeString(this.wx_qrcode_path);
        dest.writeString(this.wechatName);
        dest.writeString(this.wechatNotice);
        dest.writeString(this.mobileRegular);
        dest.writeParcelable(this.updateAndroid, flags);
        dest.writeInt(this.unlockNum);
        dest.writeInt(this.experienceDays);
        dest.writeString(this.wx_smallProgram_path);
        dest.writeString(this.wechatSmallProgramName);
        dest.writeInt(this.qrcode_date);
        dest.writeString(this.vip_notice);
        dest.writeInt(this.guest_status);
        dest.writeInt(this.guest_pay_status);
        dest.writeTypedList(this.androidLevelList);
        dest.writeInt(this.level_grade.size());
        for (Map.Entry<String, String> entry : this.level_grade.entrySet()) {
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeStringList(this.gradeList);
    }

    public ConfigResponse() {
    }

    protected ConfigResponse(Parcel in) {
        this.level_version = in.readInt();
        this.library_expiretime = in.readInt();
        this.library_all_book_index_list_version = in.readInt();
        this.scan_status = in.readInt();
        this.tv_status = in.readInt();
        this.tv_list_status = in.readInt();
        this.date = in.readString();
        this.update_status = in.readInt();
        this.wx_qrcode_path = in.readString();
        this.wechatName = in.readString();
        this.wechatNotice = in.readString();
        this.mobileRegular = in.readString();
        this.updateAndroid = in.readParcelable(UpdateInfoBean.class.getClassLoader());
        this.unlockNum = in.readInt();
        this.experienceDays = in.readInt();
        this.wx_smallProgram_path = in.readString();
        this.wechatSmallProgramName = in.readString();
        this.qrcode_date = in.readInt();
        this.vip_notice = in.readString();
        this.guest_status = in.readInt();
        this.guest_pay_status = in.readInt();
        this.androidLevelList = in.createTypedArrayList(LevelListBean.CREATOR);
        int level_gradeSize = in.readInt();
        this.level_grade = new HashMap<String, String>(level_gradeSize);
        for (int i = 0; i < level_gradeSize; i++) {
            String key = in.readString();
            String value = in.readString();
            this.level_grade.put(key, value);
        }
        this.gradeList = in.createStringArrayList();
    }

    public static final Creator<ConfigResponse> CREATOR = new Creator<ConfigResponse>() {
        @Override
        public ConfigResponse createFromParcel(Parcel source) {
            return new ConfigResponse(source);
        }

        @Override
        public ConfigResponse[] newArray(int size) {
            return new ConfigResponse[size];
        }
    };
}
