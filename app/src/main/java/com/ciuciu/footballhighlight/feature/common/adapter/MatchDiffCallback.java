package com.ciuciu.footballhighlight.feature.common.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionWrapper;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.view.Match;

public class MatchDiffCallback extends DiffUtil.Callback {

    private ItemList newList;
    private ItemList oldList;

    public MatchDiffCallback(ItemList newList, ItemList oldList) {
        this.newList = newList;
        this.oldList = oldList;
    }

    @Override
    public int getOldListSize() {
        return oldList == null ? 0 : oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList == null ? 0 : newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        SectionWrapper oldObj = (SectionWrapper) oldList.get(oldItemPosition);
        SectionWrapper newObj = (SectionWrapper) newList.get(newItemPosition);

        if (oldObj.isSection() && newObj.isSection()) {
            return oldObj.equals(newObj);
        }

        if (!oldObj.isSection() && !newObj.isSection()) {
            return ((Match) oldObj.getChild()).getMatchId().equals(((Match) newObj.getChild()).getMatchId());
        }
        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        SectionWrapper oldObj = (SectionWrapper) oldList.get(oldItemPosition);
        SectionWrapper newObj = (SectionWrapper) newList.get(newItemPosition);

        if (!oldObj.isSection() && !newObj.isSection()) {
            Match oldMatch = (Match) oldObj.getChild();
            Match newMatch = (Match) newObj.getChild();

            return oldMatch.getMatchStatus().equals(newMatch.getMatchStatus()) &&
                    oldMatch.getMatchHomeTeamScore().equals(newMatch.getMatchHomeTeamScore()) &&
                    oldMatch.getMatchAwayTeamScore().equals(newMatch.getMatchAwayTeamScore()) &&
                    oldMatch.getMatchLive().equals(newMatch.getMatchLive())
                    ;
        }

        return true;
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
