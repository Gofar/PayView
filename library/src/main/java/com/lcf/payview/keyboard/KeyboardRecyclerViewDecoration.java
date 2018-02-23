package com.lcf.payview.keyboard;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author lcf
 * @date 2018/2/6 10:02
 * @since 1.0
 */
public class KeyboardRecyclerViewDecoration extends RecyclerView.ItemDecoration {

    private Paint mPaint;

    private Drawable mDrawable;

    public KeyboardRecyclerViewDecoration(Drawable drawable) {
        if (drawable == null) {
            throw new NullPointerException("drawable is null obj");
        }
        mDrawable = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawVertical(c, parent, state);
        drawHorizontal(c, parent, state);
    }

    private void drawHorizontal(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getBottom() + layoutParams.bottomMargin;
            int bottom = top + mDrawable.getIntrinsicHeight();
            int left = child.getLeft() - layoutParams.leftMargin;
            int right = child.getRight() + layoutParams.rightMargin + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int itemCount = state.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
            int top = child.getTop() - layoutParams.topMargin;
            int bottom = child.getBottom() + layoutParams.bottomMargin;
            int left = child.getRight() + layoutParams.rightMargin;
            int right = left + mDrawable.getIntrinsicWidth();
            mDrawable.setBounds(left, top, right, bottom);
            mDrawable.draw(c);
        }
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childLayoutPosition = parent.getChildLayoutPosition(view);
        int itemCount = state.getItemCount();
        if (isLastColumn(parent, childLayoutPosition)) {
            outRect.set(0, 0, 0, mDrawable.getIntrinsicHeight());
        } else if (isLastRaw(parent, childLayoutPosition, itemCount)) {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), 0);
        } else {
            outRect.set(0, 0, mDrawable.getIntrinsicWidth(), mDrawable.getIntrinsicHeight());
        }
    }

    private void initPaint() {
        mPaint = new Paint();
        mPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setColor(0xFFE0E0E0);
        mPaint.setStrokeWidth(2);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawBorder(c, parent);
    }

    private void drawBorder(Canvas c, RecyclerView parent) {
        if (mPaint == null) {
            initPaint();
        }
        c.drawRect(0, 0, parent.getMeasuredWidth(), parent.getMeasuredHeight(), mPaint);
    }

    private boolean isLastColumn(RecyclerView parent, int childLayoutPosition) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        return layoutManager instanceof GridLayoutManager && (childLayoutPosition + 1) % getSpanCount(parent) == 0;
    }

    private boolean isLastRaw(RecyclerView parent, int childLayoutPosition, int itemCount) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            itemCount = itemCount - itemCount % getSpanCount(parent);
            if (childLayoutPosition >= itemCount) {
                return true;
            }
        }
        return false;
    }

    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = -1;
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }
}