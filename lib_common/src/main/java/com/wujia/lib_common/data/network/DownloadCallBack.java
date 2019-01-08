package com.wujia.lib_common.data.network;

public abstract class DownloadCallBack {
    public void onStart() {
    }

    public void onCompleted() {
    }

    abstract public void onError(Throwable e);

    public void onProgress(long fileSizeDownloaded) {
    }

    abstract public void onSucess(String path, long fileSize);
}
