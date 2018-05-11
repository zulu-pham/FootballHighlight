package com.ciuciu.footballhighlight.repository;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ciuciu.footballhighlight.data.NetworkBoundResource;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CurrentEventsRepository {

    private LiveScoreApi mLiveScoreApi;

    @Inject
    public CurrentEventsRepository(LiveScoreApi liveScoreApi) {
        mLiveScoreApi = liveScoreApi;
    }


    public Observable<Response<List<Match>>> getCurrentEvents(String from, String to,
                                                              @Nullable String countryId,
                                                              @Nullable String leagueId,
                                                              @Nullable String matchId) {

        return new NetworkBoundResource<List<Match>, List<MatchEntity>>() {

            @NonNull
            @Override
            protected Observable<List<MatchEntity>> createCall() {
                return mLiveScoreApi.observableEvents(from, to, countryId, leagueId, matchId);
            }

            @NonNull
            @Override
            protected List<Match> transformData(List<MatchEntity> response) {
                return parseMatchEntityList(response);
            }
        }.asObservable();
    }

    public Observable<List<Match>> getCurrentEvent(String from, String to,
                                                   @Nullable String countryId,
                                                   @Nullable String leagueId,
                                                   @Nullable String matchId) {

        return mLiveScoreApi.observableEvents(from, to, countryId, leagueId, matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(matchEntities -> parseMatchEntityList(matchEntities))
                .onErrorReturn(throwable -> {
                    Log.d("CurrentEventsRepository", "onErrorReturn");
                    return new ArrayList<>();
                })
                ;
    }

    private List<Match> parseMatchEntityList(List<MatchEntity> entityList) {
        List<Match> matchList = new ArrayList<>();
        for (MatchEntity entity : entityList) {
            Match match = new Match(entity);
            if (match.getMatchDate() != null) {
                matchList.add(match);
            }
        }
        Collections.sort(matchList, (match1, match2) -> match1.getMatchDate().compareTo(match2.getMatchDate()));
        return matchList;
    }
}
