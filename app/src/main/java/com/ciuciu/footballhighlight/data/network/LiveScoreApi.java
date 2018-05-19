package com.ciuciu.footballhighlight.data.network;

import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface LiveScoreApi {

    /*@GET("/api/?action=get_events")
    Observable<List<MatchEntity>> observableEvents(@Query("from") String from,
                                            @Query("to") String to,
                                            @Query("country_id") @Nullable String countryId,
                                            @Query("league_id") @Nullable String leagueId,
                                            @Query("match_id") @Nullable String matchId);*/

    @GET("/api/?action=get_events")
    Observable<ApiResponseBody<List<MatchEntity>>> getEvents(@Query("from") String from,
                                                 @Query("to") String to,
                                                 @Query("country_id") @Nullable String countryId,
                                                 @Query("league_id") @Nullable String leagueId,
                                                 @Query("match_id") @Nullable String matchId);
}
