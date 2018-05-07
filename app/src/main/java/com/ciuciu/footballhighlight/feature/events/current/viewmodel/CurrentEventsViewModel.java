package com.ciuciu.footballhighlight.feature.events.current.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.ciuciu.footballhighlight.ApplicationException;
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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;

public class CurrentEventsViewModel extends BaseViewModel {

    private final String TAG = "CurrentEventsViewModel";

    private CurrentEventsInteractor mInteractor;

    private List<League> supportLeagues = LeagueConfig.getLeagueList();

    private final CompositeDisposable disposable = new CompositeDisposable();

    private MutableLiveData<Response<ItemList>> responseData = new MutableLiveData<Response<ItemList>>();

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
        return responseData;
    }

    private void getCurrentEvents() {
        disposable.add(createObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(aVoid -> responseData.setValue(Response.loading(null)))
                .map(matchList -> pushData(matchList))
                .subscribeWith(new DisposableSubscriber<Response<ItemList>>() {
                    @Override
                    public void onNext(Response<ItemList> itemListResponse) {
                        Log.d(TAG, "subscribeWith onNext");
                        responseData.setValue(itemListResponse);
                    }

                    @Override
                    public void onError(Throwable t) {
                        Log.d(TAG, "subscribeWith onError " + t.toString());
                        responseData.setValue(Response.error(new ApplicationException(t), null));
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "subscribeWith onComplete");
                    }
                })
        );

    }

    private Flowable<List<Match>> createObservable() {
        Observable<List<Match>>[] observables = new Observable[supportLeagues.size()];
        for (int i = 0; i < observables.length; i++) {
            observables[i] = getEvents(supportLeagues.get(i).getCountryId(), supportLeagues.get(i).getLeagueId());
        }

        return Observable
                .mergeArrayDelayError(observables)
                .toList()
                .map(lists -> {
                    List<Match> matchList = new ArrayList<>();
                    for (List<Match> list : lists) {
                        for (Match match : list) {
                            matchList.add(match);
                        }
                    }
                    return matchList;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .repeatWhen(objectObservable -> objectObservable.delay(45, TimeUnit.SECONDS))
                .retryWhen(throwableObservable -> throwableObservable.delay(45, TimeUnit.SECONDS))
                ;
    }

    private Observable<List<Match>> getEvents(@Nullable String countryId, @Nullable String leagueId) {
        String to = new SimpleDateFormat("yyyy-M-dd").format(DateUtils.tomorrow());

        return mInteractor.getEvents(mFrom, to, countryId, leagueId, null);
    }

    private Response<ItemList> pushData(List<Match> matchList) {
        List<LeagueSectionHeader> leagueSectionHeaders;

        Response<ItemList> lastData = responseData.getValue();
        if (lastData != null && lastData.getStatus() == Response.Status.SUCCESS) {
            leagueSectionHeaders = lastData.getData().getItems();
        } else {
            leagueSectionHeaders = new ArrayList<LeagueSectionHeader>();
        }

        if (matchList != null) {
            for (Match match : matchList) {
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

        Collections.sort(leagueSectionHeaders, (match1, match2) -> match1.getDate().compareTo(match2.getDate()));

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
