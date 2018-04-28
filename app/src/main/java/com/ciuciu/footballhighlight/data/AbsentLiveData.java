package com.ciuciu.footballhighlight.data;

import android.arch.lifecycle.LiveData;

/**
 * A LiveData class that has {@code null} value.
 */
public class AbsentLiveData extends LiveData {
    private AbsentLiveData() {
        postValue(this);
    }

    public static <T> LiveData<T> create() {
        //noinspection unchecked
        return new AbsentLiveData();
    }
}
