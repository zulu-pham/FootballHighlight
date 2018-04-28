package com.ciuciu.footballhighlight.feature.common.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LeagueSectionViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.textView)
    TextView textView;

    public static int getLayoutRes(){
        return R.layout.layout_league_section;
    }

    public LeagueSectionViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(LeagueSectionHeader leagueSectionHeader) {
        textView.setText(leagueSectionHeader.getCountryName() + " - " + leagueSectionHeader.getLeagueName());
    }
}
