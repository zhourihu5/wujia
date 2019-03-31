package com.wujia.intellect.terminal.mvp.setting.data;

import com.wujia.businesslib.data.RootResponse;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-31
 * description ：
 */
public class VersionBean extends RootResponse {

    public String remark;
    public ArrayList<Version> infoList;

    public static class Version implements Serializable {
        public String version;
        public String imageurl;
        public String packageName;
        public int versionId;
        public boolean necessaryUpdate;
    }
}
