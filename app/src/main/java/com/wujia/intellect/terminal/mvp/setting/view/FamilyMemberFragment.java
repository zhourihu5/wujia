package com.wujia.intellect.terminal.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.setting.adapter.SetMemberAdapter;
import com.wujia.intellect.terminal.mvp.setting.data.FamilyMeberBean;
import com.wujia.lib.widget.InputDialog;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.base.view.GridDecoration;
import com.wujia.lib_common.base.view.VerticallDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：务业服务 home
 */
public class FamilyMemberFragment extends TitleFragment implements InputDialog.OnInputDialogListener {

    @BindView(R.id.btn_add_member)
    TextView btnAddMember;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;

    public FamilyMemberFragment() {
    }

    public static FamilyMemberFragment newInstance() {
        FamilyMemberFragment fragment = new FamilyMemberFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_member;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
        showBack();
    }

    @Override
    public int getTitle() {
        return R.string.set_family_member;
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        rvMember.addItemDecoration(new VerticallDecoration(1));
        List<FamilyMeberBean> datas = new ArrayList<>();
        datas.add(new FamilyMeberBean());
        datas.add(new FamilyMeberBean());
        datas.add(new FamilyMeberBean());
        rvMember.setAdapter(new SetMemberAdapter(mContext, datas));
    }

    @Override
    public void onSupportVisible() {
        super.onSupportVisible();
        // 当对用户可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @Override
    public void onSupportInvisible() {
        super.onSupportInvisible();
        // 当对用户不可见时 回调
        // 不管是 父Fragment还是子Fragment 都有效！

    }

    @OnClick(R.id.btn_add_member)
    public void onViewClicked() {
        InputDialog inputDialog = new InputDialog.Builder().title(getString(R.string.add_family_member))
                .hint(getString(R.string.please_input_member_phone))
                .confirm(getString(R.string.invite))
                .listener(this)
                .build(mContext);

        inputDialog.show();
    }

    @Override
    public void dialogSureClick(String input) {
        showToast(input);
    }

    @Override
    public void dialogCancelClick() {

    }
}
