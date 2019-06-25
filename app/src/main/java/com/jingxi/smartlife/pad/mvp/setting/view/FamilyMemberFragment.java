package com.jingxi.smartlife.pad.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean;
import com.jingxi.smartlife.pad.mvp.setting.adapter.SetMemberAdapter;
import com.jingxi.smartlife.pad.mvp.setting.model.FamilyMemberModel;
import com.wujia.businesslib.TitleFragment;
import com.wujia.businesslib.base.DataManager;
import com.wujia.businesslib.data.ApiResponse;
import com.wujia.businesslib.dialog.InputDialog;
import com.wujia.businesslib.event.EventBusUtil;
import com.wujia.businesslib.event.EventMemberChange;
import com.wujia.businesslib.listener.OnInputDialogListener;
import com.wujia.businesslib.util.LoginUtil;
import com.wujia.lib_common.base.view.VerticallDecoration;
import com.wujia.lib_common.data.network.SimpleRequestSubscriber;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.utils.LogUtil;

import java.util.ArrayList;
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

    FamilyMemberModel familyMemberModel;

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        rvMember.addItemDecoration(new VerticallDecoration(1));

        familyMemberModel=new FamilyMemberModel();
        String familyId= null;
        try {
            familyId = DataManager.getFamilyId();
        } catch (Exception e) {
            LogUtil.t("get familyId failed",e);
            LoginUtil.toLoginActivity();
            return;
        }

        addSubscribe(familyMemberModel.getFamilyMemberList(familyId).subscribeWith(new SimpleRequestSubscriber<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>> response) {
                super.onResponse(response);
                mAdapter = new SetMemberAdapter(mContext, response.data);
                rvMember.setAdapter(mAdapter);
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));



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
    public void dialogSureClick(final String input) {
        String familyId= null;
        try {
            familyId = DataManager.getFamilyId();
        } catch (Exception e) {
            LoginUtil.toLoginActivity();
            LogUtil.t("get familyId failed",e);
            return;
        }
        addSubscribe(familyMemberModel.addFamilyMember(input,familyId).subscribeWith(new SimpleRequestSubscriber<ApiResponse<String>>(this, new SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            @Override
            public void onResponse(ApiResponse<String> response) {
                super.onResponse(response);
                HomeUserInfoBean.DataBean.UserInfoListBean userInfoListBean=new HomeUserInfoBean.DataBean.UserInfoListBean();
                userInfoListBean.setUserName(input);
                if(mAdapter==null){
                    mAdapter= new SetMemberAdapter(mContext, new ArrayList<HomeUserInfoBean.DataBean.UserInfoListBean>());
                    rvMember.setAdapter(mAdapter);
                }
                mAdapter.getDatas().add(userInfoListBean);
                mAdapter.notifyDataSetChanged();

                EventBusUtil.post(new EventMemberChange());
            }

            @Override
            public void onFailed(ApiException apiException) {
                super.onFailed(apiException);
            }
        }));


    }

    public String getHeadUrl() {
        int num = (int) (Math.random() * 6 + 1);
        String head = String.format("file:///android_asset/img_default_head_%d.png", num);
        return head;
    }


}
