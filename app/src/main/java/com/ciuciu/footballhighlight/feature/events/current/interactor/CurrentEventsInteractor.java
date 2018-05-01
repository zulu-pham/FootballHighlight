package com.ciuciu.footballhighlight.feature.events.current.interactor;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.model.ItemList;

import io.reactivex.Observable;

public interface CurrentEventsInteractor {

    LiveData<Response<ItemList>> getCurrentEvents(String from, String to,
                                                  @Nullable String countryId,
                                                  @Nullable String leagueId,
                                                  @Nullable String matchId);

    Observable<ItemList> getEvents(String from, String to,
                                   @Nullable String countryId,
                                   @Nullable String leagueId,
                                   @Nullable String matchId);
}
