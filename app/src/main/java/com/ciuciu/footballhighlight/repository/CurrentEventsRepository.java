package com.ciuciu.footballhighlight.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.NetworkBoundResource;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.http.Query;

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
                return parseMatchEntityList(entityList);
            }
        }.getAsLiveData();
    }

    private ItemList parseMatchEntityList(List<MatchEntity> entityList) {
        ItemList itemList = new ItemList();

        List<Match> matchList = new ArrayList<>();
        for (MatchEntity entity : entityList) {
            matchList.add(new Match(entity));
        }
        itemList.setItems(matchList);

        return itemList;
    }

}
