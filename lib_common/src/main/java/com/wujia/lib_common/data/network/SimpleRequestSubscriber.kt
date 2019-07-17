package com.wujia.lib_common.data.network

import com.wujia.lib_common.base.BaseView
import com.wujia.lib_common.data.network.exception.ApiException

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

open class SimpleRequestSubscriber<T> : RequestSubscriber<T> {


    constructor(view: BaseView) : super(view) {
        val builder = ActionConfigBuilder()
        builder.showLoading(false)
        builder.errorAction(ERROR_NONE)
        actionConfig = builder.build()
    }


    protected constructor(view: BaseView, showLoading: Boolean) : super(view) {
        val builder = ActionConfigBuilder()
        builder.showLoading(showLoading)
        builder.errorAction(SHOWERRORMESSAGE)
        actionConfig = builder.build()
    }

    protected constructor(view: BaseView, errorAction: Int) : super(view) {
        val builder = ActionConfigBuilder()
        builder.showLoading(false)
        builder.errorAction(errorAction)
        actionConfig = builder.build()
    }

    protected constructor(view: BaseView, showLoading: Boolean, errorAction: Int) : super(view) {
        val builder = ActionConfigBuilder()
        builder.showLoading(showLoading)
        builder.errorAction(errorAction)
        actionConfig = builder.build()
    }

    constructor(view: BaseView?, actionConfig: ActionConfig) : super(view) {
        this.actionConfig = actionConfig
    }

    override fun onResponse(response: T) {

    }

    override fun onFailed(apiException: ApiException) {
        if (actionConfig!!.errorAction == SHOWERRORMESSAGE) {
            //            if (apiException.code != 900 && apiException.code != 2004 && apiException.code != 308) {
            //                view.showErrorMsg(apiException.getMessage());
            //            }
            apiException.message?.let { view!!.showErrorMsg(it) }
        } else if (actionConfig!!.errorAction == SHOWERRORVIEW) {
            //            if (apiException.code .equals( ERROR.NETWORK_CONNECT_ERROR)) {
            //            }
        } else {

        }
    }

    /**
     * 为以后扩展需要
     */
    class ActionConfigBuilder {
        internal var actionConfig: ActionConfig

        init {
            actionConfig = ActionConfig()
        }

        fun showLoading(showLoading: Boolean): ActionConfigBuilder {
            actionConfig.isShowLoading = showLoading
            return this
        }

        fun errorAction(errorAction: Int): ActionConfigBuilder {
            actionConfig.errorAction = errorAction
            return this
        }

        fun build(): ActionConfig {
            return ActionConfig(actionConfig)
        }
    }

    /**
     * 为以后扩展需要
     */
    class ActionConfig {
        var isShowLoading = false
        var errorAction = SHOWERRORMESSAGE


        constructor(isShowLoading: Boolean, errorAction: Int) {
            this.isShowLoading = isShowLoading
            this.errorAction = errorAction
        }

        constructor() {}

        constructor(actionConfig: ActionConfig) {
            this.isShowLoading = actionConfig.isShowLoading
            this.errorAction = actionConfig.errorAction
        }
    }

    companion object {
        val SHOWERRORMESSAGE = 1
        val SHOWERRORVIEW = 2
        val ERROR_NONE = 3
    }

}
