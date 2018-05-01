package com.ciuciu.footballhighlight.feature.events.current.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.LiveDataReactiveStreams;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ciuciu.footballhighlight.LeagueConfig;
import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.feature.events.current.interactor.CurrentEventsInteractor;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.League;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;
import com.ciuciu.footballhighlight.utils.DateUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class CurrentEventsViewModel extends BaseViewModel {

    private CurrentEventsInteractor mInteractor;

    private final CompositeDisposable disposable = new CompositeDisposable();

    private List<League> supportLeagues = LeagueConfig.getLeagueList();
    private MutableLiveData<Response<ItemList>> matchList = new MutableLiveData<>();

    private final String mFrom;

    @Inject
    public CurrentEventsViewModel(CurrentEventsInteractor interactor) {
        mInteractor = interactor;
        mFrom = new SimpleDateFormat("yyyy-M-dd").format(DateUtils.yesterday());

        getCurrentEvents();
    }

    @Override
    protected void onCleared() {
        disposable.clear();
        super.onCleared();
    }

    public LiveData<Response<ItemList>> getMatchList() {
        return matchList;
    }

    private void getCurrentEvents() {
        disposable.add(createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .toFlowable(BackpressureStrategy.BUFFER)
                .doOnSubscribe(aVoid -> matchList.setValue(Response.loading(null)))
                .subscribe(itemListResponse -> matchList.setValue(Response.success(itemListResponse.getData()))
                ));
    }

    private Observable<Response<ItemList>> createObservable() {
        Observable<ItemList>[] observables = new Observable[supportLeagues.size()];
        for (int i = 0; i < observables.length; i++) {
            observables[i] = getEvents(supportLeagues.get(i).getCountryId(), supportLeagues.get(i).getLeagueId());
        }

        return Observable
                .mergeArrayDelayError(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(objectObservable -> objectObservable.delay(60, TimeUnit.SECONDS))
                .map(itemList -> validData(itemList.getItems())
                );
    }

    private Observable<ItemList> getEvents(@Nullable String countryId, @Nullable String leagueId) {
        String to = new SimpleDateFormat("yyyy-M-dd").format(DateUtils.tomorrow());
        return mInteractor.getEvents(mFrom, to, countryId, leagueId, null)
                .doOnError(throwable -> {

                });
    }

    private Response<ItemList> validData(List<Match> matchList) {
        List<LeagueSectionHeader> leagueSectionHeaders = new ArrayList<>();

        if (matchList != null) {
            for (Match match : matchList) {
                int index = indexOfList(match, leagueSectionHeaders);

                if (index == -1) {
                    LeagueSectionHeader leagueSectionHeader = new LeagueSectionHeader(
                            match.getCountryId(),
                            match.getCountryName(),
                            match.getLeagueId(),
                            match.getLeagueName());

                    leagueSectionHeader.getChildItems().add(match);
                    leagueSectionHeaders.add(leagueSectionHeader);
                } else {
                    leagueSectionHeaders.get(index).getChildItems().add(match);
                }
            }
        }

        return Response.success(new ItemList(leagueSectionHeaders));
    }

    private int indexOfList(Match match, @NonNull List<LeagueSectionHeader> leagueSectionHeaderList) {
        for (int i = 0; i < leagueSectionHeaderList.size(); i++) {
            if (LeagueSectionHeader.isBelongOfLeague(match, leagueSectionHeaderList.get(i))) {
                return i;
            }
        }
        return -1;
    }
}
