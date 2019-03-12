package com.wujia.intellect.terminal.mvp.login.data;

import com.wujia.businesslib.data.RootResponse;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-09
 * description ：
 */
public class TokenBean extends RootResponse {
    public String accessToken;
    public int expiresIn;
}
