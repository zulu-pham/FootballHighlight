package com.ciuciu.footballhighlight.feature;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.ciuciu.footballhighlight.R;
import com.ciuciu.footballhighlight.common.ui.BaseActivity;
import com.ciuciu.footballhighlight.feature.worldcup.WorldCupFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutRes());
        initActivity();
    }

    @Override
    public int getLayoutRes() {
        return R.layout.activity_main;
    }

    @Override
    protected void initActivity() {
        addFragment(R.id.rootLayout, WorldCupFragment.newInstance());
    }

    private void addFragment(int viewId, Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(viewId, fragment)
                .commit();
    }
}
