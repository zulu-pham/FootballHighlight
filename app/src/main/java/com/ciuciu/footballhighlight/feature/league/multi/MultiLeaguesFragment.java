package com.ciuciu.footballhighlight.feature.league.multi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.common.ui.BaseLifecycleFragment;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.databinding.FragmentLeagueMultiBinding;
import com.ciuciu.footballhighlight.feature.common.adapter.MatchListAdapter;
import com.ciuciu.footballhighlight.feature.common.listener.OnRecyclerViewAdapterListener;
import com.ciuciu.footballhighlight.feature.common.viewholder.ScoreDividerItemDecoration;
import com.ciuciu.footballhighlight.model.ItemList;

import java.util.ArrayList;

public class MultiLeaguesFragment extends BaseLifecycleFragment<FragmentLeagueMultiBinding, MultiLeaguesViewModel> {

    private final String TAG = MultiLeaguesFragment.class.getSimpleName();

    private MatchListAdapter mAdapter;

    private boolean isLoadingMore = false;

    public static Fragment newInstance() {
        Fragment fragment = new MultiLeaguesFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_league_multi;
    }

    @Override
    protected boolean isSharedViewModel() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new MatchListAdapter(getContext(), new ArrayList<>());
        mAdapter.setAdapterListener(mAdapterListener);
        mViewDataBinding.recyclerView.setAdapter(mAdapter);
        mViewDataBinding.recyclerView.addItemDecoration(new ScoreDividerItemDecoration(getContext()));
    }

    @Override
    protected void subscribeUI(MultiLeaguesViewModel viewModel) {
        mViewModel.getMatchList().observe(this, itemListResponse -> processCurrentMatchResponse(itemListResponse));
    }

    @Override
    public void showLoadingIndicator(boolean visible) {

    }

    @Override
    protected Object getModelClass() {
        return MultiLeaguesViewModel.class;
    }

    private void processCurrentMatchResponse(Response<ItemList> itemListResource) {
        switch (itemListResource.getStatus()) {
            case LOADING:
                Log.d(TAG, "LOADING");
                break;

            case SUCCESS:
                Log.d(TAG, "SUCCESS");

                if (isLoadingMore) {
                    isLoadingMore = false;
                    if (itemListResource.getStatus() != Response.Status.ERROR &&
                            itemListResource.getData().size() == 0) {
                        mViewModel.getNextPlayedMatch();
                    }
                } else {
                    mAdapter.updateData(itemListResource.getData().list());
                    mAdapter.setEnableLoadMore(true);
                }
                break;

            case ERROR:
                Log.d(TAG, "ERROR");
                break;
        }
    }

    OnRecyclerViewAdapterListener mAdapterListener = () -> {
        Log.d(TAG, "onStartLoadMore");
        isLoadingMore = true;
        mViewModel.getNextPlayedMatch();
    };
}
