package com.jingxi.smartlife.pad.mvp.setting.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.liulishuo.okdownload.DownloadTask;
import com.wujia.businesslib.DownloadUtil;
import com.wujia.businesslib.TitleFragment;
import com.wujia.businesslib.listener.DownloadListener;
import com.jingxi.smartlife.pad.R;
import com.jingxi.smartlife.pad.mvp.setting.data.VersionBean;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.utils.AppUtil;
import com.wujia.lib_common.utils.LogUtil;

import java.math.BigDecimal;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * author ：shenbingkai@163.com
 * date ：2019-01-26
 * description ：检查更新
 */
public class UpdateFragment extends TitleFragment {

    @BindView(R.id.tv_version)
    TextView tvVersion;
    @BindView(R.id.tv_version_desc)
    TextView tvVersionDesc;
    @BindView(R.id.btn_update_now)
    TextView btnUpdateNow;
    @BindView(R.id.update_check_layout)
    LinearLayout updateCheckLayout;
    @BindView(R.id.progress_update)
    ProgressBar progressUpdate;
    @BindView(R.id.tv_update_downloaded)
    TextView tvUpdateDownloaded;
    @BindView(R.id.tv_update_apik_count)
    TextView tvUpdateApikCount;
    @BindView(R.id.update_ing_layout)
    LinearLayout updateIngLayout;

    private VersionBean.Version mVersion;
    private DownloadTask mTask;

    public static UpdateFragment newInstance(VersionBean.Version version, String remark) {
        UpdateFragment fragment = new UpdateFragment();
        Bundle args = new Bundle();
        args.putSerializable("version", version);
        args.putString("remark", remark);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected void initEventAndData() {
        super.initEventAndData();
    }

    @Override
    public void onLazyInitView(@Nullable Bundle savedInstanceState) {
        super.onLazyInitView(savedInstanceState);

        mVersion = (VersionBean.Version) getArguments().getSerializable("version");
        String remark = getArguments().getString("remark");
        tvVersionDesc.setText(remark);
        tvVersion.setText(mVersion.version);

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_update;
    }

    @Override
    public int getTitle() {
        return R.string.check_update;
    }

    @OnClick(R.id.btn_update_now)
    public void onViewClicked() {
        updateCheckLayout.setVisibility(View.GONE);
        updateIngLayout.setVisibility(View.VISIBLE);

//        download();

        install();
    }

    private void download() {
        mTask = DownloadUtil.download(mVersion.imageurl, new DownloadListener() {
            @Override
            public void onTaskStart() {

            }

            @Override
            public void onTaskProgress(int percent, long currentOffset, long totalLength) {
                if (null != progressUpdate) {
                    progressUpdate.setProgress(percent);
                }
                if (null != tvUpdateDownloaded) {
                    tvUpdateDownloaded.setText(getFormatSize(currentOffset) + "/" + getFormatSize(totalLength));
                }
            }

            @Override
            public void onTaskComplete(int state, final String filePath) {
                switch (state) {

                    case DownloadUtil.STATE_COMPLETE:
                        LogUtil.i("tvUpdateDownloaded " + filePath);
                        if (null != tvUpdateDownloaded) {
                            tvUpdateDownloaded.setText("正在安装");
                        }
                        Observable.create(new ObservableOnSubscribe<Boolean>() {
                            @Override
                            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                                LogUtil.i("install " + mVersion.packageName);
                                boolean install = AppUtil.install(filePath);
//                                    boolean install = true;
                                emitter.onNext(install);
                            }
                        }).subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Consumer<Boolean>() {
                                    @Override
                                    public void accept(Boolean install) throws Exception {
                                        if (install) {
                                            ToastUtil.showShort(mContext, "安装完成");
                                            //安装成功，本地记录
//                                            ThirdPermissionUtil.requestDefaultPermissions(mVersion.packageName);

                                        } else {
                                            ToastUtil.showShort(mContext, "安装失败");
                                        }
                                    }
                                });
                        break;
                    case DownloadUtil.STATE_CANCELED:
                    case DownloadUtil.STATE_OTHER:
                        break;
                }
            }
        });
    }

    private void install() {
        Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                LogUtil.i("install " + mVersion.packageName);
                boolean install = AppUtil.install("/storage/emulated/0/Android/data/com.jingxi.smartlife.pad/files/Download/apk/e4283230-33a9-47ce-9131-40b819538515.apk");
//                                    boolean install = true;
                emitter.onNext(install);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean install) throws Exception {
                        if (install) {
                            LogUtil.i("install 安装成功" );

                            ToastUtil.showShort(mContext, "安装完成");
                            //安装成功，本地记录
//                                            ThirdPermissionUtil.requestDefaultPermissions(mVersion.packageName);

                        } else {
                            ToastUtil.showShort(mContext, "安装失败");
                            LogUtil.i("install 安装失败" );
                        }
                    }
                });
    }

    /**
     * 格式化单位
     *
     * @param size
     * @return
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "B";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mTask) {
            mTask.cancel();
        }
    }
}
