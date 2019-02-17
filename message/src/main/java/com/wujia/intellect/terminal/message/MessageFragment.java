package com.wujia.intellect.terminal.message;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.wujia.intellect.terminal.message.mvp.MessageHomeFragment;
import com.wujia.lib_common.base.BaseFragment;

/**
* author ：shenbingkai@163.com
* date ：2019-01-12 20:06
* description ：消息通知 home
*/
public class MessageFragment extends BaseFragment {

    public MessageFragment() {
    }

    public static MessageFragment newInstance() {
        MessageFragment fragment = new MessageFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_frame_layout;
    }

    @Override
    protected void initEventAndData() {

    }
    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用
        if (findChildFragment(MessageHomeFragment.class) == null) {
            loadRootFragment(R.id.fl_first_container, MessageHomeFragment.newInstance());
        }
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
}
