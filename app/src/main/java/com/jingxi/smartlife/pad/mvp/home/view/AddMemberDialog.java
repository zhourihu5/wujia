package com.jingxi.smartlife.pad.mvp.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.adapter.HomeInviteAdapter;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.wujia.businesslib.HookUtil;
import com.wujia.businesslib.dialog.CommDialog;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.view.VerticallDecoration;

import java.util.List;

/**
 * Author: created by shenbingkai on 2019/2/11 14 48
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class AddMemberDialog extends CommDialog {

    private OnInputDialogListener listener;

    private EditText inputEt;
    private RecyclerView rv;

    List<HomeUserInfoBean.DataBean.UserInfoListBean> datas;

    public AddMemberDialog(@NonNull Context context,List<HomeUserInfoBean.DataBean.UserInfoListBean> datas) {
        super(context, R.style.dialogStyle);
        this.datas=datas;
    }

    @Override
    protected void init(Context context) {
        inputEt = findViewById(R.id.dialog_input);
        rv = findViewById(R.id.rv_dialog_invite);

        rv.addItemDecoration(new VerticallDecoration(24));
        rv.setAdapter(new HomeInviteAdapter(context, datas));

        findViewById(R.id.btn_send_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    String phone = inputEt.getText().toString().trim();
                    if (TextUtils.isEmpty(phone))
                        return;
                    if (phone.length() != 11) {
                        ToastUtil.showShort(mContext, R.string.please_input_right_phone);
                        return;
                    }
                    dismiss();
                    listener.dialogSureClick(phone);
                }
            }
        });
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        HookUtil.fixInputMethodManagerLeak(getContext());
    }

    public String getHeadUrl() {
        int num = (int) (Math.random() * 6 + 1);
        String head = String.format("file:///android_asset/img_default_head_%d.png", num);
        return head;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_member_layout;
    }

    public AddMemberDialog setListener(OnInputDialogListener listener) {
        this.listener = listener;
        return this;
    }

}
