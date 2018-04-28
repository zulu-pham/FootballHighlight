package com.ciuciu.footballhighlight.feature.worldcup.interactor;

import android.arch.lifecycle.LiveData;

import com.ciuciu.footballhighlight.data.Resource;
import com.ciuciu.footballhighlight.model.ItemList;

public interface WorldCupInteractor {

    LiveData<Resource<ItemList>> getScores();
}
