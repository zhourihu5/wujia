package com.wujia.businesslib.listener;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
public interface DownloadListener {

    void onTaskStart();

    void onTaskProgress(int percent,long currentOffset, long totalLength);

    void onTaskComplete(int state,String path); //0成功
}
