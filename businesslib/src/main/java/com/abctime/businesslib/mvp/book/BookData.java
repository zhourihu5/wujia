package com.abctime.businesslib.mvp.book;


import android.content.Context;

import com.abctime.businesslib.data.DataManager;
import com.abctime.lib_common.data.SPHelper;

import java.io.Serializable;
import java.util.List;

/**
 * Created by KisenHuang on 2018/5/31.
 */

public class BookData implements Serializable {

    /**
     * id : 1278
     * cid : 3
     * cat_name : aa
     * sid : [5]
     * scene : ["Nature"]
     * book_name : Farm Animals
     * words_num : 16
     * pic : http://file.abctime.com/book_1278_small.jpg
     * zipfile : http://file.abctime.com/booknzf_03ea0e58b019f67fd2de1828da32cdc9.zip
     * orientation : 1
     * read_time : 3
     * guest_readable : 1
     * is_new : 0
     * words_card : 0
     */
    public String id;
    public String cid;
    public String cat_name;
    public String book_name;
    public String words_num;
    public String pic;
    public String zipfile;
    public String orientation;
    public String read_time;
    public String guest_readable;
    private String is_new;//新书标示
    public String words_card;
    public List<String> sid;
    public List<String> scene;
    public int posInLevel;
//    public boolean is_look;//用户是否已读
//    public boolean is_lock;//是否上锁

    public boolean isNew(Context context) {
        if ("1".equals(is_new)) {
            String key = DataManager.getMemberid() + id + "isNew";
            long nowTimeMills = System.currentTimeMillis();
            long firstNewTime = (long) SPHelper.get(context, key, 0L);
            if (firstNewTime == 0) {
                SPHelper.put(context, key, nowTimeMills);
                return true;
            }
            if (nowTimeMills - firstNewTime > 3 * 24 * 60 * 60 * 1000) {
                return false;
            }
            return true;
        }

        return false;
    }

}
