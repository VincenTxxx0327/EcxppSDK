package com.ecxppsdk.base;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.LinkedList;
import java.util.List;

/**
 * Author: VincenT
 * Date: 2017/4/24 10:56
 * Contact:qq 328551489
 * Purpose:基本Pager适配器
 */

public abstract class BasePagerAdapter<T> extends PagerAdapter {
    protected List<T> mData;
    protected Context mContext;
    protected LinkedList<View> mViewCache = null;

    public BasePagerAdapter(Context context, List<T> data) {
        this.mData = data;
        this.mContext = context;
        this.mViewCache = new LinkedList<>();
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public void remove(int position) {
        mData.remove(position);
        notifyDataSetChanged();
    }

    public void addAllData(List<T> data) {
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void replaceData(List<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public int getItemPosition(Object object) {
        return super.getItemPosition(object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
        this.mViewCache.add((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup parent, int position) {
        ViewHolder viewHolder = null;
        View convertView;
//        if (mViewCache.size() == 0) {//从废弃的里去取 取到则使用 取不到则创建
        convertView = LayoutInflater.from(parent.getContext()).inflate(getItemLayout(), parent, false);
        viewHolder = onCreateViewHolder(convertView, viewHolder, position);
        convertView.setTag(viewHolder);
//        } else {
//            convertView = mViewCache.removeFirst();
//            viewHolder = (ViewHolder)convertView.getTag();
//        }
        parent.addView(convertView);
        return parent;
    }

    public class ViewHolder {

    }

    public ViewHolder onCreateViewHolder(View convertView, ViewHolder viewHolder, int position) {
        viewHolder = createItemView(convertView);
        onBindViewHolder(viewHolder, position);
        return viewHolder;
    }

    protected abstract int getItemLayout();

    protected abstract ViewHolder createItemView(View view);

    protected abstract void onBindViewHolder(ViewHolder viewHolder, int position);

    public interface OnItemClickListener extends View.OnClickListener {
        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);
    }

    protected OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

}
