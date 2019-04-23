package com.wujia.businesslib.base;

import android.content.Context;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.data.UserBean;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.SPHelper;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class DataManager {

    private static UserBean.User user;

    public static UserBean.User getUser() {
        if (null == user) {
            user = (UserBean.User) SPHelper.readObject(AppContext.get(), Constants.SP_KEY_USER);
        }
        return user;
    }

    public static String getFamilyId() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.familyId;
//            return "001901181CD10000";
        }
        return "";
    }

    public static String getCommunityId() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.communityId;
        }
        return "";
    }

    public static String getOpenid() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.openId;
        }
        return "";
    }

    public static String getAccid() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.accid;
        }
        return "";
    }

    public static String getDockKey() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.dockkey;
        }
        return "";
    }

    public static String getButtonKey() {
        UserBean.User user = getUser();
        if (null != user) {
            return user.buttonkey;
        }
        return "";
    }

    public static String getToken() {
        return (String) SPHelper.get(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, "");
    }

    public static void saveToken(String token) {
        SPHelper.put(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, token);
    }


    //    public static String getFamilyId() {
//        UserBean.User user = getUser();
//        if (null != user) {
//            int count = user.familyId.length();
//            StringBuilder newFamilyId = new StringBuilder();
//            if (count < 16) {
//                for (int i = 0; i < 16 - count; i++) {
//                    newFamilyId.append("0");
//                }
//            }
//            return user.familyId + newFamilyId.toString();
////            return "001901181CD10000";
//        }
//        return "";
//    }
}
