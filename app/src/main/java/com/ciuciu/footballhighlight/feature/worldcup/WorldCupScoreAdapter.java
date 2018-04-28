package com.ciuciu.footballhighlight.feature.worldcup;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionRecyclerViewAdapter;
import com.ciuciu.footballhighlight.feature.common.viewholder.LeagueSectionViewHolder;
import com.ciuciu.footballhighlight.feature.common.viewholder.ScoreViewHolder;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.List;

public class WorldCupScoreAdapter extends SectionRecyclerViewAdapter<LeagueSectionHeader, Match, LeagueSectionViewHolder, ScoreViewHolder> {

    private Context mContext;

    public WorldCupScoreAdapter(Context context, List<LeagueSectionHeader> sectionItemList) {
        super(context, sectionItemList);
        mContext = context;
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
