package com.abctime.businesslib.wechat;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;

/**
 * Created by four on 2018/7/4.
 */

public class WechatUtil {

    public final static String WECHAT_TYPE_KEY = "WECHAT_TYPE_KEY";

    public final static int WECHAT_TYPE_KEY_PAY = -3204; // 支付pull

    public final static int WECHAT_TYPE_KEY_SHARE = -3205; // 分享

    public final static int WECHAT_TYPE_KEY_LOG = -3333; // 上传日志

    public final static int WECHAT_TYPE_KEY_MINPEOGRAM = -3206; // 小程序

    // 实体内容 bean
    public final static String WECHAT_KEY_CONTENT = "WECHAT_KEY_CONTENT";

    public final static int WECHAT_RESULT_CODE_SUCCESSED = 0x88904;

    public final static int WECHAT_RESULT_CODE_CANCELED = 0x88905;

    public final static int WECHAT_RESULT_CODE_FAILED = 0x88906;

    public final static int WECHAT_RESULT_CODE_NONE = 0x3306;

    public static byte[] inputStreamToByte(String path) {
        try {
            FileInputStream fis = new FileInputStream(path);
            ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
            int ch;
            while ((ch = fis.read()) != -1) {
                bytestream.write(ch);
            }
            byte imgdata[] = bytestream.toByteArray();
            bytestream.close();
            return imgdata;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
