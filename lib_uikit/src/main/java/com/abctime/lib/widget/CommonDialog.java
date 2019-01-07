package com.abctime.lib.widget;

import android.content.Context;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.abctime.lib.uikit.R;
import com.abctime.lib_common.utils.font.FontUtils;
import com.abctime.lib_common.utils.sys.DensityUtils;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * author:Created by xmren on 2018/7/9.
 * email :renxiaomin@100tal.com
 */

public class CommonDialog extends BaseDialog {
    private static final float WIDTH = 0.5F;
    private static final float HEIGHT = 0.8F;
    private FrameLayout mFlTop;
    private ImageView mIvCloesed;
    private View mLlContainer;
    private TextView mTvTitle;
    private TextView mTvContent;
    private LinearLayout mLlBottom;
    private View progressBar;
//    private int mLayoutResId = ;

    private float dialogWidth = WIDTH;
    private float dialogHeight = HEIGHT;
    private int dialogAnim = R.style.dialogWindowAnim;

    public CommonDialog(@NonNull Context context) {
        super(context, R.style.dialogStyle);
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_common_layout;
    }

    public void setDialogWidth(float dialogWidth) {
        this.dialogWidth = dialogWidth;
    }

    public void setDialogHeight(float dialogHeight) {
        this.dialogHeight = dialogHeight;
    }


    public void setDialogAnim(int dialogAnim) {
        this.dialogAnim = dialogAnim;
    }

    @Override
    public float getLayoutWidth() {
        return dialogWidth;
    }

    @Override
    public int getLayoutPosition() {
        return Gravity.CENTER;
    }

    @Override
    public int getAnimations() {
        return dialogAnim;
    }

    @Override
    public void initView(View dialogView) {
        mFlTop = dialogView.findViewById(R.id.ll_dialog_top);
        mIvCloesed = dialogView.findViewById(R.id.iv_dialog_top_cloesed);
        mIvCloesed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        mLlContainer = dialogView.findViewById(R.id.ll_dialog_contanier);
        mTvTitle = dialogView.findViewById(R.id.tv_dialog_title);
        mTvContent = dialogView.findViewById(R.id.tv_dialog_content);
        mLlBottom = dialogView.findViewById(R.id.ll_dialog_bottom_container);
        progressBar = dialogView.findViewById(R.id.ll_dialog_progressbar);
    }

    @Override
    public float getLayoutHeight() {
        return dialogHeight;
    }

    public void setDialogTopView(View view, TopViewParam topViewLP) {
        if (view == null) {
            return;
        }
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        if (topViewLP != null) {
            RelativeLayout.LayoutParams topLP = (RelativeLayout.LayoutParams) mFlTop.getLayoutParams();
            topLP.topMargin = topViewLP.topMargin > Integer.MIN_VALUE ? topViewLP.topMargin : topLP.topMargin;
            topLP.bottomMargin = topViewLP.bottomMargin > Integer.MIN_VALUE ? topViewLP.bottomMargin : topLP.bottomMargin;
            layoutParams.leftMargin = topViewLP.leftMargin > Integer.MIN_VALUE ? topViewLP.leftMargin : layoutParams.leftMargin;
            layoutParams.rightMargin = topViewLP.rightMargin > Integer.MIN_VALUE ? topViewLP.rightMargin : layoutParams.rightMargin;
            layoutParams.width = topViewLP.width > Integer.MIN_VALUE ? topViewLP.width : layoutParams.width;
            layoutParams.height = topViewLP.height > Integer.MIN_VALUE ? topViewLP.height : layoutParams.height;
        }
        mFlTop.addView(view, layoutParams);
    }

    public void setBottomBtns(Map<ButtonItem, View.OnClickListener> bottomBtns) {
        if (mLlBottom != null) {
            Iterator<ButtonItem> iterator = bottomBtns.keySet().iterator();
            boolean moreThanOne = false;
            if (bottomBtns.size() > 1)
                moreThanOne = true;
            while (iterator.hasNext()) {
                ButtonItem item = iterator.next();
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                View textView = generateBottomBtn(item, bottomBtns.get(item));
                if (iterator.hasNext())
                    params.rightMargin = DensityUtils.dp2px(getContext(), 10);
                if (moreThanOne)
                    params.weight = 1;
                mLlBottom.addView(textView, params);
            }
            if (bottomBtns.isEmpty()) {
                mLlBottom.setVisibility(View.GONE);
            } else {
                mLlBottom.setVisibility(View.VISIBLE);
            }
        }
    }

    protected TextView generateBottomBtn(ButtonItem item, View.OnClickListener onClickListener) {
        TextView textView = new TextView(getContext());
        textView.setShadowLayer(3, 0, 5, ContextCompat.getColor(getContext(), R.color.clr_88000000));
        textView.setSingleLine();
        if (TextUtils.isEmpty(item.text)) {
            if (item.img > 0)
                textView.setBackgroundResource(item.img);
        } else {
            setupTextStyle(item, textView);
            textView.setGravity(Gravity.CENTER);
        }
        textView.setOnClickListener(onClickListener);
        return textView;
    }

    private void setupTextStyle(TextItem item, TextView textView) {
        textView.setText(item.text);
        if (item.text instanceof SpannableString)
            textView.setMovementMethod(new LinkMovementMethod());
        if (item.bgImg > 0)
            textView.setBackgroundResource(item.bgImg);
        if (item.textSize > 0)
            textView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, item.textSize);
        if (item.textColor > 0)
            textView.setTextColor(ContextCompat.getColor(getContext(), item.textColor));
        if (!TextUtils.isEmpty(item.textFont))
            FontUtils.changeFontTypeface(textView, item.textFont);
        if (item.lineSpacingMultiplier > 0)
            textView.setLineSpacing(textView.getLineSpacingExtra(), item.lineSpacingMultiplier);
        if (item.maxLine != -1) {
            textView.setMaxLines(item.maxLine);
        } else {
            textView.setPadding(0, 0, 0, DensityUtils.dp2px(getContext(), 30));
            textView.setMovementMethod(ScrollingMovementMethod.getInstance());
        }
    }

    public void setTitle(TextItem title) {
        if (mTvTitle != null) {
            if (title == null) {
                mTvTitle.setVisibility(View.GONE);
            } else {
                mTvTitle.setVisibility(View.VISIBLE);
                setupTextStyle(title, mTvTitle);
            }
        }
    }

    public void setContent(TextItem content) {
        if (mTvContent != null) {
            if (content == null) {
                mTvContent.setVisibility(View.GONE);
            } else {
                mTvContent.setVisibility(View.VISIBLE);
                setupTextStyle(content, mTvContent);
                if (content.viewParam != null) {
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) mTvContent.getLayoutParams();
                    layoutParams.topMargin = content.viewParam.topMargin;
                }
            }
        }
    }

    public void setBackground(@DrawableRes int resId) {
        mLlContainer.setBackgroundResource(resId);
    }

    public void showProgressBar() {
        mLlBottom.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
    }

    public static class Builder extends BaseDialog.Builder {

        private float width = WIDTH;
        private float height = HEIGHT;
        private Map<ButtonItem, View.OnClickListener> bottomBtns;
        private TextItem dialogTitle;
        private TextItem dialogContent;
        private int dialogBgResId;
        //        private int layoutResId;
        private View dialogTopView;
        private boolean dialogTopClosedImageShow;
        private boolean cancelable = true;
        private boolean outsideDismiss;
        private int dialogAnim;
        private TopViewParam topViewLP;

        public Builder() {
            bottomBtns = new LinkedHashMap<>();
        }

        public Builder setWidth(float width) {
            this.width = width;
            return this;
        }

        public Builder setHeight(float height) {
            this.height = height;
            return this;
        }

        public Builder setTitle(String title) {
            this.dialogTitle = new TextItem(title);
            return this;
        }

        public Builder setTitle(TextItem title) {
            this.dialogTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            this.dialogContent = new TextItem(content);
            return this;
        }

        public Builder setContent(TextItem content) {
            this.dialogContent = content;
            return this;
        }

        public Builder setDialogBg(@DrawableRes int resId) {
            this.dialogBgResId = resId;
            return this;
        }

        public Builder setOutSideDismiss(boolean outSideDismiss) {
            this.outsideDismiss = outSideDismiss;
            return this;
        }

        public Builder setDialogAnim(int dialogAnim) {
            this.dialogAnim = dialogAnim;
            return this;
        }

        public Builder addTopView(View topView, TopViewParam params) {
            this.dialogTopView = topView;
            this.topViewLP = params;
            return this;
        }

        public Builder isShowTopCloseIcon(boolean isShow) {
            this.dialogTopClosedImageShow = isShow;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            this.cancelable = cancelable;
            return this;
        }

//        public Builder setLayoutResId(int layoutResId) {
//            this.layoutResId = layoutResId;
//            return this;
//        }

        public Builder addBottomBtn(ButtonItem buttonItem, View.OnClickListener onClickListener) {
            bottomBtns.put(buttonItem, onClickListener);
            return this;
        }

        @Override
        public BaseDialog create(Context context) {
            CommonDialog commonDialog = new CommonDialog(context);
            commonDialog.setDialogWidth(width);
            commonDialog.setDialogHeight(height);
            commonDialog.setContent(dialogContent);
            commonDialog.setTitle(dialogTitle);
            commonDialog.setBackground(dialogBgResId);
            commonDialog.setDialogTopView(dialogTopView, topViewLP);
            commonDialog.showCloseIv(dialogTopClosedImageShow);
            commonDialog.setBottomBtns(bottomBtns);
            commonDialog.setCanceledOnTouchOutside(outsideDismiss);
            commonDialog.setDialogAnim(dialogAnim);
            commonDialog.setCancelable(cancelable);
//            commonDialog.mLayoutResId = layoutResId;
            commonDialog.create();
            return commonDialog;
        }

    }

    private void showCloseIv(boolean dialogTopClosedImageShow) {
        mIvCloesed.setVisibility(dialogTopClosedImageShow ? View.VISIBLE : View.GONE);
    }

    public static class TextItem {

        public CharSequence text;
        @FontUtils.FontType
        public String textFont = FontUtils.FONT_TYPE_FZRUISYJW_ZHUN;
        @ColorRes
        public int textColor = R.color.clr_953821;
        public int textSize = 15;
        public int bgImg;
        public float lineSpacingMultiplier = 1.2f;
        public int maxLine = 3;
        public TopViewParam viewParam;

        public TextItem(CharSequence text) {
            this.text = text;
        }
    }

    public static class ButtonItem extends TextItem {

        @DrawableRes
        public int img;

        public ButtonItem(CharSequence text) {
            super(text);
            textColor = R.color.clr_ffffff;
            textSize = 20;
            bgImg = R.drawable.ic_ui_button;
            lineSpacingMultiplier = 1;
        }
    }

    public static class TopViewParam {
        public int leftMargin = Integer.MIN_VALUE;
        public int topMargin = Integer.MIN_VALUE;
        public int rightMargin = Integer.MIN_VALUE;
        public int bottomMargin = Integer.MIN_VALUE;
        public int width = Integer.MIN_VALUE;
        public int height = Integer.MIN_VALUE;
    }
}
