package com.wujia.intellect.terminal.family;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.wujia.intellect.terminal.family.mvp.adapter.EquipmentAdapter;
import com.wujia.intellect.terminal.family.mvp.adapter.EquipmentExpandAdapter;
import com.wujia.intellect.terminal.family.mvp.adapter.ModeAdapter;
import com.wujia.intellect.terminal.family.mvp.data.EquipmentBean;
import com.wujia.intellect.terminal.family.mvp.data.ModeBean;
import com.wujia.intellect.terminal.family.mvp.view.GridDecoration;
import com.wujia.intellect.terminal.family.mvp.view.HorizontalDecoration;
import com.wujia.lib.widget.WJPopWindow;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：服务市场 home
 */
public class AllFragment extends BaseFragment {

    private RecyclerView rvUsually, rvAll, rvMode;
    private EquipmentAdapter useAdapter, allAdapter;
    private ModeAdapter modeAdapter;
    private List<EquipmentBean> useList, allList;
    private List<ModeBean> modeList;
    private TextView title;


    public AllFragment() {
    }

    public static AllFragment newInstance() {
        AllFragment fragment = new AllFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_family_all;
    }

    @Override
    protected void initEventAndData() {
        rvMode = $(R.id.rv_mode);
        rvUsually = $(R.id.rv_usually);
        rvAll = $(R.id.rv_all);
        title = $(R.id.family_home_title);

        //模式
        modeList = new ArrayList<>();
        modeList.add(new ModeBean("回家模式"));
        modeList.add(new ModeBean("离家模式"));
        modeList.add(new ModeBean("睡眠模式"));

        modeAdapter = new ModeAdapter(mActivity, modeList);
        rvMode.addItemDecoration(new HorizontalDecoration(25));
        rvMode.setAdapter(modeAdapter);

        //设备
        useList = new ArrayList<>();
        useList.add(new EquipmentBean("窗帘", R.mipmap.icon_curtain));
        useList.add(new EquipmentBean("红外线", R.mipmap.icon_infrared));
        useList.add(new EquipmentBean("烟雾感应", R.mipmap.icon_cloud));
        useList.add(new EquipmentBean("燃气报警", R.mipmap.icon_gas));

        useAdapter = new EquipmentAdapter(mActivity, useList);
        rvUsually.addItemDecoration(new HorizontalDecoration(25));
        rvUsually.setAdapter(useAdapter);

        allList = new ArrayList<>();
        allList.add(new EquipmentBean("灯", R.mipmap.icon_lamp));
        allList.add(new EquipmentBean("窗帘", R.mipmap.icon_curtain));
        allList.add(new EquipmentBean("红外线", R.mipmap.icon_infrared));
        allList.add(new EquipmentBean("烟雾感应", R.mipmap.icon_cloud));
        allList.add(new EquipmentBean("燃气报警", R.mipmap.icon_gas));
        allList.add(new EquipmentBean("空调", R.mipmap.icon_air));
        allList.add(new EquipmentBean("地暖", R.mipmap.icon_floor));
        allList.add(new EquipmentBean("新风", R.mipmap.icon_wind));
        allList.add(new EquipmentBean("门锁", R.mipmap.icon_lock));
        allList.add(new EquipmentBean("摄像头", R.mipmap.icon_camera));

        allAdapter = new EquipmentAdapter(mActivity, allList);
        rvAll.addItemDecoration(new GridDecoration(25, 25));
        rvAll.setAdapter(allAdapter);


        final LinearLayoutManager llm = (LinearLayoutManager) rvUsually.getLayoutManager();
        useAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
//                showSeekbarDialog();
                showOtherDialog();

//                View view = llm.getChildAt(position);
//                int[] location = new int[2];
//                view.getLocationInWindow(location); //获取在当前窗口内的绝对坐标
//                view.getLocationOnScreen(location);//获取在整个屏幕内的绝对坐标
//
//                Dialog dialog = new Dialog(mActivity);
//                dialog.setContentView(R.layout.layout_seekbar_pop);
//                Window dialogWindow = dialog.getWindow();
//                dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//                WindowManager.LayoutParams lp = dialogWindow.getAttributes();
//                dialogWindow.setGravity(Gravity.LEFT|Gravity.TOP);
////
//                int wi=view.getWidth();
//
//                lp.x = 0;
//                lp.y = location[1];
//                lp.width = 320;
//                lp.height = view.getHeight();
//                dialogWindow.setAttributes(lp);
//
//                dialog.show();

//                new WJPopWindow.PopupWindowBuilder(mActivity)
//                        .setView(R.layout.layout_seekbar_pop)//显示的布局，还可以通过设置一个View
//                        .size(view.getWidth(), view.getHeight()) //设置显示的大小，不设置就默认包裹内容
//                        .setFocusable(true)//是否获取焦点，默认为ture
//                        .setOutsideTouchable(true)//是否PopupWindow 以外触摸dissmiss
//
//                        .create()//创建PopupWindow
//                        .showAtLocation(rvUsually,Gravity.CENTER, 0,0);//显示PopupWindow
            }
        });
    }

    /**
     * author ：shenbingkai@163.com
     * date ：2019-01-24 23:56
     * description ：弹出seek开关对化框
     */
    public void showSeekbarDialog() {
        Dialog dialog = new Dialog(mActivity);
        dialog.setContentView(R.layout.layout_seekbar_pop);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void showOtherDialog() {
        Dialog dialog = new Dialog(mActivity);
        View conv = LayoutInflater.from(mActivity).inflate(R.layout.layout_other_switch_pop, null);
        RecyclerView rv = conv.findViewById(R.id.rv_expand);
        List<EquipmentBean> datas=new ArrayList<>();

        datas.add(new EquipmentBean("拉开", R.mipmap.icon_smart_curtain_on));
        datas.add(new EquipmentBean("关上", R.mipmap.icon_curtain));

        rv.setAdapter(new EquipmentExpandAdapter(mActivity,datas));
        dialog.setContentView(conv);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(400,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

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
