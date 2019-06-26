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

    public static LoginDTO.DataBean getUser() throws Exception {
        Object object = SPHelper.readObject(AppContext.get(), Constants.SP_KEY_USER);
        if (object == null) {
            throw new Exception("please relogin to get user");
        }
        return (LoginDTO.DataBean) object;
    }

    public static final String getFamilyId() throws Exception {
        return getUser().getUserInfo().getFid();
    }

    public static String getDockKey() throws Exception {
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
        throw new Exception("no dock key,please relogin to get dockkey");
    }

    public static String getButtonKey() throws Exception {
        LoginDTO.DataBean user = getUser();
        if (null != user) {
            return user.getDevice().getButtonKey();
        }
        throw new Exception("no dock key,please relogin to get dockkey");
    }

    public static String getToken() {
        return (String) SPHelper.get(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, "");
    }

    public static void saveToken(String token) {
        SPHelper.put(AppContext.get(), Constants.COMMON_REQUEST_TOKEN, token);
    }
}
