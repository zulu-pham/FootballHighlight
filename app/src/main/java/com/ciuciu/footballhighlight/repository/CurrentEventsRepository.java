package com.ciuciu.footballhighlight.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ciuciu.footballhighlight.data.NetworkBoundResource;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;

public class CurrentEventsRepository {

    private LiveScoreApi mLiveScoreApi;

    @Inject
    public CurrentEventsRepository(LiveScoreApi liveScoreApi) {
        mLiveScoreApi = liveScoreApi;
    }

    public LiveData<Response<ItemList>> getCurrentEvents(String from, String to,
                                                         @Nullable String countryId,
                                                         @Nullable String leagueId,
                                                         @Nullable String matchId) {

        return new NetworkBoundResource<ItemList, List<MatchEntity>>() {

            @NonNull
            @Override
            protected Call<List<MatchEntity>> createCall() {
                return mLiveScoreApi.getEvents(from, to, countryId, leagueId, matchId);
            }

            @Override
            protected ItemList processResult(List<MatchEntity> entityList) {
                return new ItemList(parseMatchEntityList(entityList));
            }
        }.getAsLiveData();
    }

    public Observable<List<Match>> getCurrentEvent(String from, String to,
                                                   @Nullable String countryId,
                                                   @Nullable String leagueId,
                                                   @Nullable String matchId) {

        return mLiveScoreApi.observableEvents(from, to, countryId, leagueId, matchId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(matchEntities -> parseMatchEntityList(matchEntities))
                /*.onExceptionResumeNext(matchList -> {
                    Log.d("CurrentEventsRepository", "onExceptionResumeNext");
                })*/
                /*.onErrorResumeNext(throwable -> {
                    Log.d("CurrentEventsRepository", "onErrorResumeNext");
                })*/
                .onErrorReturn(throwable -> {
                    Log.d("CurrentEventsRepository", "onErrorReturn");
                    return new ArrayList<>();
                })
                //.doOnError(matchList -> new ArrayList<Match>())
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
