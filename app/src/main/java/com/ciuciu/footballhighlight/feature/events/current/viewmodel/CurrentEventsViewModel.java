package com.ciuciu.footballhighlight.feature.events.current.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.feature.events.current.interactor.CurrentEventsInteractor;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class CurrentEventsViewModel extends BaseViewModel {

    private CurrentEventsInteractor mInteractor;

    private MediatorLiveData<Response<ItemList>> matchList;

    @Inject
    public CurrentEventsViewModel(CurrentEventsInteractor interactor) {
        mInteractor = interactor;
    }

    public LiveData<Response<ItemList>> getCurrentEvents(String from, String to,
                                                         @Nullable String countryId,
                                                         @Nullable String leagueId,
                                                         @Nullable String matchId) {
        if (matchList == null) {
            matchList = new MediatorLiveData<>();
            matchList.addSource(mInteractor.getCurrentEvents(from, to, countryId, leagueId, matchId), itemListResource -> {
                switch (itemListResource.getStatus()) {
                    case SUCCESS:
                        matchList.setValue(validData(itemListResource.getData().getItems()));
                        break;

                    default:
                        matchList.setValue(itemListResource);
                        break;
                }
            });
        }
        return matchList;
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
