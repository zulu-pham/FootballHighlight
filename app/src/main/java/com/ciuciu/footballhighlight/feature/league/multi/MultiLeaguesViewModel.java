package com.ciuciu.footballhighlight.feature.league.multi;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.data.network.response.ApiResponseBody;
import com.ciuciu.footballhighlight.feature.league.interactor.CurrentMatchInteractor;
import com.ciuciu.footballhighlight.feature.league.interactor.PlayedMatchInteractor;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MultiLeaguesViewModel extends BaseViewModel {

    private final String TAG = MultiLeaguesViewModel.class.getSimpleName();

    private CurrentMatchInteractor mCurrentMatchInteractor;
    private PlayedMatchInteractor mPlayedMatchInteractor;

    private final CompositeDisposable currentMatchDisposable = new CompositeDisposable();
    private final CompositeDisposable playedMatchDisposable = new CompositeDisposable();

    private MutableLiveData<Response<ItemList>> matchListData = new MutableLiveData<>();

    @Inject
    public MultiLeaguesViewModel(CurrentMatchInteractor currentMatchInteractor, PlayedMatchInteractor playedMatchInteractor) {
        mCurrentMatchInteractor = currentMatchInteractor;
        mPlayedMatchInteractor = playedMatchInteractor;
        getCurrentMatch();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        currentMatchDisposable.clear();
        playedMatchDisposable.clear();
    }

    public LiveData<Response<ItemList>> getMatchList() {
        return matchListData;
    }

    /**
     * Current Match
     */
    private void getCurrentMatch() {
        currentMatchDisposable.add(mCurrentMatchInteractor.getCurrentMatch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(aVoid -> matchListData.setValue(Response.loading(null)))
                .map(matchList -> pushCurrentMatchData(matchList))
                .doOnNext(itemListResponse -> {
                    Log.d(TAG, "CurrentMatchInteractor doOnNext");
                    matchListData.setValue(itemListResponse);
                })
                .subscribe()
        );
    }

    private Response<ItemList> pushCurrentMatchData(ApiResponseBody<List<Match>> apiResponseBody) {
        List<LeagueSectionHeader> leagueSectionHeaders;
        Response<ItemList> lastData = matchListData.getValue();

        if (lastData == null || lastData.getData() == null) {
            if (apiResponseBody.getStatusCode() == ApiResponseBody.STATUS_CODE_BAD_REQUEST) {
                return Response.error(new IOException(), null);
            }

            leagueSectionHeaders = new ArrayList<>();
        } else {
            leagueSectionHeaders = lastData.getData().getItems();
        }

        if (apiResponseBody.getData() != null) {
            for (Match match : apiResponseBody.getData()) {
                int index = indexOfList(match, leagueSectionHeaders);

                if (index == -1) {
                    LeagueSectionHeader leagueSectionHeader = new LeagueSectionHeader(
                            match.getCountryId(),
                            match.getCountryName(),
                            match.getLeagueId(),
                            match.getLeagueName());

                    leagueSectionHeader.setDate(match.getMatchDate());
                    leagueSectionHeader.getChildItems().add(match);
                    leagueSectionHeaders.add(leagueSectionHeader);
                } else {
                    int childIndex = leagueSectionHeaders.get(index).getChildItems().indexOf(match);
                    if (childIndex >= 0) {
                        leagueSectionHeaders.get(index).getChildItems().set(childIndex, match);
                    } else {
                        leagueSectionHeaders.get(index).getChildItems().add(match);
                    }
                }
            }
        }
        //Collections.sort(leagueSectionHeaders, (match1, match2) -> match1.getDate().compareTo(match2.getDate()));
        return Response.success(new ItemList(leagueSectionHeaders));
    }

    /**
     * Previous Match
     */
    public void getNextPlayedMatch() {
        playedMatchDisposable.add(mPlayedMatchInteractor.getPlayedMatch()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(matchList -> pushPlayedMatchData(matchList))
                .doOnNext(itemListResponse -> {
                    Log.d(TAG, "PlayedMatchInteractor doOnNext " + itemListResponse.toString());
                    matchListData.setValue(itemListResponse);
                })
                .subscribe()
        );
    }

    private Response<ItemList> pushPlayedMatchData(ApiResponseBody<List<Match>> apiResponseBody) {
        List<LeagueSectionHeader> leagueSectionHeaders;
        Response<ItemList> lastData = matchListData.getValue();

        if (lastData == null || lastData.getData() == null) {
            if (apiResponseBody.getStatusCode() == ApiResponseBody.STATUS_CODE_BAD_REQUEST) {
                return Response.error(new IOException(), null);
            }

            leagueSectionHeaders = new ArrayList<>();
        } else {
            leagueSectionHeaders = lastData.getData().getItems();
        }

        if (apiResponseBody.getData() != null) {
            for (Match match : apiResponseBody.getData()) {
                int index = indexOfList(match, leagueSectionHeaders);

                if (index == -1) {
                    LeagueSectionHeader leagueSectionHeader = new LeagueSectionHeader(
                            match.getCountryId(),
                            match.getCountryName(),
                            match.getLeagueId(),
                            match.getLeagueName());

                    leagueSectionHeader.setDate(match.getMatchDate());
                    leagueSectionHeader.getChildItems().add(match);
                    leagueSectionHeaders.add(leagueSectionHeader);
                } else {
                    int childIndex = leagueSectionHeaders.get(index).getChildItems().indexOf(match);
                    if (childIndex >= 0) {
                        leagueSectionHeaders.get(index).getChildItems().set(childIndex, match);
                    } else {
                        leagueSectionHeaders.get(index).getChildItems().add(match);
                    }
                }
            }
        }

        //Collections.sort(leagueSectionHeaders, (match1, match2) -> match1.getDate().compareTo(match2.getDate()));

        return Response.success(new ItemList(leagueSectionHeaders));
    }

    /*
     */
    private int indexOfList(Match match, @NonNull List<LeagueSectionHeader> leagueSectionHeaderList) {
        for (int i = 0; i < leagueSectionHeaderList.size(); i++) {
            if (LeagueSectionHeader.isBelongOfLeague(match, leagueSectionHeaderList.get(i))) {
                return i;
            }
        }
        return -1;
    }

}
