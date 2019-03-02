package com.wujia.intellect.terminal.family.mvp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import com.wujia.businesslib.TitleFragment;
import com.wujia.intellect.terminal.family.R;
import com.wujia.intellect.terminal.family.mvp.adapter.EquipmentAdapter;
import com.wujia.intellect.terminal.family.mvp.adapter.EquipmentExpandAdapter;
import com.wujia.intellect.terminal.family.mvp.adapter.ModeAdapter;
import com.wujia.intellect.terminal.family.mvp.data.EquipmentBean;
import com.wujia.intellect.terminal.family.mvp.data.EquipmentType;
import com.wujia.intellect.terminal.family.mvp.data.ModeBean;
import com.wujia.intellect.terminal.family.mvp.view.EqExpandGridDecoration;
import com.wujia.lib.widget.ArcSeekBar;
import com.wujia.lib_common.base.BaseFragment;
import com.wujia.lib_common.base.baseadapter.MultiItemTypeAdapter;
import com.wujia.lib_common.base.view.GridDecoration;
import com.wujia.lib_common.base.view.HorizontalDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：全部设备
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
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);
        rvMode = $(R.id.rv_mode);
        rvUsually = $(R.id.rv_usually);
        rvAll = $(R.id.rv_all);
        title = $(R.id.family_home_title);

        testData();

        modeAdapter = new ModeAdapter(mActivity, modeList);
        rvMode.addItemDecoration(new HorizontalDecoration(13));
        rvMode.setAdapter(modeAdapter);

        useAdapter = new EquipmentAdapter(mActivity, useList);
        rvUsually.addItemDecoration(new HorizontalDecoration(13));
        rvUsually.setAdapter(useAdapter);

        allAdapter = new EquipmentAdapter(mActivity, allList);
        rvAll.addItemDecoration(new GridDecoration(13, 13));
        rvAll.setAdapter(allAdapter);


        final LinearLayoutManager llm = (LinearLayoutManager) rvUsually.getLayoutManager();
        useAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
//                showSeekbarDialog();
                expand(useList.get(position));


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

        allAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnRVItemClickListener() {
            @Override
            public void onItemClick(@Nullable RecyclerView.Adapter adapter, RecyclerView.ViewHolder holder, int position) {
                expand(allList.get(position));
            }
        });
    }

    private void expand(EquipmentBean bean) {
        switch (bean.type) {
            case LINGHT:
            case AIR:
            case LAND:
            case WIND:
                showSeekbarDialog(bean.type);
                break;

            case DOOR:
            case SMOKE:
            case CURTAIN:
            case FUEL_GAS:
            case INFRAED_RAY:
                showOtherDialog(bean.menus);
                break;

            case CARMERA:

                startActivity(new Intent(mActivity,FamilyCameraActivity.class));
                break;
        }
    }

    /**
     * author ：shenbingkai@163.com
     * date ：2019-01-24 23:56
     * description ：弹出seek开关对化框
     */
    public void showSeekbarDialog(EquipmentType type) {
        final Dialog dialog = new Dialog(mActivity);
        View conv = LayoutInflater.from(mActivity).inflate(R.layout.layout_seekbar_pop, null);

        TextView tvDese = conv.findViewById(R.id.pop_desc);
        final TextView tvProgress = conv.findViewById(R.id.pop_progress);
        ArcSeekBar seekBar = conv.findViewById(R.id.pop_arc_seekbar);

        String desc = "";
        String unit = "";
        int normal = 0;

        switch (type) {
            case LINGHT:
                desc = "亮度";
                unit = "%";
                normal = 80;
                break;
            case AIR:
                desc = "温度";
                unit = "℃";
                normal = 30;
                break;
            case LAND:
                desc = "温度";
                unit = "℃";
                normal = 30;
                break;
            case WIND:
                desc = "风速";
                normal = 50;
                break;
        }
        tvDese.setText(desc);
        final String finalUnit = unit;
        tvProgress.setText(normal + finalUnit);

        seekBar.setOnProgressChangeListener(new ArcSeekBar.OnProgressChangeListener() {
            @Override
            public void onProgressChanged(ArcSeekBar seekBar, int progress, boolean isUser) {
                tvProgress.setText(progress + finalUnit);
            }

            @Override
            public void onStartTrackingTouch(ArcSeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(ArcSeekBar seekBar) {

            }
        });
        conv.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(conv);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
    }

    public void showOtherDialog(List<EquipmentBean.Menu> menus) {
        final Dialog dialog = new Dialog(mActivity);
        View conv = LayoutInflater.from(mActivity).inflate(R.layout.layout_other_switch_pop, null);
        RecyclerView rv = conv.findViewById(R.id.rv_expand);
        conv.findViewById(R.id.close_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        rv.addItemDecoration(new EqExpandGridDecoration(25));
        rv.setAdapter(new EquipmentExpandAdapter(mActivity, menus));
        dialog.setContentView(conv);
        Window dialogWindow = dialog.getWindow();
        dialogWindow.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialogWindow.setGravity(Gravity.CENTER);
        dialog.show();
    }

    private void testData() {

        //模式
        modeList = new ArrayList<>();
        modeList.add(new ModeBean("回家模式"));
        modeList.add(new ModeBean("离家模式"));
        modeList.add(new ModeBean("睡眠模式"));

        //设备
        useList = new ArrayList<>();
        allList = new ArrayList<>();

        EquipmentBean light = new EquipmentBean(EquipmentType.LINGHT, "灯", R.mipmap.icon_lamp);
        EquipmentBean curtain = new EquipmentBean(EquipmentType.CURTAIN, "窗帘", R.mipmap.icon_curtain);
        curtain.addMenu(new EquipmentBean.Menu("拉开", R.mipmap.icon_smart_curtain_on));
        curtain.addMenu(new EquipmentBean.Menu("关上", R.mipmap.icon_curtain));

        EquipmentBean ray = new EquipmentBean(EquipmentType.INFRAED_RAY, "红外线", R.mipmap.icon_infrared);
        ray.addMenu(new EquipmentBean.Menu("客厅", R.mipmap.icon_lamp));
        ray.addMenu(new EquipmentBean.Menu("主卧", R.mipmap.icon_lamp));
        ray.addMenu(new EquipmentBean.Menu("次卧", R.mipmap.icon_lamp));
        ray.addMenu(new EquipmentBean.Menu("厨房", R.mipmap.icon_lamp));

        EquipmentBean smoke = new EquipmentBean(EquipmentType.SMOKE, "烟雾感应", R.mipmap.icon_cloud);
        smoke.addMenu(new EquipmentBean.Menu("触发报警", R.mipmap.icon_smart_warning));
        smoke.addMenu(new EquipmentBean.Menu("恢复正常", R.mipmap.icon_smart_normal));

        EquipmentBean fuelGas = new EquipmentBean(EquipmentType.SMOKE, "燃气报警", R.mipmap.icon_cloud);
        fuelGas.addMenu(new EquipmentBean.Menu("触发报警", R.mipmap.icon_smart_warning));
        fuelGas.addMenu(new EquipmentBean.Menu("恢复正常", R.mipmap.icon_smart_normal));

        EquipmentBean air = new EquipmentBean(EquipmentType.AIR, "空调", R.mipmap.icon_air);

        EquipmentBean land = new EquipmentBean(EquipmentType.LAND, "地暖", R.mipmap.icon_floor);
        EquipmentBean wind = new EquipmentBean(EquipmentType.WIND, "新风", R.mipmap.icon_wind);
        EquipmentBean door = new EquipmentBean(EquipmentType.DOOR, "门锁", R.mipmap.icon_lock);
        door.addMenu(new EquipmentBean.Menu("开门", R.mipmap.icon_smartgate_open));
        door.addMenu(new EquipmentBean.Menu("关门", R.mipmap.icon_smartgate_close));

        EquipmentBean carmera = new EquipmentBean(EquipmentType.CARMERA, "摄像头", R.mipmap.icon_camera);

        useList.add(curtain);
        useList.add(ray);
        useList.add(smoke);
        useList.add(fuelGas);

        allList.add(light);
        allList.add(curtain);
        allList.add(ray);
        allList.add(smoke);
        allList.add(fuelGas);
        allList.add(air);
        allList.add(land);
        allList.add(wind);
        allList.add(door);
        allList.add(carmera);
    }

}
