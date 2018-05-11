package com.ciuciu.footballhighlight.feature.events.current.interactor;

import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.List;

import io.reactivex.Observable;

public interface CurrentEventsInteractor {

    Observable<Response<List<Match>>> getEvents(String from, String to,
                                                @Nullable String countryId,
                                                @Nullable String leagueId,
                                                @Nullable String matchId);
}
