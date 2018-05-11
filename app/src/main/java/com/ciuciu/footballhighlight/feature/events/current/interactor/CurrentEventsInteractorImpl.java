package com.ciuciu.footballhighlight.feature.events.current.interactor;

import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.common.interactor.BaseInteractor;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;
import com.ciuciu.footballhighlight.model.view.Match;
import com.ciuciu.footballhighlight.repository.CurrentEventsRepository;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CurrentEventsInteractorImpl extends BaseInteractor implements CurrentEventsInteractor {

    private CurrentEventsRepository mCurrentEventsRepository;

    @Inject
    public CurrentEventsInteractorImpl(PreferencesHelper preferencesHelper, DiskHelper diskHelper, LiveScoreApi liveScoreApi,
                                       CurrentEventsRepository currentEventsRepository) {
        super(preferencesHelper, diskHelper, liveScoreApi);
        mCurrentEventsRepository = currentEventsRepository;
    }

    @Override
    public Observable<Response<List<Match>>> getEvents(String from, String to, @Nullable String countryId, @Nullable String leagueId, @Nullable String matchId) {
        return mCurrentEventsRepository.getCurrentEvents(from, to, countryId, leagueId, matchId);
    }
}
