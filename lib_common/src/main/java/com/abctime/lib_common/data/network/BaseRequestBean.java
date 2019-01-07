package com.abctime.lib_common.data.network;

import com.abctime.lib_common.utils.AppContext;
import com.abctime.lib_common.utils.AppUtil;
import com.abctime.lib_common.utils.ChannelUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * retrofit 通过 post 对象方式 需要继承自该类
 */
public class BaseRequestBean {
    private String source;
    private String source_id;
    private String version;
    private String timestamp;
    private String uuid;
    private String sign;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSource_id() {
        return ChannelUtils.getCurrentChannelId(AppContext.get());
    }

    public void setSource_id(String source_id) {
        this.source_id = source_id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTimestamp() {
        return RequestParamsUtils.getSecondTimestamp(new Date()) + "";
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUuid() {
        return AppUtil.getDeviceInfo(AppContext.get());
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    //add by xmren 待测试 生成sign
    public String getSign() {
        Field[] fields = getClass().getDeclaredFields();
        Field[] declaredFields = getClass().getSuperclass().getDeclaredFields();
        Field[] fieldArr = concat(fields, declaredFields);
        Map<String, String> infoMap = new HashMap();
        for (int i = 0; i < fieldArr.length; i++) {
            String name = fieldArr[i].getName();
            if (!Constants.COMMON_REQUEST_SIGN.equals(name)) {
                infoMap.put(name, (String) getFieldValueByName(name, this));
            }
        }
        generateSignIno(infoMap);
        return infoMap.get(Constants.COMMON_REQUEST_SIGN);
    }

    public String getClient_type() {
        return Constants.ANDROID_CLIENT_TYPE;
    }


    /**
     * 根据属性名获取属性值
     */
    private Object getFieldValueByName(String fieldName, Object o) {
        try {
            String firstLetter = fieldName.substring(0, 1).toUpperCase();
            String getter = "get" + firstLetter + fieldName.substring(1);
            Method method = o.getClass().getMethod(getter, new Class[]{});
            Object value = method.invoke(o, new Object[]{});
            return value;
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> T[] concat(T[] first, T[] second) {
        T[] result = Arrays.copyOf(first, first.length + second.length);
        System.arraycopy(second, 0, result, first.length, second.length);
        return result;
    }

    private void generateSignIno(Map<String, String> combineParams) {
        List<String> keyList = new ArrayList<>();
        Iterator<String> iterator = combineParams.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            keyList.add(key);
        }
        Collections.sort(keyList);
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < keyList.size(); i++) {
            buffer.append(keyList.get(i)).append(combineParams.get(keyList.get(i)));
        }
        combineParams.put(Constants.COMMON_REQUEST_SIGN, RequestParamsUtils.getSignValue(buffer.toString()));
    }


}