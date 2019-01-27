package com.wujia.intellect.terminal.mvp.setting.adapter;

import android.content.Context;

import com.wujia.intellect.terminal.R;
import com.wujia.intellect.terminal.mvp.setting.data.FamilyMeberBean;
import com.wujia.lib_common.base.baseadapter.CommonAdapter;
import com.wujia.lib_common.base.baseadapter.base.ViewHolder;

import java.util.List;

/**
 * Author: created by shenbingkai on 2018/12/11 11 24
 * Email:  shenbingkai@gamil.com
 * Description:
 */
public class SetMemberAdapter extends CommonAdapter<FamilyMeberBean> {
    public SetMemberAdapter(Context context, List<FamilyMeberBean> datas) {
        super(context, R.layout.item_family_member_layout, datas);
    }

    @Override
    protected void convert(ViewHolder holder, FamilyMeberBean item, int pos) {

//        holder.setText(R.id.scene_in_mode_tv,item.title);

    }
}
