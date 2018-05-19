package com.ciuciu.footballhighlight.feature.common.viewholder;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.ciuciu.footballhighlight.R;

public class CircleIndicator extends AppCompatImageView {

    private Animation mAnimation;

    public CircleIndicator(Context context) {
        super(context);
    }

    public CircleIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);

        mAnimation = AnimationUtils.loadAnimation(context, R.anim.rotate);

        this.setVisibility(INVISIBLE);
    }

    public void startAnimation() {
        setVisibility(VISIBLE);
        startAnimation(mAnimation);
    }

    public void stopAnimation() {
        if (getVisibility() != VISIBLE) {
            return;
        }

        mAnimation.cancel();
        mAnimation.reset();

        clearAnimation();
        setVisibility(INVISIBLE);
    }
}
