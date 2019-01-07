package com.abctime.businesslib.arouter;

import com.alibaba.android.arouter.facade.template.IProvider;
import com.google.gson.JsonArray;

/**
 * Created by xmren on 2018/5/21.
 */

public interface LibraryService extends IProvider{
    public void saveLibraryTolocal(JsonArray jsonArray);
}
