package com.ciuciu.footballhighlight.feature.common.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.model.view.Match;
import com.ciuciu.footballhighlight.utils.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScoreViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.image_home_logo)
    ImageView imageViewHomeLogo;

    @BindView(R.id.textView_home_name)
    TextView textViewHomeName;

    @BindView(R.id.textView_home_score)
    TextView textViewHomeScore;

    @BindView(R.id.image_away_logo)
    ImageView imageViewAwayLogo;

    @BindView(R.id.textView_away_name)
    TextView textViewAwayName;

    @BindView(R.id.textView_away_score)
    TextView textViewAwayScore;

    @BindView(R.id.textView_match_status)
    TextView textViewMatchStatus;

    public static int getLayoutRes() {
        return R.layout.row_item_score;
    }

    public ScoreViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bindView(Match match) {
        ImageUtils.loadTeamLogo(itemView.getContext(), imageViewHomeLogo, match.getMatchHomeTeamName());
        textViewHomeName.setText(match.getMatchHomeTeamName());
        textViewHomeScore.setText(match.getMatchHomeTeamScore());

        ImageUtils.loadTeamLogo(itemView.getContext(), imageViewAwayLogo, match.getMatchAwayTeamName());
        textViewAwayName.setText(match.getMatchAwayTeamName());
        textViewAwayScore.setText(match.getMatchAwayTeamScore());

        textViewMatchStatus.setText(match.getMatchStatus());
    }
}
