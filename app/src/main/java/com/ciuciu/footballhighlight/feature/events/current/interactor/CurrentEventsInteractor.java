package com.ciuciu.footballhighlight.feature.events.current.interactor;

import android.arch.lifecycle.LiveData;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.model.ItemList;

public interface CurrentEventsInteractor {

    LiveData<Response<ItemList>> getCurrentEvents(String from, String to,
                                                  @Nullable String countryId,
                                                  @Nullable String leagueId,
                                                  @Nullable String matchId);
}
