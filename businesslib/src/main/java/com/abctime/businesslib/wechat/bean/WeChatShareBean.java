package com.abctime.businesslib.wechat.bean;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by four on 2018/7/4.
 */

public class WeChatShareBean implements Serializable{

    public final static int WECHAT_SHARE_SCENE_TIME_LINE = 0x33901;

    public final static int WECHAT_SHARE_SCENE_SESSION = 0x33902;

    public final static int WECHAT_SHARE_TYPE_WEB = 0x4062;

    public final static int WECHAT_SHARE_TYPE_IMAGE = 0x4063;

    public String imageUrl; // 优先级高

    public String title;

    public String description;

    public String webUrl;

    public int type = WECHAT_SHARE_TYPE_WEB; // 默认是 web

    public int scene = WeChatShareBean.WECHAT_SHARE_SCENE_TIME_LINE;
}
