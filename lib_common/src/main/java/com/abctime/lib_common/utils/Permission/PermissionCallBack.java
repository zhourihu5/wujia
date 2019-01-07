package com.abctime.lib_common.utils.Permission;

import com.tbruyelle.rxpermissions2.Permission;

import io.reactivex.functions.Consumer;

/**
 * Created by yseerd on 2018/6/4.
 */

public  interface PermissionCallBack
{

    public void permissionGranted() ;

    public void permissionDenied();

    public void permissionDeniedWithNeverAsk();

}