package com.wujia.businesslib.arouter;

import android.content.Context;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Interceptor;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

import static com.wujia.businesslib.arouter.ArouterConstant.LOGIN_NEED;

/**
 * Created by xmren on 2018/5/29.
 */
@Interceptor(priority = 1)
public class LoginInterceptor implements IInterceptor {
    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        int extra = postcard.getExtra();
        switch (extra) {
            case LOGIN_NEED:
                callback.onInterrupt(new RuntimeException("need login"));
                RouteDelegate.login().jump();
                break;
        }
        callback.onContinue(postcard);
    }

    @Override
    public void init(Context context) {

    }
}
