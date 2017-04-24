package com.ecxppsdk.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Author: Wh1te
 * Date: 2016/9/19
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {
    protected List<T> mData;

    public BaseAdapter(List<T> data) {
        this.mData = data;
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void replaceData(List<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        return createItemView(view);
    }

    protected abstract int getItemLayout();

    protected abstract RecyclerView.ViewHolder createItemView(View view);


    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


}
