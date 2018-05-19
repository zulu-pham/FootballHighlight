package com.ciuciu.footballhighlight.feature.league.interactor;

import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.List;

import io.reactivex.Flowable;

public interface CurrentMatchInteractor {

    Flowable<ApiResponseBody<List<Match>>> getCurrentMatch();
}
