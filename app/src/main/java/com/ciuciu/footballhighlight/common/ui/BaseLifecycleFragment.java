package com.ciuciu.footballhighlight.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;

public abstract class BaseLifecycleFragment<B extends ViewDataBinding, V extends BaseViewModel> extends BaseFragment {

    protected BaseActivity mActivity;
    public B mViewDataBinding;
    public V mViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        AndroidSupportInjection.inject(this);
        mActivity = (BaseActivity) context;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isSharedViewModel()) {
            mViewModel = ViewModelProviders.of(getActivity(), viewModelFactory).get(getModelClass());
        } else {
            mViewModel = ViewModelProviders.of(this, viewModelFactory).get(getModelClass());
        }
        subscribeUI(mViewModel);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mViewDataBinding = DataBindingUtil.inflate(inflater, getLayoutRes(), container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initView(savedInstanceState);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mViewDataBinding.unbind();
    }

    @Override
    public void onDetach() {
        mActivity = null;
        super.onDetach();
    }

    protected abstract @LayoutRes
    int getLayoutRes();

    protected abstract <T> T getModelClass();

    /**
     * For Share ViewModel between Fragments, ViewModel is scoped to Activity contain Fragments,
     * to communicate between Fragments
     */
    protected abstract boolean isSharedViewModel();

    protected abstract void initView(Bundle savedInstanceState);

    protected abstract void subscribeUI(V viewModel);

    public abstract void showLoadingIndicator(boolean visible);
}
