package com.jingxi.smartlife.pad.mvp.setting.view

import android.os.Bundle
import butterknife.OnClick
import com.jingxi.smartlife.pad.R
import com.jingxi.smartlife.pad.mvp.home.data.HomeUserInfoBean
import com.jingxi.smartlife.pad.mvp.setting.adapter.SetMemberAdapter
import com.jingxi.smartlife.pad.mvp.setting.model.FamilyMemberModel
import com.wujia.businesslib.TitleFragment
import com.wujia.businesslib.base.DataManager
import com.wujia.businesslib.data.ApiResponse
import com.wujia.businesslib.dialog.InputDialog
import com.wujia.businesslib.event.EventBusUtil
import com.wujia.businesslib.event.EventMemberChange
import com.wujia.businesslib.listener.OnInputDialogListener
import com.wujia.businesslib.util.LoginUtil
import com.wujia.lib_common.base.view.VerticallDecoration
import com.wujia.lib_common.data.network.SimpleRequestSubscriber
import com.wujia.lib_common.data.network.exception.ApiException
import com.wujia.lib_common.utils.LogUtil
import kotlinx.android.synthetic.main.fragment_member.*
import java.util.*

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-12 20:06
 * description ：
 */
class FamilyMemberFragment : TitleFragment(), OnInputDialogListener {



    internal var mAdapter: SetMemberAdapter? = null

    internal lateinit var familyMemberModel: FamilyMemberModel

    val headUrl: String
        get() {
            val num = (Math.random() * 6 + 1).toInt()
            return String.format("file:///android_asset/img_default_head_%d.png", num)
        }


    override fun getLayoutId(): Int {
        return R.layout.fragment_member
    }

    override fun getTitle(): Int {
        return R.string.set_family_member
    }

    override fun onLazyInitView(savedInstanceState: Bundle?) {
        super.onLazyInitView(savedInstanceState)
        // 懒加载
        // 同级Fragment场景、ViewPager场景均适用

        rv_member!!.addItemDecoration(VerticallDecoration(1))

        familyMemberModel = FamilyMemberModel()
        var familyId: String? = null
        try {
            familyId = DataManager.getFamilyId()
        } catch (e: Exception) {
            LogUtil.t("get familyId failed", e)
            LoginUtil.toLoginActivity()
            return
        }

        addSubscribe(familyMemberModel.getFamilyMemberList(familyId).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>>(this, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<List<HomeUserInfoBean.DataBean.UserInfoListBean>>) {
                super.onResponse(response)
                mAdapter = SetMemberAdapter(mContext, response.data)
                rv_member!!.adapter = mAdapter
            }

        }))


    }

    @OnClick(R.id.btn_add_member)
    fun onViewClicked() {
        val inputDialog = InputDialog.Builder().title(getString(R.string.add_family_member))
                .hint(getString(R.string.please_input_member_phone))
                .confirm(getString(R.string.invite))
                .listener(this)
                .build(mContext)

        inputDialog.show()
    }

    override fun dialogSureClick(input: String) {
        var familyId: String? = null
        try {
            familyId = DataManager.getFamilyId()
        } catch (e: Exception) {
            LoginUtil.toLoginActivity()
            LogUtil.t("get familyId failed", e)
            return
        }

        addSubscribe(familyMemberModel.addFamilyMember(input, familyId).subscribeWith(object : SimpleRequestSubscriber<ApiResponse<String>>(this, SimpleRequestSubscriber.ActionConfig(true, SimpleRequestSubscriber.SHOWERRORMESSAGE)) {
            override fun onResponse(response: ApiResponse<String>) {
                super.onResponse(response)
                val userInfoListBean = HomeUserInfoBean.DataBean.UserInfoListBean()
                userInfoListBean.userName = input
                if (mAdapter == null) {
                    mAdapter = SetMemberAdapter(mContext, ArrayList())
                    rv_member!!.adapter = mAdapter
                }
                mAdapter!!.datas.add(userInfoListBean)
                mAdapter!!.notifyDataSetChanged()

                EventBusUtil.post(EventMemberChange())
            }

        }))


    }

    companion object {

        fun newInstance(): FamilyMemberFragment {
            val fragment = FamilyMemberFragment()
            val args = Bundle()
            fragment.arguments = args
            return fragment
        }
    }


}
