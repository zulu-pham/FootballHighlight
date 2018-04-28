package com.ciuciu.footballhighlight.feature.worldcup.interactor;

import android.arch.lifecycle.LiveData;

import com.ciuciu.footballhighlight.common.interactor.BaseInteractor;
import com.ciuciu.footballhighlight.data.Resource;
import com.ciuciu.footballhighlight.data.disk.DiskHelper;
import com.ciuciu.footballhighlight.data.network.LiveScoreApi;
import com.ciuciu.footballhighlight.data.preferences.PreferencesHelper;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.repository.WorldCupRepository;

import javax.inject.Inject;

public class WorldCupInteractorImpl extends BaseInteractor implements WorldCupInteractor {

    private WorldCupRepository mWorldCupRepository;

    @Inject
    public WorldCupInteractorImpl(PreferencesHelper preferencesHelper, DiskHelper diskHelper, LiveScoreApi liveScoreApi,
                                  WorldCupRepository worldCupRepository) {
        super(preferencesHelper, diskHelper, liveScoreApi);
        mWorldCupRepository = worldCupRepository;
    }

    @Override
    public LiveData<Resource<ItemList>> getScores() {
        return mWorldCupRepository.getListMatch();
    }
}
