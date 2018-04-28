package com.ciuciu.footballhighlight.data.network;

import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.model.entity.MatchEntity;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiveScoreApi {

    @GET("/api/?action=get_events")
    Call<List<MatchEntity>> getEvents(@Query("from") String from,
                                      @Query("to") String to,
                                      @Query("country_id") @Nullable String country_id,
                                      @Query("league_id") @Nullable String league_id,
                                      @Query("match_id") @Nullable String match_id);

    @GET("/api/?action=get_events")
    Observable<List<MatchEntity>> getEventsObservable(@Query("from") String from,
                                                      @Query("to") String to,
                                                      @Query("country_id") @Nullable String country_id,
                                                      @Query("league_id") @Nullable String league_id,
                                                      @Query("match_id") @Nullable String match_id);
}
