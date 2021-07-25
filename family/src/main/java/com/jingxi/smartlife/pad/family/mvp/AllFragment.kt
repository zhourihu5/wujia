package com.jingxi.smartlife.pad.family.mvp

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jingxi.smartlife.pad.family.R
import com.jingxi.smartlife.pad.family.mvp.adapter.EquipmentAdapter
import com.jingxi.smartlife.pad.family.mvp.adapter.EquipmentExpandAdapter
import com.jingxi.smartlife.pad.family.mvp.adapter.ModeAdapter
import com.jingxi.smartlife.pad.family.mvp.data.EquipmentBean
import com.jingxi.smartlife.pad.family.mvp.data.EquipmentType
import com.jingxi.smartlife.pad.family.mvp.data.ModeBean
import com.jingxi.smartlife.pad.family.mvp.view.EqExpandGridDecoration
import com.wujia.lib.widget.ArcSeekBar
import com.wujia.lib_common.base.BaseFragment
import com.wujia.lib_common.base.view.GridDecoration
import com.wujia.lib_common.base.view.HorizontalDecoration
import java.util.*


/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：全部设备
 */
class AllFragment : BaseFragment() {
    override val layoutId: Int
        get() = R.layout.fragment_family_all
    private var rvUsually: RecyclerView? = null
    private var rvAll: RecyclerView? = null
    private var rvMode: RecyclerView? = null
    private var useAdapter: EquipmentAdapter? = null
    private var allAdapter: EquipmentAdapter? = null
    private var modeAdapter: ModeAdapter? = null
    private var useList: MutableList<EquipmentBean>? = null
    private var allList: MutableList<EquipmentBean>? = null
    private var modeList:ArrayList<ModeBean> = ArrayList()
    private var title: TextView? = null



    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        rvMode = `$`(R.id.rv_mode)
        rvUsually = `$`(R.id.rv_usually)
        rvAll = `$`(R.id.rv_all)
        title = `$`(R.id.family_home_title)

        testData()

        modeAdapter = ModeAdapter(mActivity, modeList)
        rvMode!!.addItemDecoration(HorizontalDecoration(13))
        rvMode!!.adapter = modeAdapter

        useAdapter = useList?.let { EquipmentAdapter(mActivity, it) }
        rvUsually!!.addItemDecoration(HorizontalDecoration(13))
        rvUsually!!.adapter = useAdapter

        allAdapter = allList?.let { EquipmentAdapter(mActivity, it) }
        rvAll!!.addItemDecoration(GridDecoration(13, 13))
        rvAll!!.adapter = allAdapter


        val llm = rvUsually!!.layoutManager as LinearLayoutManager?
        useAdapter!!.setOnItemClickListener { adapter, holder, position -> expand(useList!![position]) }

        allAdapter!!.setOnItemClickListener { adapter, holder, position -> expand(allList!![position]) }
    }

    private fun expand(bean: EquipmentBean) {
        when (bean.type) {
            EquipmentType.LINGHT, EquipmentType.AIR, EquipmentType.LAND, EquipmentType.WIND -> showSeekbarDialog(bean.type)

            EquipmentType.DOOR, EquipmentType.SMOKE, EquipmentType.CURTAIN, EquipmentType.FUEL_GAS, EquipmentType.INFRAED_RAY -> showOtherDialog(bean.menus)

            EquipmentType.CARMERA ->

                startActivity(Intent(mActivity, FamilyCameraActivity::class.java))
        }
    }

    /**
     * author ：shenbingkai@163.com
     * date ：2019-01-24 23:56
     * description ：弹出seek开关对化框
     */
    private fun showSeekbarDialog(type: EquipmentType) {
        val dialog = Dialog(mActivity)
        val conv = LayoutInflater.from(mActivity).inflate(R.layout.layout_seekbar_pop, null)

        val tvDese = conv.findViewById<TextView>(R.id.pop_desc)
        val tvProgress = conv.findViewById<TextView>(R.id.pop_progress)
        val seekBar = conv.findViewById<ArcSeekBar>(R.id.pop_arc_seekbar)

        var desc = ""
        var unit = ""
        var normal = 0

        when (type) {
            EquipmentType.LINGHT -> {
                desc = "亮度"
                unit = "%"
                normal = 80
            }
            EquipmentType.AIR -> {
                desc = "温度"
                unit = "℃"
                normal = 30
            }
            EquipmentType.LAND -> {
                desc = "温度"
                unit = "℃"
                normal = 30
            }
            EquipmentType.WIND -> {
                desc = "风速"
                normal = 50
            }
        }
        tvDese.text = desc
        val finalUnit = unit
        tvProgress.text = normal.toString() + finalUnit

        seekBar.setOnProgressChangeListener(object : ArcSeekBar.OnProgressChangeListener {
            override fun onProgressChanged(seekBar: ArcSeekBar, progress: Int, isUser: Boolean) {
                tvProgress.text = progress.toString() + finalUnit
            }

            override fun onStartTrackingTouch(seekBar: ArcSeekBar) {

            }

            override fun onStopTrackingTouch(seekBar: ArcSeekBar) {

            }
        })
        conv.findViewById<View>(R.id.close_btn).setOnClickListener { dialog.dismiss() }
        dialog.setContentView(conv)
        val dialogWindow = dialog.window
        dialogWindow!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogWindow.setGravity(Gravity.CENTER)
        dialog.show()
    }

    private fun showOtherDialog(menus: List<EquipmentBean.Menu>?) {
        val dialog = Dialog(mActivity)
        val conv = LayoutInflater.from(mActivity).inflate(R.layout.layout_other_switch_pop, null)
        val rv = conv.findViewById<RecyclerView>(R.id.rv_expand)
        conv.findViewById<View>(R.id.close_btn).setOnClickListener { dialog.dismiss() }

        rv.addItemDecoration(EqExpandGridDecoration(25))
        rv.adapter = menus?.let { EquipmentExpandAdapter(mActivity, it) }
        dialog.setContentView(conv)
        val dialogWindow = dialog.window
        dialogWindow!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialogWindow.setGravity(Gravity.CENTER)
        dialog.show()
    }

    private fun testData() {

        //模式
        modeList.clear()
        modeList.add(ModeBean("回家模式"))
        modeList.add(ModeBean("离家模式"))
        modeList.add(ModeBean("睡眠模式"))

        //设备
        useList = ArrayList()
        allList = ArrayList()

        val light = EquipmentBean(EquipmentType.LINGHT, "灯", R.mipmap.icon_lamp)
        val curtain = EquipmentBean(EquipmentType.CURTAIN, "窗帘", R.mipmap.icon_curtain)
        curtain.addMenu(EquipmentBean.Menu("拉开", R.mipmap.icon_smart_curtain_on))
        curtain.addMenu(EquipmentBean.Menu("关上", R.mipmap.icon_curtain))

        val ray = EquipmentBean(EquipmentType.INFRAED_RAY, "红外线", R.mipmap.icon_infrared)
        ray.addMenu(EquipmentBean.Menu("客厅", R.mipmap.icon_lamp))
        ray.addMenu(EquipmentBean.Menu("主卧", R.mipmap.icon_lamp))
        ray.addMenu(EquipmentBean.Menu("次卧", R.mipmap.icon_lamp))
        ray.addMenu(EquipmentBean.Menu("厨房", R.mipmap.icon_lamp))

        val smoke = EquipmentBean(EquipmentType.SMOKE, "烟雾感应", R.mipmap.icon_cloud)
        smoke.addMenu(EquipmentBean.Menu("触发报警", R.mipmap.icon_smart_warning))
        smoke.addMenu(EquipmentBean.Menu("恢复正常", R.mipmap.icon_smart_normal))

        val fuelGas = EquipmentBean(EquipmentType.SMOKE, "燃气报警", R.mipmap.icon_cloud)
        fuelGas.addMenu(EquipmentBean.Menu("触发报警", R.mipmap.icon_smart_warning))
        fuelGas.addMenu(EquipmentBean.Menu("恢复正常", R.mipmap.icon_smart_normal))

        val air = EquipmentBean(EquipmentType.AIR, "空调", R.mipmap.icon_air)

        val land = EquipmentBean(EquipmentType.LAND, "地暖", R.mipmap.icon_floor)
        val wind = EquipmentBean(EquipmentType.WIND, "新风", R.mipmap.icon_wind)
        val door = EquipmentBean(EquipmentType.DOOR, "门锁", R.mipmap.icon_lock)
        door.addMenu(EquipmentBean.Menu("开门", R.mipmap.icon_smartgate_open))
        door.addMenu(EquipmentBean.Menu("关门", R.mipmap.icon_smartgate_close))

        val carmera = EquipmentBean(EquipmentType.CARMERA, "摄像头", R.mipmap.icon_camera)

        useList!!.add(curtain)
        useList!!.add(ray)
        useList!!.add(smoke)
        useList!!.add(fuelGas)

        allList!!.add(light)
        allList!!.add(curtain)
        allList!!.add(ray)
        allList!!.add(smoke)
        allList!!.add(fuelGas)
        allList!!.add(air)
        allList!!.add(land)
        allList!!.add(wind)
        allList!!.add(door)
        allList!!.add(carmera)
    }

    companion object {

        fun newInstance(): AllFragment {
            val fragment = AllFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

}
