package com.ciuciu.footballhighlight.common.ui;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.ciuciu.footballhighlight.common.viewmodel.BaseViewModel;

import javax.inject.Inject;

import dagger.android.AndroidInjection;

public abstract class BaseLifecycleActivity<B extends ViewDataBinding, V extends BaseViewModel> extends BaseActivity {

    public B mViewDataBinding;
    public V mViewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        performDependencyInjection();
        super.onCreate(savedInstanceState);
        performDataBinding();
        initActivity();
    }

    public void performDependencyInjection() {
        AndroidInjection.inject(this);
        mViewModel = ViewModelProviders.of(this, viewModelFactory).get(getModelClass());
    }

    private void performDataBinding() {
        mViewDataBinding = DataBindingUtil.setContentView(this, getLayoutRes());
        //mViewDataBinding.setVariable(getBindingVariable(), mViewModel);
        mViewDataBinding.executePendingBindings();
    }

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    //public abstract int getBindingVariable();
    protected abstract <T> T getModelClass();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mViewDataBinding.unbind();
    }
}
