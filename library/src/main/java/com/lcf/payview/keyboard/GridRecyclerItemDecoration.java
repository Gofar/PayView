package com.lcf.payview.keyboard;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

/**
 * @author lcf
 * @date 2018/2/5 13:49
 * @since 1.0
 */
public class GridRecyclerItemDecoration extends ItemDecoration {
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    public GridRecyclerItemDecoration(Context context) {
        TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
    }

    public GridRecyclerItemDecoration(Drawable drawable) {
        if (drawable == null) {
            throw new NullPointerException("Drawable cannot be null.");
        }
        this.mDivider = drawable;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        drawHorizontal(c, parent);
        drawVertical(c, parent);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int childPosition = parent.getChildLayoutPosition(view);
        if (isLastColumn(parent, childPosition)) {
            // 如果是最后一列，则不需要绘制右边
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else if (isLastSpan(parent, childPosition)) {
            // 如果是最后一行，则不需要绘制底部
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        } else {
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), mDivider.getIntrinsicHeight());
        }
    }

    private void drawHorizontal(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getLeft() - lp.leftMargin;
            int right = child.getRight() + lp.rightMargin + mDivider.getIntrinsicWidth();
            int top = child.getBottom() + lp.bottomMargin;
            int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    private void drawVertical(Canvas c, RecyclerView parent) {
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {
            View child = parent.getChildAt(i);
            RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) child.getLayoutParams();
            int left = child.getRight() + lp.rightMargin;
            int right = left + mDivider.getIntrinsicWidth();
            int top = child.getTop() - lp.topMargin;
            int bottom = child.getBottom() + lp.bottomMargin;
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    /**
     * 行数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        int spanCount = -1;
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
        }
        return spanCount;
    }

    /**
     * 是否是最后一列
     *
     * @param parent
     * @param childPosition
     * @return
     */
    private boolean isLastColumn(RecyclerView parent, int childPosition) {
        int spanCount = getSpanCount(parent);
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            if ((childPosition + 1) % spanCount == 0) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                if ((childPosition + 1) % spanCount == 0) {
                    return true;
                }
            } else {
                int childCount = parent.getAdapter().getItemCount();
                childCount = childCount - childCount % spanCount;
                if (childPosition >= childCount) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否是最后一行
     *
     * @param parent
     * @param childPosition
     * @return
     */
    private boolean isLastSpan(RecyclerView parent, int childPosition) {
        int spanCount = getSpanCount(parent);
        int childCount = parent.getAdapter().getItemCount();
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            // 拿到最后一行的起始position
            int lastSpan = childCount - childCount % spanCount;
            if (childPosition >= lastSpan) {
                return true;
            }
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                // 纵向滚动
                // 拿到最后一行的起始position
                int lastSpan = childCount - childCount % spanCount;
                if (childPosition >= lastSpan) {
                    return true;
                }
            } else {
                // 横向滚动
                // 最后一列
                if ((childCount + 1) % spanCount == 0) {
                    return true;
                }
            }
        }
        return false;
    }
}
