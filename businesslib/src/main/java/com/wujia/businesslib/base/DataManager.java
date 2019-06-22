package com.wujia.businesslib.base;

import com.wujia.businesslib.Constants;
import com.wujia.businesslib.data.LoginDTO;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.SPHelper;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-21
 * description ：
 */
public class DataManager {

//    private static LoginDTO.DataBean user;

    public static LoginDTO.DataBean getUser() {
        return (LoginDTO.DataBean) SPHelper.readObject(AppContext.get(), Constants.SP_KEY_USER);
    }

//    public static String getFid() {
//        LoginDTO.DataBean user = getUser();
//        if (null != user) {
//            return user.getUserInfo().get;
//        }
//        return "";
//    }
    @Deprecated
    public static String getCommunityId() {//todo
//        LoginDTO.DataBean user = getUser();
//        if (null != user) {
//            return user.communityId;
//        }
        throw new RuntimeException("deprecated api");
//        return "";
    }
    @Deprecated
    public static String getOpenid() {
//        UserBean.User user = getUser();
//        if (null != user) {
//            return user.openId;
//        }
//        return "";
        throw new RuntimeException("deprecated api");
    }
    @Deprecated
    public static String getAccid() {
//        UserBean.User user = getUser();
//        if (null != user) {
//            return user.accid;
//        }
//        return "";
        throw new RuntimeException("deprecated api");
    }

    public static String getDockKey() {
        LoginDTO.DataBean user = getUser();
        if (null != user) {
            int count = user.getDevice().getDeviceKey().length();
            StringBuilder newDockkey = new StringBuilder();
            if (count < 16) {
                for (int i = 0; i < 16 - count; i++) {
                    newDockkey.append("0");
                }
            }
            return user.getDevice().getDeviceKey() + newDockkey.toString();
            // return "001901181CD10000";
        }
        return "";
    }

    public static String getButtonKey() {
        LoginDTO.DataBean user = getUser();
        if (null != user) {
            return user.getDevice().getButtonKey();
        }
        return "";
    }

    public static String getToken() {
        return (String) SPHelper.get(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, "");
    }

    public static void saveToken(String token) {
        SPHelper.put(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, token);
    }
}
