package com.ciuciu.footballhighlight.data.network;

import android.support.annotation.NonNull;

import com.google.gson.Gson;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private final RxJava2CallAdapterFactory original;

    private RxErrorHandlingCallAdapterFactory() {
        original = RxJava2CallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        Class<?> rawType = getRawType(returnType);
        ObservableType observableType = ObservableType.findByClass(rawType);
        return new RxJavaCallAdapterWrapper(original.get(returnType, annotations, retrofit), observableType);
    }

    private static class RxJavaCallAdapterWrapper<R> implements CallAdapter<R, Object> {
        private final Gson gson = new Gson();
        private final CallAdapter<R, ?> original;
        private final ObservableType observableType;

        RxJavaCallAdapterWrapper(CallAdapter<R, ?> original, ObservableType observableType) {
            this.original = original;
            this.observableType = observableType;
        }

        @Override
        public Type responseType() {
            return original.responseType();
        }

        @Override
        public Object adapt(Call<R> call) {
            original.adapt(call);
            switch (observableType) {
                case FLOWABLE: {
                    return ((Flowable<?>) original.adapt(call))
                            .onErrorResumeNext(throwable -> {
                                if (throwable instanceof HttpException) {
                                    return Flowable.just(fromJson(throwable));
                                }
                                return Flowable.error(throwable);
                            });
                }
                case SINGLE: {
                    return ((Single<?>) original.adapt(call))
                            .onErrorResumeNext(throwable -> {
                                if (throwable instanceof HttpException) {
                                    return Single.just(fromJson(throwable));
                                }
                                return Single.error(throwable);
                            });
                }
                case MAYBE: {
                    return ((Maybe<?>) original.adapt(call))
                            .onErrorResumeNext(throwable -> {
                                if (throwable instanceof HttpException) {
                                    return Maybe.just(fromJson(throwable));
                                }
                                return Maybe.error(throwable);
                            });
                }
                case COMPLETABLE: {
                    return ((Completable) original.adapt(call))
                            .onErrorResumeNext(throwable -> {
                                if (throwable instanceof HttpException) {
                                    return Completable.complete();
                                }
                                return Completable.error(throwable);
                            });
                }
                case OBSERVABLE: {
                    return ((Observable<?>) original.adapt(call))
                            .onErrorResumeNext(throwable -> {
                                if (throwable instanceof HttpException) {
                                    return Observable.just(fromJson(throwable));
                                }
                                return Observable.just(null);
                                //return Observable.error(throwable);
                            });
                }
                default: {
                    throw new IllegalStateException("compatible observable not found.");
                }
            }
        }

        private <T> T fromJson(@NonNull Throwable throwable) {
            HttpException httpException = (HttpException) throwable;
            ResponseBody responseBody = httpException.response().errorBody();
            return gson.fromJson(responseBody.charStream(), responseType());
        }
    }

    /**
     * Enum representing the type of DataSource
     */
    private enum ObservableType {
        FLOWABLE(Flowable.class),
        SINGLE(Single.class),
        MAYBE(Maybe.class),
        COMPLETABLE(Completable.class),
        OBSERVABLE(Observable.class);

        private final Class clazz;

        ObservableType(Class clazz) {
            this.clazz = clazz;
        }

        static ObservableType findByClass(Class clazz) {
            for (ObservableType observableType : values()) {
                if (observableType.clazz == clazz) {
                    return observableType;
                }
            }
            throw new IllegalArgumentException("no such type: " + clazz.getCanonicalName());
        }
    }
}
