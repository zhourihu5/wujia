package com.abctime.lib.viewdelegate.title;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.abctime.lib.viewdelegate.IDelegate;
import com.abctime.lib.viewdelegate.menu.IRightMenuProvider;
import com.abctime.lib.viewdelegate.menu.RightMenuGroup;

/**
 * Created by KisenHuang on 2018/5/29.
 * 标题代理类
 */

public class TitleDelegate implements IDelegate<ITitle> {

    private ITitle mTitleView;
    private LinearLayout mRealContent;

    public TitleDelegate(ITitle view, View content) {
        setView(view);
        setupTitleLayout(content);
    }

    @Override
    public void setView(ITitle view) {
        mTitleView = view;
    }

    private void setupTitleLayout(View view) {
        mRealContent = new LinearLayout(view.getContext());
        mRealContent.setOrientation(LinearLayout.VERTICAL);
        View title = LayoutInflater.from(view.getContext()).inflate(mTitleView.getLayoutId(), mRealContent);
        mTitleView.inflateFinished(title);
        //动态添加右侧菜单布局
        if (title.getContext() instanceof IRightMenuProvider) {
            RightMenuGroup rightMenuGroup = ((IRightMenuProvider) title.getContext()).getRightMenuGroup();
            mTitleView.inflateRightMenu(rightMenuGroup.getMenuViewGroup());
        }
        mRealContent.addView(view);
    }

    public void setBackListener(View.OnClickListener listener) {
        mTitleView.setBackListener(listener);
    }

    public View getView() {
        return mRealContent;
    }

}
