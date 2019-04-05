package com.wujia.lib_common.data.network;

import com.wujia.lib_common.base.BaseView;
import com.wujia.lib_common.data.network.exception.ApiException;
import com.wujia.lib_common.data.network.exception.ERROR;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

public class SimpleRequestSubscriber<T> extends RequestSubscriber<T> {
    public static final int SHOWERRORMESSAGE = 1;
    public static final int SHOWERRORVIEW = 2;
    public static final int ERROR_NONE = 3;


    public SimpleRequestSubscriber(BaseView view) {
        super(view);
        ActionConfigBuilder builder = new ActionConfigBuilder();
        builder.showLoading(false);
        builder.errorAction(ERROR_NONE);
        actionConfig = builder.build();
    }


    protected SimpleRequestSubscriber(BaseView view, boolean showLoading) {
        super(view);
        ActionConfigBuilder builder = new ActionConfigBuilder();
        builder.showLoading(showLoading);
        builder.errorAction(SHOWERRORMESSAGE);
        actionConfig = builder.build();
    }

    protected SimpleRequestSubscriber(BaseView view, int errorAction) {
        super(view);
        ActionConfigBuilder builder = new ActionConfigBuilder();
        builder.showLoading(false);
        builder.errorAction(errorAction);
        actionConfig = builder.build();
    }

    protected SimpleRequestSubscriber(BaseView view, boolean showLoading, int errorAction) {
        super(view);
        ActionConfigBuilder builder = new ActionConfigBuilder();
        builder.showLoading(showLoading);
        builder.errorAction(errorAction);
        actionConfig = builder.build();
    }

    public SimpleRequestSubscriber(BaseView view, ActionConfig actionConfig) {
        super(view);
        this.actionConfig = actionConfig;
    }

    @Override
    public void onResponse(T response) {

    }

    @Override
    public void onFailed(ApiException apiException) {
        if (actionConfig.errorAction == SHOWERRORMESSAGE) {
//            if (apiException.code != 900 && apiException.code != 2004 && apiException.code != 308) {
//                view.showErrorMsg(apiException.getMessage());
//            }
            view.showErrorMsg(apiException.getMessage());
        } else if (actionConfig.errorAction == SHOWERRORVIEW) {
//            if (apiException.code .equals( ERROR.NETWORK_CONNECT_ERROR)) {
//            }
        } else {

        }
    }

    /**
     * 为以后扩展需要
     */
    public static class ActionConfigBuilder {
        ActionConfig actionConfig;

        public ActionConfigBuilder() {
            actionConfig = new ActionConfig();
        }

        public ActionConfigBuilder showLoading(boolean showLoading) {
            actionConfig.isShowLoading = showLoading;
            return this;
        }

        public ActionConfigBuilder errorAction(int errorAction) {
            actionConfig.errorAction = errorAction;
            return this;
        }

        public ActionConfig build() {
            return new ActionConfig(actionConfig);
        }
    }

    /**
     * 为以后扩展需要
     */
    public static class ActionConfig {
        public boolean isShowLoading = false;
        public int errorAction = SHOWERRORMESSAGE;


        public ActionConfig(boolean isShowLoading, int errorAction) {
            this.isShowLoading = isShowLoading;
            this.errorAction = errorAction;
        }

        public ActionConfig() {
        }

        public ActionConfig(ActionConfig actionConfig) {
            this.isShowLoading = actionConfig.isShowLoading;
            this.errorAction = actionConfig.errorAction;
        }
    }

}
