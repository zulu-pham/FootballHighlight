package com.ciuciu.footballhighlight.common.interactor;

import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;

import javax.inject.Inject;

public class BaseInteractor implements MvvmInteractor {

    private final PreferencesHelper mPreferencesHelper;
    private final DiskHelper mDiskHelper;
    private final LiveScoreApi mLiveScoreApi;

    @Inject
    public BaseInteractor(PreferencesHelper preferencesHelper,
                          DiskHelper diskHelper,
                          LiveScoreApi liveScoreApi) {
        mPreferencesHelper = preferencesHelper;
        mDiskHelper = diskHelper;
        mLiveScoreApi = liveScoreApi;
    }

    @Override
    public PreferencesHelper getPreferencesHelper() {
        return mPreferencesHelper;
    }

    @Override
    public DiskHelper getDiscHelper() {
        return mDiskHelper;
    }

    @Override
    public LiveScoreApi getLiveScoreApi() {
        return mLiveScoreApi;
    }
}
