package com.ciuciu.footballhighlight.feature.worldcup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.LinearLayout;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.common.ui.BaseLifecycleFragment;
import com.ciuciu.footballhighlight.data.Resource;
import com.ciuciu.footballhighlight.databinding.FragmentWorldCupBinding;
import com.ciuciu.footballhighlight.feature.worldcup.viewmodel.WorldCupViewModel;
import com.ciuciu.footballhighlight.model.ItemList;
import com.ciuciu.footballhighlight.widget.LinearDividerItemDecoration;

public class WorldCupFragment extends BaseLifecycleFragment<FragmentWorldCupBinding, WorldCupViewModel> {

    public static Fragment newInstance() {
        Fragment fragment = new WorldCupFragment();
        return fragment;
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_world_cup;
    }

    @Override
    protected Object getModelClass() {
        return WorldCupViewModel.class;
    }

    @Override
    protected boolean isSharedViewModel() {
        return false;
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        LinearDividerItemDecoration itemDecoration = new LinearDividerItemDecoration(getContext(), LinearLayout.VERTICAL, R.dimen.distance_smaller);
        mViewDataBinding.recyclerView.addItemDecoration(itemDecoration);
    }

    @Override
    protected void subscribeUI(WorldCupViewModel viewModel) {
        viewModel.getMatchList().observe(this, itemListResource -> processResponse(itemListResource));
    }

    @Override
    public void showLoadingIndicator(boolean visible) {

    }

    private void processResponse(Resource<ItemList> itemListResource) {
        switch (itemListResource.getStatus()) {
            case LOADING:
                Log.d("WorldCupFragment", "LOADING");
                break;

            case SUCCESS:
                Log.d("WorldCupFragment", "SUCCESS");
                WorldCupScoreAdapter adapter = new WorldCupScoreAdapter(getContext(), itemListResource.getData().getItems());
                mViewDataBinding.recyclerView.setAdapter(adapter);
                break;

            case ERROR:
                Log.d("WorldCupFragment", "ERROR");
                break;
        }
    }
}
