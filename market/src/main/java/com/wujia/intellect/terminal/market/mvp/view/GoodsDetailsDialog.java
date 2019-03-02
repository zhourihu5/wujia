package com.wujia.intellect.terminal.market.mvp.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.wujia.businesslib.dialog.CommDialog;
import com.wujia.intellect.terminal.market.R;
import com.wujia.lib.imageloader.DensityUtil;
import com.wujia.lib.widget.util.ToastUtil;

/**
 * Author: created by shenbingkai on 2019/2/23 17 17
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class GoodsDetailsDialog extends CommDialog implements View.OnClickListener{

    public GoodsDetailsDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
    }

    @Override
    protected void init(Context context) {

        setSize();

        findViewById(R.id.btn1).setOnClickListener(this);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_goods_details;
    }

    private void setSize() {
        Window dialogWindow = getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        dialogWindow.setGravity(Gravity.CENTER);

        lp.width = DensityUtil.dp2px(mContext, 845 + getBaseWidthPx());
        lp.height = DensityUtil.dp2px(mContext, 343);

//        lp.y=176;
        dialogWindow.setAttributes(lp);
    }

    @Override
    public void onClick(View v) {
        ToastUtil.showShort(getContext(),"增加成功");
        dismiss();
    }
}
