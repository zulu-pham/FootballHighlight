package com.ciuciu.footballhighlight.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.NetworkBoundResource;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CurrentEventsRepository {

    private LiveScoreApi mLiveScoreApi;

    @Inject
    public CurrentEventsRepository(LiveScoreApi liveScoreApi) {
        mLiveScoreApi = liveScoreApi;
    }

    public Observable<ApiResponseBody<List<Match>>> getCurrentEvents(String from, String to,
                                                                     @Nullable String countryId,
                                                                     @Nullable String leagueId,
                                                                     @Nullable String matchId) {
        return new NetworkBoundResource<List<Match>, ApiResponseBody<List<MatchEntity>>>() {
            @NonNull
            @Override
            protected Observable<ApiResponseBody<List<MatchEntity>>> createCall() {
                return mLiveScoreApi.getEvents(from, to, countryId, leagueId, matchId);
            }

            @NonNull
            @Override
            protected ApiResponseBody<List<Match>> transformData(ApiResponseBody<List<MatchEntity>> response) {
                return new ApiResponseBody<>(response.getStatusCode(), parseData(response));
            }
        }.asObservable();
    }

    private List<Match> parseData(ApiResponseBody<List<MatchEntity>> response) {
        List<Match> matchList = new ArrayList<>();
        if (response.getData() != null) {
            for (MatchEntity entity : response.getData()) {
                Match match = new Match(entity);
                if (match.getMatchDate() != null) {
                    matchList.add(match);
                }
            }
            Collections.sort(matchList, (match1, match2) -> match1.getMatchDate().compareTo(match2.getMatchDate()));
            return matchList;
        }
        return matchList;
    }
}
