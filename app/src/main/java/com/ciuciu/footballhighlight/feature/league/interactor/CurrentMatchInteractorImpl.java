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
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;

public class CurrentMatchInteractorImpl extends BaseInteractor implements CurrentMatchInteractor {

    private CurrentEventsRepository mCurrentEventsRepository;

    private List<League> supportLeagues = LeagueConfig.getLeagueList();

    private Date mStartDate;
    private String mFromDate;
    private String mToDate;

    @Inject
    public CurrentMatchInteractorImpl(PreferencesHelper preferencesHelper, DiskHelper diskHelper, LiveScoreApi liveScoreApi,
                                      CurrentEventsRepository currentEventsRepository) {
        super(preferencesHelper, diskHelper, liveScoreApi);
        mCurrentEventsRepository = currentEventsRepository;

        mStartDate = setupStartDate();
        mFromDate = new SimpleDateFormat("yyyy-M-dd").format(DateUtils.yesterday());
        mToDate = new SimpleDateFormat("yyyy-M-dd").format(DateUtils.tomorrow());
    }

    @Override
    public Flowable<ApiResponseBody<List<Match>>> getCurrentMatch() {
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
                .map(lists -> flatCurrentMatchData(lists))
                .repeatWhen(objectObservable -> objectObservable.delay(45, TimeUnit.SECONDS))
                .retryWhen(throwableObservable -> throwableObservable.delay(45, TimeUnit.SECONDS));
    }

    private Observable<ApiResponseBody<List<Match>>> getEvents(@Nullable String countryId, @Nullable String leagueId) {
        return mCurrentEventsRepository.getCurrentEvents(mFromDate, mToDate, countryId, leagueId, null);
    }

    private ApiResponseBody<List<Match>> flatCurrentMatchData(List<ApiResponseBody<List<Match>>> apiResponseBodyList) {
        int statusCode = ApiResponseBody.STATUS_CODE_SUCCESS;

        List<Match> matchList = new ArrayList<>();
        for (int i = 0; i < apiResponseBodyList.size(); i++) {
            // Status code
            if (statusCode == ApiResponseBody.STATUS_CODE_SUCCESS) {
                statusCode = apiResponseBodyList.get(i).getStatusCode();
            } else if (statusCode == ApiResponseBody.STATUS_CODE_NO_CONTENT &&
                    apiResponseBodyList.get(i).getStatusCode() != ApiResponseBody.STATUS_CODE_SUCCESS) {
                statusCode = apiResponseBodyList.get(i).getStatusCode();
            }
            // data
            for (Match match : apiResponseBodyList.get(i).getData()) {
                if (match.getMatchDate().before(mStartDate)) {
                    matchList.add(match);
                }
            }
        }
        Collections.sort(matchList, (match1, match2) -> match1.getMatchDate().compareTo(match2.getMatchDate()));
        return ApiResponseBody.success(statusCode, matchList);
    }

    /**
     * @return 3 hour later from now
     */
    private Date setupStartDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 3);
        return calendar.getTime();
    }
}
