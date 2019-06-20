package com.wujia.businesslib;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;
import com.wujia.businesslib.listener.DownloadListener;
import com.wujia.lib_common.utils.AppContext;
import com.wujia.lib_common.utils.FileUtil;
import com.wujia.lib_common.utils.LogUtil;

import java.io.File;

/**
 * author ：shenbingkai@163.com
 * date ：2019-03-30
 * description ：
 */
public class DownloadUtil {

    public static final int STATE_COMPLETE = 0;
    public static final int STATE_CANCELED = 10;
    public static final int STATE_OTHER = 20;

    public static DownloadTask download(String url, final DownloadListener listener) {

        String fileName = FileUtil.getNameForUrl(url);

        DownloadTask.Builder builder = new DownloadTask.Builder(url, new File(FileUtil.getDowndloadApkPath(AppContext.get()), fileName));
        builder.setMinIntervalMillisCallbackProcess(1000);
        builder.setPriority(10);
        builder.setReadBufferSize(8192);
        builder.setFlushBufferSize(32768);
        builder.setConnectionCount(1);
        DownloadTask task = builder.build();
        task.enqueue(new DownloadListener1() {
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                listener.onTaskStart();
            }

            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {
            }

            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {
            }

            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                if (totalLength != 0) {
                    int percent = (int) (currentOffset * 1.0f / totalLength * 100);
                    LogUtil.i("正在下载：" + percent + " %");
                    listener.onTaskProgress(percent, currentOffset,  totalLength);
                }
            }

            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {

                LogUtil.i("taskEnd");
                if (EndCause.COMPLETED == cause) {
                    listener.onTaskComplete(STATE_COMPLETE, task.getFile().getAbsolutePath());

//                    DataBaseUtil.insert(new AppPackageBean(subscriptions.get(position).servicePackage));
                    //TODO 安装需要系统签名
//                            AppUtil.install(task.getFile().getPath());
                } else if (EndCause.CANCELED == cause) {
                    listener.onTaskComplete(STATE_CANCELED, "");

                } else {
                    if(realCause!=null){
                        realCause.printStackTrace();
                    }
                    LogUtil.i("cause:"+cause.name());
                    listener.onTaskComplete(STATE_OTHER, "");

                }
            }
        });
        return task;
    }
}
