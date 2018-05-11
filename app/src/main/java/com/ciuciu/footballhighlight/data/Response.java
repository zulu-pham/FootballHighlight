package com.ciuciu.footballhighlight.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.ciuciu.footballhighlight.data.Response.Status.ERROR;
import static com.ciuciu.footballhighlight.data.Response.Status.LOADING;
import static com.ciuciu.footballhighlight.data.Response.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Response<T> {
    /**
     * Status of a resource that is provided to the UI.
     * <p>
     * These are usually created by the Repository classes where they return
     * {@code LiveData<Resource<T>>} to pass back the latest data to the UI with its fetch status.
     */
    public enum Status {
        SUCCESS, ERROR, LOADING
    }

    private final Status status;
    private final T data;
    private final Throwable exception;

    private Response(@NonNull Status status, @Nullable T data, @Nullable Throwable exception) {
        this.status = status;
        this.data = data;
        this.exception = exception;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }

    public Throwable getException() {
        return exception;
    }

    public static <T> Response<T> success(@NonNull T data) {
        return new Response<>(SUCCESS, data, null);
    }

    public static <T> Response<T> error(Throwable exception, @Nullable T data) {
        return new Response<>(ERROR, data, exception);
    }

    public static <T> Response<T> loading(@Nullable T data) {
        return new Response<>(LOADING, data, null);
    }
}
