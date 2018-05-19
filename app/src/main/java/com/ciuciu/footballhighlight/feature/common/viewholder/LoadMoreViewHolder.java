package com.ciuciu.footballhighlight.feature.common.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ciuciu.footballhighlight.R;


public class LoadMoreViewHolder extends RecyclerView.ViewHolder {

    private CircleIndicator mLoadingIndicator;

    public static int getLayoutRes() {
        return R.layout.row_item_loading_indicator;
    }

    public LoadMoreViewHolder(View itemView) {
        super(itemView);
        mLoadingIndicator = itemView.findViewById(R.id.loading_indicator);
    }

    public void start() {
        mLoadingIndicator.startAnimation();
    }

    public void stop() {
        mLoadingIndicator.stopAnimation();
    }
}
