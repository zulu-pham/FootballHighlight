package com.ciuciu.footballhighlight.common.interactor;

import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;

public interface MvvmInteractor {

    PreferencesHelper getPreferencesHelper();

    DiskHelper getDiscHelper();

    LiveScoreApi getLiveScoreApi();

}
