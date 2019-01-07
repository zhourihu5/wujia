package com.abctime.businesslib.qiniu;

import com.abctime.businesslib.data.ApiResponse;
import com.abctime.businesslib.data.DataManager;
import com.abctime.lib_common.data.network.HttpHelper;
import com.abctime.lib_common.data.network.RxUtil;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCancellationSignal;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * Created by xmren on 2018/5/15.
 */

public class QiNiuUploadManager {
    public volatile static QiNiuUploadManager qiNiuUploadManager;
    public volatile UploadManager uploadManager;
    public static final int TYPE_BUG = 0;
    public static final int TYPE_AUDIO = 1;
    public static final int TYPE_IMAGE = 2;
    public static String qiniu_token_url = "/v2/api/api/qiniu-token";

    public static String TYPE_BUG_BUCKET = "abctime-bug";
    public static String TYPE_AUDIO_BUCKET = "abctime-audio-phone";

    public static String TYPE_IMAGE_BUCKET = "";

    public HttpHelper mHttpHelper;

    public ConcurrentHashMap<UploadInfo, UploadTask> uploadTaskConcurrentHashMap;

    public static QiNiuUploadManager getInstance() {
        if (qiNiuUploadManager == null) {
            synchronized (QiNiuUploadManager.class) {
                if (qiNiuUploadManager == null) {
                    qiNiuUploadManager = new QiNiuUploadManager();
                }
            }
        }
        return qiNiuUploadManager;
    }

    private QiNiuUploadManager() {
        uploadManager = getUploadInstatnce();
        uploadTaskConcurrentHashMap = new ConcurrentHashMap<UploadInfo, UploadTask>();
    }

    public void setHttpHelper(HttpHelper httpHelper) {
        mHttpHelper = httpHelper;
    }

    private void upload(final UploadInfo uploadInfo, final QiNiuUploadCallBack qiNiuUploadCallBack) {
        if (uploadManager == null) {
            throw new RuntimeException("please init UploadManager first");
        }
        UploadTask uploadTask = new UploadTask(uploadInfo.uploadFiles, uploadManager, uploadInfo.uploadType, new QiNiuUploadCallBack() {
            @Override
            public void onUploadFileCountChange(long totalCount, long remainCount) {
                if (qiNiuUploadCallBack != null)
                    qiNiuUploadCallBack.onUploadFileCountChange(totalCount, remainCount);
            }

            @Override
            public void onUploadFinish(HashMap<String, String> filesPathMap) {
                if (qiNiuUploadCallBack != null)
                    qiNiuUploadCallBack.onUploadFinish(filesPathMap);
                uploadTaskConcurrentHashMap.remove(uploadInfo);
            }

            @Override
            public void onUploadFailed() {
                cancelUploadTask(uploadInfo);
                if (qiNiuUploadCallBack != null)
                    qiNiuUploadCallBack.onUploadFailed();
            }
        });
        uploadTaskConcurrentHashMap.put(uploadInfo, uploadTask);
        uploadTask.start();
    }

    private boolean cancelUploadTask(UploadInfo uploadInfo) {
        if (uploadTaskConcurrentHashMap != null && uploadTaskConcurrentHashMap.size() > 0) {
            UploadTask task = uploadTaskConcurrentHashMap.remove(uploadInfo);
            if (task != null)
                task.cancelTask();
        }
        return true;
    }

    public boolean cancelAll() {
        if (uploadTaskConcurrentHashMap != null && uploadTaskConcurrentHashMap.size() > 0) {
            Collection<UploadTask> values = uploadTaskConcurrentHashMap.values();
            Iterator<UploadTask> iterator = values.iterator();
            while (iterator.hasNext()) {
                UploadTask task = iterator.next();
                if (task != null)
                    task.cancelTask();
            }
            uploadTaskConcurrentHashMap.clear();
        }
        return true;
    }

    public void uploadAudio(List<String> uploadFiles, QiNiuUploadCallBack qiNiuUploadCallBack) {
        upload(new UploadInfo(uploadFiles, TYPE_AUDIO), qiNiuUploadCallBack);
    }

    private UploadManager getUploadInstatnce() {
        if (uploadManager == null) {
            synchronized (UploadManager.class) {
                if (uploadManager == null) {
                    uploadManager = new UploadManager();
                }
            }
        }
        return uploadManager;
    }

    /**
     * 上传图片
     */
    public void uploadImage(String picPath, QiNiuUploadCallBack callBack) {

        List<String> image = new ArrayList<>();

        image.add(picPath);

        upload(new UploadInfo(image, TYPE_IMAGE), callBack);
    }

    public class UploadTask {
        List<String> uploadFiles;

        UploadManager uploadManager;
        int uploadType;
        CountDownLatch countDownLatch;
        QiNiuUploadCallBack qiNiuUploadCallBack;
        private Disposable getTokenDisposable;
        private volatile boolean isCancelled = false;
        private final HashMap<String, String> filesPathMap;

        public UploadTask(List<String> uploadFiles, UploadManager uploadManager, int uploadType, QiNiuUploadCallBack qiNiuUploadCallBack) {
            this.uploadFiles = uploadFiles;

            this.uploadManager = uploadManager;
            this.uploadType = uploadType;
            countDownLatch = new CountDownLatch(uploadFiles.size());
            this.qiNiuUploadCallBack = qiNiuUploadCallBack;
            filesPathMap = new HashMap<>();
        }

        public void start() {
            if (uploadFiles.size() > 0) {
                startUpload(uploadType, uploadFiles.get(0));
            }
        }

        private void failedCallBack() {
            if (qiNiuUploadCallBack != null) {
                qiNiuUploadCallBack.onUploadFailed();
            }
        }

        private void startUpload(final int uploadType, final String localFilePath) {
            Map<String, String> paramsMap = new HashMap<>();

            String bucket = "";
            String titleName = "";

            if (uploadType == TYPE_AUDIO) {

                bucket = TYPE_AUDIO_BUCKET;
                titleName = "audio_";
            } else if (uploadType == TYPE_BUG) {

                bucket = TYPE_BUG_BUCKET;

            } else if (uploadType == TYPE_IMAGE) {

                bucket = TYPE_IMAGE_BUCKET;
                titleName = "b_head_";
            }

            final String key = titleName + DataManager.getMemberid() + "_" + System.currentTimeMillis()
                    + (uploadType == TYPE_IMAGE ? ".jpeg" : uploadType == TYPE_AUDIO ? ".wav" : "");

            paramsMap.put("bucket", bucket);
            paramsMap.put("key", key);
            paramsMap.put("member_id", DataManager.getMemberid());
            getTokenDisposable = mHttpHelper.createBaseApi()
                    .executeGet(qiniu_token_url, paramsMap)
                    .compose(RxUtil.<JsonObject>rxSchedulerHelper())
                    .compose(RxUtil.<JsonObject, ApiResponse<JsonObject>>rxGsonFormat(new TypeToken<ApiResponse<JsonObject>>() {
                    }))
                    .subscribe(new Consumer<ApiResponse<JsonObject>>() {
                        @Override
                        public void accept(ApiResponse<JsonObject> response) throws Exception {
                            if (response.isSuccess()) {
                                JsonObject results = response.data;
                                if (results != null && results.get("token") != null) {
                                    String token = results.get("token").getAsString();

                                    uploadManager.put(localFilePath, key, token, new UpCompletionHandler() {
                                        @Override
                                        public void complete(String key, ResponseInfo info, JSONObject res) {
                                            //res包含hash、key等信息，具体字段取决于上传策略的设置
                                            if (info.isOK()) {
                                                countDownLatch.countDown();
//                            String qiniuPath = uploadType == TYPE_IMAGE ? key : info.path;
                                                filesPathMap.put(localFilePath, key);
                                                if (qiNiuUploadCallBack != null) {
                                                    qiNiuUploadCallBack.onUploadFileCountChange(uploadFiles.size(), countDownLatch.getCount());
                                                }
                                                if (countDownLatch.getCount() == 0) {
                                                    if (qiNiuUploadCallBack != null) {
                                                        qiNiuUploadCallBack.onUploadFinish(filesPathMap);
                                                    }
                                                } else {
                                                    startUpload(uploadType, uploadFiles.get((int) (uploadFiles.size() - countDownLatch.getCount())));
                                                }
                                            } else {
                                                if (qiNiuUploadCallBack != null) {
                                                    qiNiuUploadCallBack.onUploadFailed();
                                                }
                                            }
                                        }
                                    }, new UploadOptions(null, null, false, null, new UpCancellationSignal() {
                                        public boolean isCancelled() {
                                            return isCancelled;
                                        }
                                    }));

                                }

                            } else {
                                failedCallBack();
                            }
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) throws Exception {
                            failedCallBack();
                        }
                    });
        }

        public boolean cancelTask() {
            isCancelled = true;
            if (getTokenDisposable != null && getTokenDisposable.isDisposed()) {
                getTokenDisposable.dispose();
            }
            return true;
        }

    }

    public interface QiNiuUploadCallBack {
        void onUploadFileCountChange(long totalCount, long remainCount);

        void onUploadFinish(HashMap<String, String> filesPathMap);

        void onUploadFailed();
    }

    public class UploadInfo {
        public List<String> uploadFiles;
        public int uploadType;

        public UploadInfo(List<String> uploadFiles, int uploadType) {
            this.uploadFiles = uploadFiles;
            this.uploadType = uploadType;
        }
    }
}
