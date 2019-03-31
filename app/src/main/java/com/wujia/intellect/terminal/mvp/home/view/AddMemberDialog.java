package com.wujia.intellect.terminal.mvp.home.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.adapter.HomeInviteAdapter;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.businesslib.dialog.CommDialog;
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

    public AddMemberDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
    }

    @Override
    protected void init(Context context) {
        inputEt = findViewById(R.id.dialog_input);
        rv = findViewById(R.id.rv_dialog_invite);

        List<HomeMeberBean> mems = DataBaseUtil.query(HomeMeberBean.class);
        rv.addItemDecoration(new VerticallDecoration(24));
        rv.setAdapter(new HomeInviteAdapter(context, mems));

        findViewById(R.id.btn_send_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    String phone = inputEt.getText().toString();
                    if (TextUtils.isEmpty(phone))
                        return;
                    dismiss();
                    DataBaseUtil.insert(new HomeMeberBean(phone));
                    listener.dialogSureClick(inputEt.getText().toString());
                }
            }
        });
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
