package com.ciuciu.footballhighlight.feature.events.current;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.common.ui.BaseLifecycleFragment;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.databinding.FragmentEventsCurrentBinding;
import com.ciuciu.footballhighlight.feature.common.adapter.MatchListAdapter;
import com.ciuciu.footballhighlight.feature.common.viewholder.ScoreDividerItemDecoration;
import com.ciuciu.footballhighlight.feature.events.current.viewmodel.CurrentEventsViewModel;
import com.ciuciu.footballhighlight.model.ItemList;

import java.util.ArrayList;

public class CurrentEventsFragment extends BaseLifecycleFragment<FragmentEventsCurrentBinding, CurrentEventsViewModel> {

    private MatchListAdapter mAdapter;

    public static Fragment newInstance() {
        Fragment fragment = new CurrentEventsFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_events_current;
    }

    @Override
    protected Object getModelClass() {
        return CurrentEventsViewModel.class;
    }

    @Override
    protected boolean isSharedViewModel() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        mAdapter = new MatchListAdapter(getContext(), new ArrayList<>());
        mViewDataBinding.recyclerView.setAdapter(mAdapter);
        mViewDataBinding.recyclerView.addItemDecoration(new ScoreDividerItemDecoration(getContext()));
    }

    @Override
    protected void subscribeUI(CurrentEventsViewModel viewModel) {
        viewModel.getMatchList()
                .observe(this, itemListResponse -> processResponse(itemListResponse));
    }

    @Override
    public void showLoadingIndicator(boolean visible) {

    }

    private void processResponse(Response<ItemList> itemListResource) {
        switch (itemListResource.getStatus()) {
            case LOADING:
                Log.d("WorldCupFragment", "LOADING");
                break;

            case SUCCESS:
                Log.d("WorldCupFragment", "SUCCESS");
                mAdapter.updateData(itemListResource.getData().list());
                break;

            case ERROR:
                Log.d("WorldCupFragment", "ERROR");
                break;
        }
    }
}
