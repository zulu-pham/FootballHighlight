package com.ciuciu.footballhighlight.feature.common.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionRecyclerViewAdapter;
import com.ciuciu.footballhighlight.feature.common.adapter.sectionedadapter.SectionWrapper;
import com.ciuciu.footballhighlight.feature.common.listener.OnRecyclerViewAdapterListener;
import com.ciuciu.footballhighlight.feature.common.viewholder.LeagueSectionViewHolder;
import com.ciuciu.footballhighlight.feature.common.viewholder.LoadMoreViewHolder;
import com.ciuciu.footballhighlight.feature.common.viewholder.ScoreViewHolder;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.model.view.LeagueSectionHeader;
import com.ciuciu.footballhighlight.model.view.Match;

import java.util.List;

public class MatchListAdapter extends SectionRecyclerViewAdapter<LeagueSectionHeader, Match, LeagueSectionViewHolder, ScoreViewHolder> {

    private final String TAG = MatchListAdapter.class.getSimpleName();

    private final int DEFAULT_ITEM_COUNT = 1;       // load more item

    private final int ITEM_LOAD_MORE = 3;

    private Context mContext;

    private OnRecyclerViewAdapterListener mListener;

    private boolean mIsLoading = false;
    private boolean mEnableLoadMore = false;
    private boolean mIsRefresh = false;

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

        notifyItemChanged(getItemCount() - 1);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        switch (viewType) {
            case ITEM_LOAD_MORE:
                View view = LayoutInflater.from(mContext).inflate(LoadMoreViewHolder.getLayoutRes(), viewGroup, false);
                return new LoadMoreViewHolder(view);

            default:
                return super.onCreateViewHolder(viewGroup, viewType);
        }
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
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int flatPosition) {
        if (mEnableLoadMore && flatPosition == getFlatItemList().size()) {
            if (!mIsLoading && mListener != null) {
                ((LoadMoreViewHolder) holder).start();
                mListener.onLoadMore();
            }
            return;
        }

        if (getItemViewType(flatPosition) != ITEM_LOAD_MORE) {
            super.onBindViewHolder(holder, flatPosition);
        }
    }

    @Override
    public void onBindSectionViewHolder(LeagueSectionViewHolder sectionViewHolder, int sectionPosition, LeagueSectionHeader section) {
        sectionViewHolder.bindView(section);
    }

    @Override
    public void onBindChildViewHolder(ScoreViewHolder childViewHolder, int sectionPosition, int childPosition, Match child) {
        childViewHolder.bindView(child);
    }

    @Override
    public int getItemCount() {
        return super.getItemCount() + DEFAULT_ITEM_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_LOAD_MORE;
        } else {
            return super.getItemViewType(position);
        }
    }

    public void setEnableLoadMore(boolean enable) {
        if (mEnableLoadMore != enable) {
            mEnableLoadMore = enable;
            notifyItemChanged(getItemCount() - 1);
        }
    }

    public void setAdapterListener(OnRecyclerViewAdapterListener listener) {
        mListener = listener;
    }
}
