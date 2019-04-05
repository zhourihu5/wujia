package com.wujia.intellect.terminal.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.wujia.businesslib.DataBaseUtil;
import com.wujia.businesslib.TitleFragment;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMemberChange;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.home.data.HomeMeberBean;
import com.wujia.intellect.terminal.mvp.setting.adapter.SetMemberAdapter;
import com.wujia.businesslib.dialog.InputDialog;
import com.wujia.lib_common.base.view.VerticallDecoration;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
public class FamilyMemberFragment extends TitleFragment implements OnInputDialogListener {

    @BindView(R.id.btn_add_member)
    TextView btnAddMember;
    @BindView(R.id.rv_member)
    RecyclerView rvMember;
    List<HomeMeberBean> mems;
    SetMemberAdapter mAdapter;

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

        mems = DataBaseUtil.query(HomeMeberBean.class);
        rvMember.addItemDecoration(new VerticallDecoration(1));
        mAdapter = new SetMemberAdapter(mContext, mems);
        rvMember.setAdapter(mAdapter);
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
        DataBaseUtil.insert(new HomeMeberBean(input));
        mems.clear();
        mems.addAll(DataBaseUtil.query(HomeMeberBean.class));
        mAdapter.notifyDataSetChanged();
        EventBusUtil.post(new EventMemberChange());
    }
}
