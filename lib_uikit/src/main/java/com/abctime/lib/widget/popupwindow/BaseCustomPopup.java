package com.abctime.lib.widget.popupwindow;

import android.content.Context;
import android.view.View;

/**
 * @projectname abctime android app
 * @classname：com.abctime.lib.widget.popupwindow
 * @classdescribe
 * @author xmren
 * @email:renxiaomin@100tal.com
 * @time 2018/6/2 上午11:16
 * @change
 * @changtime
 */

public abstract class BaseCustomPopup extends AbcPopup {
    private static final String TAG = "BaseCustomPopup";

    protected BaseCustomPopup(Context context) {
        super(context);
    }

    @Override
    public void onPopupWindowCreated() {
        super.onPopupWindowCreated();
        //执行设置PopupWindow属性也可以通过Builder中设置
        //setContentView(x,x,x);
        //...
        initAttributes();
    }

    @Override
    public void onPopupWindowViewCreated(View contentView) {
        initViews(contentView);
    }

    @Override
    public void onPopupWindowDismiss() {

    }

    /**
     * 可以在此方法中设置PopupWindow需要的属性
     */
    protected abstract void initAttributes();

    /**
     * 初始化view {@see getView()}
     *
     * @param view
     */
    protected abstract void initViews(View view);


}
