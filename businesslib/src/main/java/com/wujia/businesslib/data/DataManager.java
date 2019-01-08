package com.wujia.businesslib.data;

import android.content.Context;
import android.text.TextUtils;

import com.wujia.lib_common.data.SPHelper;
import com.wujia.lib_common.utils.ACache;
import com.wujia.lib_common.utils.AESCrypt;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.AssetsUtil;
import com.wujia.lib_common.utils.GsonUtil;
import com.google.gson.JsonArray;

import java.io.Serializable;
import java.util.Map;
import java.util.Set;

import static java.lang.Boolean.FALSE;

/**
 * Created by xmren on 2018/5/21.
 */

public class DataManager {
    private static final String GUEST_LOGIN_STATUS = "guest_login_status";
    private static final String CONFIG_DATA = "config_data";
    private static final String GamePlayed = "Game_Played";
    private static final String LIBRARY_BERSION = "library_bersion";
    private static final String USERMODEL = "usermodel";
    private static final String MEMBERID = "memberid";
    private static final String TOKEN = "token";
    private static final String MOBILE_REGULAR = "mobile_regular";
    private static final java.lang.String LIBRARY_DATA = "library_data";
    private static final java.lang.String BookInfo_DATA = "bookinfo_data";
    private static final String USER_QRCODE_URL = "user_qrcode";
    private static final java.lang.String THEME_ID_MAP = "THEME_ID_MAP";
    private static final java.lang.String LEVEL_ID_MAP = "LEVEL_ID_MAP";
    private static final String SLIENT = "slient";
    private static final String PLAYED = "played";
    private static final String LASTUPDATEDIALOGTAG = "LASTUPDATEDIALOGTAG";
    private static final String REPORT = "REPORT";

    public static void setPlayed(int bookId, boolean ifPlayed) {
        SPHelper.put(AppContext.get(), GamePlayed + bookId, ifPlayed);
    }

    public static boolean getIfPlayed(int bookId) {
        return (boolean) SPHelper.get(AppContext.get(), GamePlayed + bookId, FALSE);
    }

    public static boolean isGuestLogin() {
        return (boolean) SPHelper.get(AppContext.get(), GUEST_LOGIN_STATUS, FALSE);
    }

    public static void setGuestLogin(boolean isGuestLogin) {
        SPHelper.put(AppContext.get(), GUEST_LOGIN_STATUS, isGuestLogin);
    }

    public static void saveConfigData(ConfigResponse configResponse) {
        ACache.get(AppContext.get()).put(CONFIG_DATA, configResponse);
        SPHelper.put(AppContext.get(), LIBRARY_BERSION, configResponse.library_all_book_index_list_version + "");
        SPHelper.put(AppContext.get(), MOBILE_REGULAR, configResponse.mobileRegular);
        SPHelper.put(AppContext.get(), USER_QRCODE_URL, configResponse.qrcode_date);

    }

    public static ConfigResponse getConfigData() {
        return getConfigData(AppContext.get());
    }

    public static ConfigResponse getConfigData(Context context) {
        ConfigResponse configResponse = (ConfigResponse) ACache.get(AppContext.get()).getParAsObject(CONFIG_DATA);
        if (configResponse == null) {
            String assetConfigData = getAssetConfigData(context);
            if (!TextUtils.isEmpty(assetConfigData)) {
                configResponse = GsonUtil.GsonToBean(assetConfigData, ConfigResponse.class);
            } else {//
                configResponse = new ConfigResponse();
            }
        }
        return configResponse;
    }


    public static String getMobileRegular() {
        return (String) SPHelper.get(AppContext.get(), MOBILE_REGULAR, "");
    }

    public static String getLibraryVersion() {
        return (String) SPHelper.get(AppContext.get(), LIBRARY_BERSION, "0");
    }

    public static String getQrcodeUrl() {
        return (String) SPHelper.get(AppContext.get(), USER_QRCODE_URL, "");
    }

    public static void saveUserInfo(UserEntity userEntity) {
        ACache.get(AppContext.get()).put(USERMODEL, userEntity);
        setGuestLogin(userEntity.isGuestLogin());
        SPHelper.put(AppContext.get(), MEMBERID, userEntity.member_id + "");
        SPHelper.put(AppContext.get(), "username", userEntity.nickname);
        SPHelper.put(AppContext.get(), TOKEN, userEntity.token);
    }

    public static void clearUserData() {

        saveUserInfo(new UserEntity());
        //SPHelper.remove(AppContext.get(),"localHeightCatId");
        //SPHelper.remove(AppContext.get(),"localCatId");
    }


    public static UserEntity getUserInfo() {
        return (UserEntity) ACache.get(AppContext.get()).getParAsObject(USERMODEL);
    }

    public static UserEntity getUserInfo(Context context) {
        return (UserEntity) ACache.get(context).getParAsObject(USERMODEL);
    }

    public static String getMemberid() {
        return (String) SPHelper.get(AppContext.get(), MEMBERID, "");
    }

    public static String getToken() {
        return (String) SPHelper.get(AppContext.get(), TOKEN, "");
    }

    public static void saveLibraryData(JsonArray results) {
        ACache.get(AppContext.get()).put(LIBRARY_DATA, results.toString());
    }

    public static String getLibraryData() {
        return ACache.get(AppContext.get()).getAsString(LIBRARY_DATA);
    }

    public static String getBookInfo() {
        return ACache.get(AppContext.get()).getAsString(BookInfo_DATA);
    }


    public static void saveReadReport(Set<String> playedDic) {
        ACache.get(AppContext.get()).put(REPORT + getMemberid(), (Serializable) playedDic);
    }

    public static Set<String> getReadReport() {
        return (Set<String>) ACache.get(AppContext.get()).getParAsObject(REPORT + getMemberid());
    }

    public static void saveThemeIdMap(Map<String, String> themeIdMap) {
        ACache.get(AppContext.get()).put(THEME_ID_MAP, (Serializable) themeIdMap);
    }

    public static void saveLevelIdMap(Map<String, String> levelIdMap) {
        ACache.get(AppContext.get()).put(LEVEL_ID_MAP, (Serializable) levelIdMap);
    }

    public static Map<String, String> getThemeIdMap() {
        return (Map<String, String>) ACache.get(AppContext.get()).getParAsObject(THEME_ID_MAP);

    }

    public static Map<String, String> getLevelIdMap() {
        return (Map<String, String>) ACache.get(AppContext.get()).getParAsObject(LEVEL_ID_MAP);

    }

    public static String getAssetConfigData(Context context) {
        String configJson = AssetsUtil.getFromAssets(context, "config.data");
        configJson = AESCrypt.decrypt(configJson);
        return configJson;
    }

    public static String getAssetLibraryData(Context context) {
        String libraryData = AssetsUtil.getFromAssets(context, "book.data");
        libraryData = AESCrypt.decrypt(libraryData);
        return libraryData;
    }

    public static boolean isSilent() {
        return (boolean) SPHelper.get(AppContext.get(), SLIENT, false);
    }

    public static boolean isSilent(Context context) {
        return (boolean) SPHelper.get(context, SLIENT, false);
    }

    public static void setSlient(boolean isSlient) {
        SPHelper.put(AppContext.get(), SLIENT, isSlient);
    }

    public static int getLastUpdateDialogTag() {
        return (int) SPHelper.get(AppContext.get(), LASTUPDATEDIALOGTAG, -1);
    }

    public static void setLastUpdateDialogTag(int lastUpdateDialogTag) {
        SPHelper.put(AppContext.get(), LASTUPDATEDIALOGTAG, lastUpdateDialogTag);
    }
}
