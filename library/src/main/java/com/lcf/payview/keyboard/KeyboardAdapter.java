package com.lcf.payview.keyboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.lcf.payview.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author lcf
 * @date 2018/2/3 18:14
 * @since 1.0
 */
public class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder> {

    private List<KeyboardItem> mData;
    private LayoutInflater mLayoutInflater;
    private OnItemClickListener mOnItemClickListener;

    public KeyboardAdapter(List<KeyboardItem> data) {
        this.mData = data == null ? new ArrayList<KeyboardItem>() : data;
    }

    @Override
    public int getItemViewType(int position) {
        return mData.get(position).getType();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mLayoutInflater == null) {
            mLayoutInflater = LayoutInflater.from(parent.getContext());
        }
        View view;
        if (viewType == KeyboardType.TYPE_DELETE) {
            view = mLayoutInflater.inflate(R.layout.item_keyboard_delete, null);
        } else if (viewType == KeyboardType.TYPE_EMPTY) {
            view = mLayoutInflater.inflate(R.layout.item_keyboard_empty, null);
        } else {
            view = mLayoutInflater.inflate(R.layout.item_keyboard_key, null);
        }
        final ViewHolder holder = new ViewHolder(view);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(KeyboardAdapter.this, v, holder.getLayoutPosition());
                }
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        KeyboardItem item = mData.get(position);
        if (item.getType() == KeyboardType.TYPE_NUMBER) {
            Button button = (Button) holder.itemView;
            button.setText(item.getTitle());
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public OnItemClickListener getOnItemClickListener() {
        return mOnItemClickListener;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public List<KeyboardItem> getData() {
        return mData;
    }

    public void setNewData(List<KeyboardItem> data) {
        this.mData = data == null ? new ArrayList<KeyboardItem>() : data;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

    /**
     * Interface definition for a callback to be invoked when an item in this
     * RecyclerView itemView has been clicked.
     */
    public interface OnItemClickListener {

        /**
         * Callback method to be invoked when an item in this RecyclerView has
         * been clicked.
         *
         * @param adapter  the adapter
         * @param view     The itemView within the RecyclerView that was clicked (this
         *                 will be a view provided by the adapter)
         * @param position The position of the view in the adapter.
         */
        void onItemClick(RecyclerView.Adapter adapter, View view, int position);
    }
}
