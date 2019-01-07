package com.abctime.lib_common.utils;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.liulishuo.okdownload.DownloadTask;
import com.liulishuo.okdownload.core.cause.EndCause;
import com.liulishuo.okdownload.core.cause.ResumeFailedCause;
import com.liulishuo.okdownload.core.listener.DownloadListener1;
import com.liulishuo.okdownload.core.listener.assist.Listener1Assist;

import java.io.File;

/**
 * Created by xmren on 2018/5/16.
 */

public class AbcDownLoadManager {
    public static DownloadTask dowload(String fileUrl, File saveFile, final DownloadCallBack downloadCallBack) {
        DownloadTask.Builder builder = new DownloadTask.Builder(fileUrl, saveFile);

        // Set the minimum internal milliseconds of progress callbacks to 100ms.(default is 3000)
        builder.setMinIntervalMillisCallbackProcess(1000);

        // set the priority of the task to 10, higher means less time to wait to download.(default is 0)
        builder.setPriority(10);

        // set the read buffer to 8192 bytes for the response input-stream.(default is 4096)
        builder.setReadBufferSize(8192);

        // set the flush buffer to 32768 bytes for the buffered output-stream.(default is 16384)
        builder.setFlushBufferSize(32768);

        // set this task allow using 5 connections to download data.
        builder.setConnectionCount(3);

        // build the task.
        DownloadTask task = builder.build();

        task.enqueue(new DownloadListener1() {

            @Override
            public void taskStart(@NonNull DownloadTask task, @NonNull Listener1Assist.Listener1Model model) {
                if (downloadCallBack != null)
                    downloadCallBack.start();
            }

            @Override
            public void retry(@NonNull DownloadTask task, @NonNull ResumeFailedCause cause) {

            }

            @Override
            public void connected(@NonNull DownloadTask task, int blockCount, long currentOffset, long totalLength) {

            }

            @Override
            public void progress(@NonNull DownloadTask task, long currentOffset, long totalLength) {
                if (downloadCallBack != null)
                    downloadCallBack.process(currentOffset, totalLength);
            }

            @Override
            public void taskEnd(@NonNull DownloadTask task, @NonNull EndCause cause, @Nullable Exception realCause, @NonNull Listener1Assist.Listener1Model model) {
                if (downloadCallBack != null) {
                    EndTaskCause status;
                    if (EndCause.COMPLETED == cause) {
                        status = EndTaskCause.COMPLETED;
                    } else if (EndCause.CANCELED == cause) {
                        status = EndTaskCause.CANCELED;
                    } else {
                        status = EndTaskCause.ERROR;
                    }
                    downloadCallBack.fnish(status, realCause);
                }
            }
        });
        return task;
    }

    public interface DownloadCallBack {
        void start();

        void process(long currentOffset, long totalLength);

        void fnish(EndTaskCause status, Exception ex);
    }

    public enum EndTaskCause {
        COMPLETED,
        ERROR,
        CANCELED,
    }
}
