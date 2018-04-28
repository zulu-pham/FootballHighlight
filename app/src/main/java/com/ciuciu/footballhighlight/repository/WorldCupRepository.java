package com.ciuciu.footballhighlight.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

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

public class WorldCupRepository {

    private LiveScoreApi mLiveScoreApi;

    @Inject
    public WorldCupRepository(LiveScoreApi liveScoreApi) {
        mLiveScoreApi = liveScoreApi;
    }

    public LiveData<Response<ItemList>> getListMatch() {

        return new NetworkBoundResource<ItemList, List<MatchEntity>>() {

            @NonNull
            @Override
            protected Call<List<MatchEntity>> createCall() {
                return mLiveScoreApi.getEvents("2018-4-24", "2018-4-26", "163", null, null);
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
