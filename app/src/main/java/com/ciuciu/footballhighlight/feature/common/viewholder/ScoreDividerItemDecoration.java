package com.ciuciu.footballhighlight.feature.common.viewholder;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.ciuciu.footballhighlight.R;

public class ScoreDividerItemDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;
    private int mDistance;
    private final Rect mBounds = new Rect();

    public ScoreDividerItemDecoration(Context context) {
        mPaint = new Paint();
        mPaint.setColor(Color.TRANSPARENT);
        mDistance = (int) context.getResources().getDimension(R.dimen.distance_normal);
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if (parent.getLayoutManager() == null) {
            return;
        }
        drawVertical(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        if (parent.getChildViewHolder(view) instanceof ScoreViewHolder) {
            outRect.set(0, 0, 0, mDistance);
        }
    }

    private void drawVertical(Canvas canvas, RecyclerView parent) {
        canvas.save();
        final int left;
        final int right;
        //noinspection AndroidLintNewApi - NewApi lint fails to handle overrides.
        if (parent.getClipToPadding()) {
            left = parent.getPaddingLeft();
            right = parent.getWidth() - parent.getPaddingRight();
            canvas.clipRect(left, parent.getPaddingTop(), right,
                    parent.getHeight() - parent.getPaddingBottom());
        } else {
            left = 0;
            right = parent.getWidth();
        }

        final int childCount = parent.getChildCount();

        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            parent.getDecoratedBoundsWithMargins(child, mBounds);
            final int bottom = mBounds.bottom + Math.round(child.getTranslationY());
            final int top = bottom - mDistance;
            canvas.drawLine(left, top, right, bottom, mPaint);
        }
        canvas.restore();
    }
}