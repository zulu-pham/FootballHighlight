package com.ciuciu.footballhighlight.feature.league.interactor;

import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.LeagueConfig;
import com.ciuciu.footballhighlight.common.interactor.BaseInteractor;
import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;
import com.ciuciu.footballhighlight.model.League;
import com.ciuciu.footballhighlight.model.view.Match;
import com.ciuciu.footballhighlight.repository.CurrentEventsRepository;
import com.ciuciu.footballhighlight.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class PlayedMatchInteractorImpl extends BaseInteractor implements PlayedMatchInteractor {

    private CurrentEventsRepository mCurrentEventsRepository;

    private List<League> supportLeagues = LeagueConfig.getLeagueList();

    private Date mFromDate, mToDate;

    @Inject
    public PlayedMatchInteractorImpl(PreferencesHelper preferencesHelper, DiskHelper diskHelper, LiveScoreApi liveScoreApi,
                                     CurrentEventsRepository currentEventsRepository) {
        super(preferencesHelper, diskHelper, liveScoreApi);

        mCurrentEventsRepository = currentEventsRepository;

        mFromDate = DateUtils.after(-1);
        mToDate = DateUtils.after(mFromDate, -2);
    }

    @Override
    public Flowable<ApiResponseBody<List<Match>>> getPlayedMatch() {
        return createObservable();
    }

    private Flowable<ApiResponseBody<List<Match>>> createObservable() {
        Observable<ApiResponseBody<List<Match>>>[] observables = new Observable[supportLeagues.size()];
        for (int i = 0; i < observables.length; i++) {
            observables[i] = getEvents(supportLeagues.get(i).getCountryId(), supportLeagues.get(i).getLeagueId());
        }

        return Observable
                .mergeArray(observables)
                .toList()
                .map(lists -> flatPlayedMatchData(lists))
                .toFlowable()
                .doOnNext(apiResponseBody -> {
                    if (apiResponseBody.getStatusCode() != ApiResponseBody.STATUS_CODE_BAD_REQUEST) {
                        mFromDate = DateUtils.after(mToDate, -1);
                        mToDate = DateUtils.after(mFromDate, -2);
                    }
                });
    }

    private Observable<ApiResponseBody<List<Match>>> getEvents(@Nullable String countryId, @Nullable String leagueId) {

        String fromDateString = new SimpleDateFormat("yyyy-M-dd").format(mFromDate);
        String toDateString = new SimpleDateFormat("yyyy-M-dd").format(mToDate);

        return mCurrentEventsRepository.getCurrentEvents(toDateString, fromDateString, countryId, leagueId, null);
    }

    private ApiResponseBody<List<Match>> flatPlayedMatchData(List<ApiResponseBody<List<Match>>> apiResponseBodyList) {
        int statusCode = ApiResponseBody.STATUS_CODE_SUCCESS;

        List<Match> matchList = new ArrayList<>();
        for (int i = 0; i < apiResponseBodyList.size(); i++) {
            // Status code
            if (apiResponseBodyList.get(i).getStatusCode() == ApiResponseBody.STATUS_CODE_BAD_REQUEST) {
                return ApiResponseBody.error();
            }
            if (statusCode == ApiResponseBody.STATUS_CODE_SUCCESS) {
                statusCode = apiResponseBodyList.get(i).getStatusCode();
            }
            // data
            matchList.addAll(apiResponseBodyList.get(i).getData());
        }
        Collections.sort(matchList, (match1, match2) -> match1.getMatchDate().compareTo(match2.getMatchDate()));
        return ApiResponseBody.success(statusCode, matchList);
    }
}
