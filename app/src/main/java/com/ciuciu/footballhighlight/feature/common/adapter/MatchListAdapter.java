package com.ciuciu.footballhighlight.feature.common.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionRecyclerViewAdapter;
import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionWrapper;
import com.ciuciu.footballhighlight.feature.common.viewholder.LeagueSectionViewHolder;
import com.ciuciu.footballhighlight.feature.common.viewholder.ScoreViewHolder;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.List;

public class MatchListAdapter extends SectionRecyclerViewAdapter<LeagueSectionHeader, Match, LeagueSectionViewHolder, ScoreViewHolder> {

    private Context mContext;

    public MatchListAdapter(Context context, List<LeagueSectionHeader> sectionItemList) {
        super(context, sectionItemList);
        mContext = context;
    }

    public void updateData(List<LeagueSectionHeader> sectionItemList) {
        List<SectionWrapper<LeagueSectionHeader, Match>> newListSectionWrapper = generateFlatItemList(sectionItemList);

        ItemList newList = new ItemList<>(newListSectionWrapper);
        ItemList oldList = new ItemList(getFlatItemList());

        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new MatchDiffCallback(newList, oldList));
        setFlatItemList(newListSectionWrapper);
        diffResult.dispatchUpdatesTo(this);
    }

    @Override
    public LeagueSectionViewHolder onCreateSectionViewHolder(ViewGroup sectionViewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(LeagueSectionViewHolder.getLayoutRes(), sectionViewGroup, false);
        return new LeagueSectionViewHolder(view);
    }

    @Override
    public ScoreViewHolder onCreateChildViewHolder(ViewGroup childViewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(ScoreViewHolder.getLayoutRes(), childViewGroup, false);
        return new ScoreViewHolder(view);
    }

    @Override
    public void onBindSectionViewHolder(LeagueSectionViewHolder sectionViewHolder, int sectionPosition, LeagueSectionHeader section) {
        sectionViewHolder.bindView(section);
    }

    @Override
    public void onBindChildViewHolder(ScoreViewHolder childViewHolder, int sectionPosition, int childPosition, Match child) {
        childViewHolder.bindView(child);
    }
}
