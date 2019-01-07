package com.abctime.lib_common.data.network;

import com.abctime.lib_common.utils.GsonUtil;
import com.google.gson.reflect.TypeToken;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {

    /**
     * 统一线程处理
     *
     * @param <T>
     * @return
     */
    public static <T> FlowableTransformer<T, T> rxSchedulerHelper() {    //compose简化线程

        return new FlowableTransformer<T, T>() {
            @Override
            public Flowable<T> apply(Flowable<T> observable) {
                return observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    /**
     * 处理Json解析
     */
    public static <F, T> FlowableTransformer<F, T> rxGsonFormat(final TypeToken<T> typeToken) {

        return new FlowableTransformer<F, T>() {
            @Override
            public Flowable<T> apply(Flowable<F> observable) {
                return observable.map(new Function<F, T>() {
                    @Override
                    public T apply(F f) throws Exception {
                        return GsonUtil.GsonToType(GsonUtil.GsonString(f), typeToken);
                    }
                });
            }
        };
    }


    /**
     * 生成Flowable
     *
     * @param <T>
     * @return
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> emitter) throws Exception {
                try {
                    emitter.onNext(t);
                    emitter.onComplete();
                } catch (Exception e) {
                    emitter.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }
}
