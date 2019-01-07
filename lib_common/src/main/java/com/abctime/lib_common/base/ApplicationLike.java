package com.abctime.lib_common.base;

import android.app.Application;
import android.content.res.Configuration;

import com.alibaba.android.arouter.facade.template.IProvider;


public interface ApplicationLike extends IProvider {

  public void onTerminate(Application application);
  public void onCreateAsLibrary(Application application);
  public void onLowMemoryAsLibrary(Application application);
  public void onTrimMemoryAsLibrary(Application application, int level);
  public void onConfigurationChanged(Application application, Configuration configuration);

}
