package com.ciuciu.footballhighlight.feature.worldcup.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.support.annotation.NonNull;

import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;
import com.ciuciu.footballhighlight.data.Resource;
import com.ciuciu.footballhighlight.feature.worldcup.interactor.WorldCupInteractor;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.entity.MatchEntity;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

public class WorldCupViewModel extends BaseViewModel {

    private WorldCupInteractor mWorldCupInteractor;

    private MediatorLiveData<Resource<ItemList>> matchList;

    @Inject
    public WorldCupViewModel(WorldCupInteractor interactor) {
        mWorldCupInteractor = interactor;
    }

    public LiveData<Resource<ItemList>> getMatchList() {
        if (matchList == null) {
            matchList = new MediatorLiveData<>();
            matchList.addSource(mWorldCupInteractor.getScores(), itemListResource -> {
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

    private Resource<ItemList> validData(List<Match> matchList) {
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

        return Resource.success(new ItemList(leagueSectionHeaders));
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
