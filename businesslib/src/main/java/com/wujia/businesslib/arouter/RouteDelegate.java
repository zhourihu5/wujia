package com.wujia.businesslib.arouter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.util.SparseArray;

import com.alibaba.android.arouter.facade.service.SerializationService;
import com.alibaba.android.arouter.launcher.ARouter;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Author: shenbingkai
 * CreateDate: 2019-04-06 00:51
 * Description:
 */

public class RouteDelegate {

    public static Jumper webView(String url, String title) {
        return build(ARouterURI.webURI.MAIN).withString("TAG_URL", url).withString("TITLE_INTENT", title);
    }

    public static Jumper login() {
        return build(ARouterURI.LoginURI.LOGIN);
    }

    public static Jumper payment() {
        return build(ARouterURI.PaymentURI.MAIN);
    }

    public static Jumper build(String uri) {
        return new Jumper(uri);
    }

    public static void jump(String uri) {
        build(uri).jump();
    }

    public static class Jumper {

        private String uri;
        private Bundle mBundle;

        private Jumper(String uri) {
            this.uri = uri;
            mBundle = new Bundle();
        }

        public Jumper setBundle(Bundle Jumper) {
            mBundle = new Bundle(Jumper);
            return this;
        }

        public Jumper withObject(@Nullable String key, @Nullable Object value) {
            SerializationService serializationService = ARouter.getInstance().navigation(SerializationService.class);
            mBundle.putString(key, serializationService.object2Json(value));
            return this;
        }

        public Jumper withString(@Nullable String key, @Nullable String value) {
            mBundle.putString(key, value);
            return this;
        }

        public Jumper withBoolean(@Nullable String key, boolean value) {
            mBundle.putBoolean(key, value);
            return this;
        }

        public Jumper withShort(@Nullable String key, short value) {
            mBundle.putShort(key, value);
            return this;
        }

        public Jumper withInt(@Nullable String key, int value) {
            mBundle.putInt(key, value);
            return this;
        }

        public Jumper withLong(@Nullable String key, long value) {
            mBundle.putLong(key, value);
            return this;
        }

        public Jumper withDouble(@Nullable String key, double value) {
            mBundle.putDouble(key, value);
            return this;
        }

        public Jumper withByte(@Nullable String key, byte value) {
            mBundle.putByte(key, value);
            return this;
        }

        public Jumper withChar(@Nullable String key, char value) {
            mBundle.putChar(key, value);
            return this;
        }

        public Jumper withFloat(@Nullable String key, float value) {
            mBundle.putFloat(key, value);
            return this;
        }

        public Jumper withCharSequence(@Nullable String key, @Nullable CharSequence value) {
            mBundle.putCharSequence(key, value);
            return this;
        }

        public Jumper withParcelable(@Nullable String key, @Nullable Parcelable value) {
            mBundle.putParcelable(key, value);
            return this;
        }

        public Jumper withParcelableArray(@Nullable String key, @Nullable Parcelable[] value) {
            mBundle.putParcelableArray(key, value);
            return this;
        }

        public Jumper withParcelableArrayList(@Nullable String key, @Nullable ArrayList<? extends Parcelable> value) {
            mBundle.putParcelableArrayList(key, value);
            return this;
        }

        public Jumper withSparseParcelableArray(@Nullable String key, @Nullable SparseArray<? extends Parcelable> value) {
            mBundle.putSparseParcelableArray(key, value);
            return this;
        }

        public Jumper withIntegerArrayList(@Nullable String key, @Nullable ArrayList<Integer> value) {
            mBundle.putIntegerArrayList(key, value);
            return this;
        }

        public Jumper withStringArrayList(@Nullable String key, @Nullable ArrayList<String> value) {
            mBundle.putStringArrayList(key, value);
            return this;
        }

        public Jumper withCharSequenceArrayList(@Nullable String key, @Nullable ArrayList<CharSequence> value) {
            mBundle.putCharSequenceArrayList(key, value);
            return this;
        }

        public Jumper withSerializable(@Nullable String key, @Nullable Serializable value) {
            mBundle.putSerializable(key, value);
            return this;
        }

        public Jumper withByteArray(@Nullable String key, @Nullable byte[] value) {
            mBundle.putByteArray(key, value);
            return this;
        }

        public Jumper withShortArray(@Nullable String key, @Nullable short[] value) {
            mBundle.putShortArray(key, value);
            return this;
        }

        public Jumper withCharArray(@Nullable String key, @Nullable char[] value) {
            mBundle.putCharArray(key, value);
            return this;
        }

        public Jumper withFloatArray(@Nullable String key, @Nullable float[] value) {
            mBundle.putFloatArray(key, value);
            return this;
        }

        public Jumper withCharSequenceArray(@Nullable String key, @Nullable CharSequence[] value) {
            mBundle.putCharSequenceArray(key, value);
            return this;
        }

        public void jump() {
            ARouter.getInstance().build(uri).with(mBundle).navigation();
        }

        public void jump(Activity activity, int requestCode) {
            ARouter.getInstance().build(uri).with(mBundle).navigation(activity, requestCode);
        }
    }

}
