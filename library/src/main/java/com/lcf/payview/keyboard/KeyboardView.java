package com.lcf.payview.keyboard;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.lcf.payview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: lcf
 * @since: 1.0
 * @date: 2018/2/4 20:55
 */
public class KeyboardView extends FrameLayout {
    private int[] KEY_NUMBERS = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};
    private int KEY_EMPTY_INDEX = 9;

    private KeyboardAdapter mAdapter;
    private OnKeyboardClickListener mOnKeyboardClickListener;

    public KeyboardView(@NonNull Context context) {
        this(context, null);
    }

    public KeyboardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public KeyboardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        RecyclerView rv = new RecyclerView(context);
        rv.setLayoutManager(new GridLayoutManager(context, 3));
        rv.addItemDecoration(new GridRecyclerItemDecoration(getResources().getDrawable(R.drawable.shape_keyboard_divider)));
        mAdapter = new KeyboardAdapter(getData());
        mAdapter.setOnItemClickListener(new KeyboardAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View view, int position) {
                KeyboardItem item = mAdapter.getData().get(position);
                if (mOnKeyboardClickListener != null) {
                    if (item.getType() == KeyboardType.TYPE_NUMBER) {
                        mOnKeyboardClickListener.onTextClick(item.getTitle());
                    } else if (item.getType() == KeyboardType.TYPE_DELETE) {
                        mOnKeyboardClickListener.onDeleteClick();
                    }
                }
            }
        });
        rv.setAdapter(mAdapter);
        LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        addView(rv, lp);
    }

    private List<KeyboardItem> getData() {
        List<KeyboardItem> items = new ArrayList<>();
        for (int i : KEY_NUMBERS) {
            items.add(new KeyboardItem(KeyboardType.TYPE_NUMBER, String.valueOf(i)));
        }
        items.add(KEY_EMPTY_INDEX, new KeyboardItem(KeyboardType.TYPE_EMPTY, ""));
        items.add(new KeyboardItem(KeyboardType.TYPE_DELETE, ""));
        return items;
    }

    public OnKeyboardClickListener getOnKeyboardClickListener() {
        return mOnKeyboardClickListener;
    }

    public void setOnKeyboardClickListener(OnKeyboardClickListener listener) {
        this.mOnKeyboardClickListener = listener;
    }
}
