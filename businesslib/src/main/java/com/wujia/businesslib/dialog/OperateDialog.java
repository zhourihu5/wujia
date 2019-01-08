package com.wujia.businesslib.dialog;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wujia.businesslib.R;
import com.wujia.lib.imageloader.ImageLoaderManager;
import com.wujia.lib.imageloader.listener.SourceReadyListener;
import com.wujia.lib.widget.BaseDialog;
import com.wujia.lib.widget.util.ToastUtil;
import com.wujia.lib_common.utils.FileUtils;
import com.wujia.lib_common.utils.Permission.PermissionCallBack;
import com.wujia.lib_common.utils.Permission.PermissionUtils;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Author: created by shenbingkai on 2018/11/19 14 23
 * Email:  shenbingkai@gamil.com
 * Description: 运营弹窗
 */
public class OperateDialog extends BaseDialog {
    private TextView tvContent, tvTitle, tvSubTitle;
    private ImageView iVContent, btnColose;
    private TextView btnSave;

    private boolean savaAble;//能否点击保存
    private boolean attentionAble;//trie关注，，false保存图片

    public OperateDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_operate;
    }

    @Override
    public float getLayoutWidth() {
        return 0.5F;
    }

    @Override
    public int getLayoutPosition() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimations() {
        return R.style.dialogWindowAnim;
    }

    @Override
    public void initView(View dialogView) {
        tvContent = dialogView.findViewById(R.id.op_dialog_content);
        tvTitle = dialogView.findViewById(R.id.op_dialog_title);
        tvSubTitle = dialogView.findViewById(R.id.op_dialog_title_sub);
        iVContent = dialogView.findViewById(R.id.op_dialog_img);
        btnSave = dialogView.findViewById(R.id.op_dialog_save);
        btnColose = dialogView.findViewById(R.id.op_dialog_close);

        btnColose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public OperateDialog setContent(final Object... params) {
        if (null != params && params.length > 0) {

            if (null != params[0]) {
                tvContent.setText((String) params[0]);
            }
            if (null != params[1]) {
                ImageLoaderManager.getInstance().loadImageWithPrepareCall((String) params[1], iVContent, R.mipmap.bg_image_default, new SourceReadyListener() {
                    @Override
                    public void onResourceReady(int i, int i1) {
                        savaAble = true;
                    }
                });
            }
            if (null != params[2]) {
                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (attentionAble) {
                            dismiss();
                            ((View.OnClickListener) params[2]).onClick(null);
                        } else {
                            if (savaAble) {
                                savaAble = false;
                                PermissionUtils.checkPermission((Activity) context, Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallBack() {
                                    @Override
                                    public void permissionGranted() {
                                        boolean result = save(loadBitmapFromView(iVContent));
                                        if (result) {
                                            iVContent.setVisibility(View.GONE);
                                            tvSubTitle.setVisibility(View.VISIBLE);
                                            tvTitle.setText("保存成功");
                                            tvContent.setText("在微信-扫一扫-右上角相册中，打开二维码");
                                            tvContent.setGravity(Gravity.CENTER);
                                            btnSave.setText("去关注");
                                            attentionAble = true;
                                        } else {
                                            savaAble = true;
                                        }
                                    }

                                    @Override
                                    public void permissionDenied() {
                                        savaAble = true;
                                        ToastUtil.shortShow(context, "无法获取SD卡权限");
                                    }

                                    @Override
                                    public void permissionDeniedWithNeverAsk() {
                                        savaAble = true;
                                        ToastUtil.shortShow(context, "无法获取SD卡权限");
                                    }
                                });
                            }
                        }
                    }
                });
            }
        }

        return this;
    }

    @Override
    public float getLayoutHeight() {
        return 0.7F;
    }


    public BaseDialog build() {
        this.create();
        return this;
    }

    private Bitmap loadBitmapFromView(View v) {
        if (v == null) {
            return null;
        }
        Bitmap screenshot;
        screenshot = Bitmap.createBitmap(v.getWidth(), v.getHeight(), Bitmap.Config.RGB_565);
        Canvas c = new Canvas(screenshot);
        c.translate(-v.getScrollX(), -v.getScrollY());
        v.draw(c);
        return screenshot;
    }

    /**
     * 文件 路径
     *
     * @return
     */
    private File getImageFile() {

        String root = FileUtils.getExternalStorageDirectory();

        return new File(root, "ABC_Reading_Wechat.jpg");
    }

    private boolean save(Bitmap bmp) {
        if (null == bmp)
            return false;
        try {

            File file = getImageFile();

            FileOutputStream fos = new FileOutputStream(file);

            //通过io流的方式来压缩保存图片
            boolean isSuccess = bmp.compress(Bitmap.CompressFormat.JPEG, 90, fos);

            fos.flush();

            fos.close();

            // 保存图片后发送广播通知更新数据库
            Uri uri = Uri.fromFile(file);

            context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

            bmp.recycle();
            return isSuccess;

        } catch (Exception e) {

            e.printStackTrace();
        }

        return false;
    }
}
