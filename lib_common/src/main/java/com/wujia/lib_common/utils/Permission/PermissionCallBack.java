package com.wujia.lib_common.utils.Permission;

/**
 * Created by yseerd on 2018/6/4.
 */

public  interface PermissionCallBack
{

    public void permissionGranted() ;

    public void permissionDenied();

    public void permissionDeniedWithNeverAsk();

}