package com.ciuciu.footballhighlight.feature.events.current;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.common.ui.BaseLifecycleFragment;
import com.ciuciu.footballhighlight.data.Response;
import com.ciuciu.footballhighlight.databinding.FragmentEventsCurrentBinding;
import com.ciuciu.footballhighlight.feature.common.adapter.MatchListAdapter;
import com.ciuciu.footballhighlight.feature.events.current.viewmodel.CurrentEventsViewModel;
import com.ciuciu.footballhighlight.model.ItemList;

public class CurrentEventsFragment extends BaseLifecycleFragment<FragmentEventsCurrentBinding, CurrentEventsViewModel> {

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

    }

    @Override
    protected void subscribeUI(CurrentEventsViewModel viewModel) {
        viewModel.getCurrentEvents("2018-4-24", "2018-4-26", "163", null, null)
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
                MatchListAdapter adapter = new MatchListAdapter(getContext(), itemListResource.getData().getItems());
                mViewDataBinding.recyclerView.setAdapter(adapter);
                break;

            case ERROR:
                Log.d("WorldCupFragment", "ERROR");
                break;
        }
    }
}
