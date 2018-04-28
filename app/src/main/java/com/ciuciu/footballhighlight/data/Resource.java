package com.ciuciu.footballhighlight.data;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.ApplicationException;

import static com.ciuciu.footballhighlight.data.Resource.Status.ERROR;
import static com.ciuciu.footballhighlight.data.Resource.Status.LOADING;
import static com.ciuciu.footballhighlight.data.Resource.Status.SUCCESS;

/**
 * A generic class that holds a value with its loading status.
 *
 * @param <T>
 */
public class Resource<T> {
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
    private final ApplicationException exception;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable ApplicationException exception) {
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

    public ApplicationException getException() {
        return exception;
    }

    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(ApplicationException exception, @Nullable T data) {
        return new Resource<>(ERROR, data, exception);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }
}
