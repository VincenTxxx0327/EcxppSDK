package com.ecxppsdk.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.ecxppsdk.R;

import java.util.List;

import butterknife.ButterKnife;

import static com.ecxppsdk.base.BaseAdapter.HolderType.Footer;
import static com.ecxppsdk.base.BaseAdapter.HolderType.HeaderFirst;
import static com.ecxppsdk.base.BaseAdapter.HolderType.HeaderSecond;
import static com.ecxppsdk.base.BaseAdapter.HolderType.HeaderThird;
import static com.ecxppsdk.base.BaseAdapter.HolderType.Loading;

/**
 * Author: VincenT
 * Date: 2017/3/1 14:38
 * Contact:qq 328551489
 * Purpose:基本适配器
 */
public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    public enum HolderType {
        HeaderFirst(20), HeaderSecond(21), HeaderThird(22), Footer(23), Loading(24), Content(25);

        private int id;

        HolderType(int id) {
            this.id = id;
        }

        public int getId() {
            return id;
        }

    }

    protected BaseAdapter mAdapter;
    protected int mWidth = 480;
    protected int mHeight = 800;
    protected int mColumn = 1;
    protected int mColumnWidth = 0;
    protected List<T> mData;
    protected Context mContext;
    protected String mType = "android";
    protected boolean isLastPage = false;
    protected boolean isInfinite = false;//是否无限轮播
    protected View mHeaderViewFirst;
    protected View mHeaderViewSecond;
    protected View mHeaderViewThird;
    protected View mFooterView;
    protected int mHeaderCount = 0;//头部View个数
    protected int mFooterCount = 0;//底部View个数

    public BaseAdapter(List<T> data) {
        this.mData = data;
    }

    public BaseAdapter(List<T> data, int width, int height) {
        this.mData = data;
        this.mWidth = width;
        this.mHeight = height;
    }

    public BaseAdapter(List<T> data, int width, int height, int column, int columnWidth) {
        this.mData = data;
        this.mWidth = width;
        this.mHeight = height;
        this.mColumn = column;
        this.mColumnWidth = columnWidth;
    }

    /**
     * 实现多类别item时用
     */
    public BaseAdapter(List<T> data, String type) {
        this.mData = data;
        this.mType = type;
    }

    /**
     * 有弹窗时用
     */
    public BaseAdapter(List<T> data, Context context) {
        this.mData = data;
        this.mContext = context;
    }

    public void setAdapter(BaseAdapter adapter) {
        mAdapter = adapter;
    }

    public void setHeaderView() {
        mHeaderCount = 1;
        notifyItemInserted(0);
    }

    public void setHeaderView(View viewFirst) {
        mHeaderViewFirst = viewFirst;
        mHeaderCount = 1;
        notifyItemInserted(0);
    }

    public void setHeaderView(View viewFirst, View viewSecond) {
        mHeaderViewFirst = viewFirst;
        mHeaderViewSecond = viewSecond;
        mHeaderCount = 2;
        notifyItemInserted(0);
    }

    public void setHeaderView(View viewFirst, View viewSecond, View viewThird) {
        mHeaderViewFirst = viewFirst;
        mHeaderViewSecond = viewSecond;
        mHeaderViewThird = viewThird;
        mHeaderCount = 3;
        notifyItemInserted(0);
    }

    public void setFooterView() {
        mFooterCount = 1;
        notifyItemInserted(getItemCount() - 1);
    }

    public void setFooterView(View view) {
        mFooterView = view;
        mFooterCount = 1;
        notifyItemInserted(getItemCount() - 1);
    }

    public boolean isHeaderViewFirst(int position) {
        return mHeaderCount != 0 && mHeaderViewFirst != null && position == 0 && position < mHeaderCount;
    }

    public boolean isHeaderViewSecond(int position) {
        return mHeaderCount != 0 && mHeaderViewSecond != null && position == 1 && position < mHeaderCount;
    }

    public boolean isHeaderViewThird(int position) {
        return mHeaderCount != 0 && mHeaderViewThird != null && position > 1 && position < mHeaderCount;
    }

    public boolean isFooterView(int position) {
        return mFooterCount != 0 && position - mHeaderCount >= mData.size();
    }

    public int getHeaderCount() {
        return mHeaderCount;
    }

    public static class HeaderViewHolder extends RecyclerView.ViewHolder {
        private OnItemClickListener mListener;

        public HeaderViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            this.mListener = listener;
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(view, getAdapterPosition());
                        mListener.onHeaderClick(view, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mListener != null) {
                        mListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public static class FooterViewHolder extends RecyclerView.ViewHolder {

        public LinearLayout llView;
        public TextView tvText;
        public ProgressBar pbBar;

        private OnItemClickListener mListener;

        public FooterViewHolder(View itemView, OnItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            llView = (LinearLayout) itemView.findViewById(R.id.ll_footer_view);
            tvText = (TextView) itemView.findViewById(R.id.tv_footer_text);
            pbBar = (ProgressBar) itemView.findViewById(R.id.pb_footer_bar);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mListener != null) {
                        mListener.onItemClick(view, getAdapterPosition());
                        mListener.onFooterClick(view, getAdapterPosition());
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mListener != null) {
                        mListener.onItemLongClick(view, getAdapterPosition());
                    }
                    return true;
                }
            });
        }
    }

    public static class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return mData;
    }

    public void setInfinite(boolean infinite) {
        isInfinite = infinite;
    }

    public void addPositionData(int position, T data) {
        if (mData.size() == 0) {
            this.mData.add(data);
        } else {
            this.mData.add(position, data);
        }
    }

    public void addAllData(List<T> data) {
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void addAllData(List<T> data, boolean isLastPage) {
        this.isLastPage = isLastPage;
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void replaceData(List<T> data) {
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    public void replaceData(List<T> data, boolean isLastPage) {
        this.isLastPage = isLastPage;
        this.mData.clear();
        this.mData.addAll(data);
        this.notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        int resId = getItemLayout(viewType);
        if (resId == HeaderFirst.getId()) {
            view = mHeaderViewFirst;
        } else if (resId == HeaderSecond.getId()) {
            view = mHeaderViewSecond;
        } else if (resId == HeaderThird.getId()) {
            view = mHeaderViewThird;
        } else if (resId == Footer.getId()) {
            view = mFooterView;
        } else if (resId == Loading.getId()) {
            view = mFooterView;
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(resId, parent, false);
        }
        return createItemView(view, viewType);
    }

    protected abstract int getItemLayout(int viewType);

    protected abstract RecyclerView.ViewHolder createItemView(View view, int viewType);


    @Override
    public int getItemCount() {
        if (isInfinite) {
            return Integer.MAX_VALUE;
        } else {
            return mData == null ? mHeaderCount + mFooterCount : mHeaderCount + mData.size() + mFooterCount;
        }
    }

    public void remove(int position) {
        mData.remove(position);
        notifyItemRemoved(position);
    }

    public static class BaseItemClickLister implements OnItemClickListener {

        @Override
        public void onHeaderClick(View view, int position) {

        }

        @Override
        public void onFooterClick(View view, int position) {

        }

        @Override
        public void onItemClick(View view, int position) {

        }

        @Override
        public void onInnerItemClick(View view, int position, List data) {

        }

        @Override
        public boolean onInnerItemLongClick(View view, int position, List data) {
            return false;
        }

        @Override
        public boolean onItemLongClick(View view, int position) {
            return false;
        }
    }

    public interface OnItemClickListener {

        void onHeaderClick(View view, int position);

        void onFooterClick(View view, int position);

        void onItemClick(View view, int position);

        boolean onItemLongClick(View view, int position);

        void onInnerItemClick(View view, int position, List data);

        boolean onInnerItemLongClick(View view, int position, List data);

    }

    public interface OnItemSwitchListener {
        void onSwitchClick(View view, boolean isOn);
    }

    protected OnItemClickListener mOnItemClickListener;
    protected OnItemSwitchListener mOnItemSwitchListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemSwitchListener(OnItemSwitchListener onItemSwitchListener) {
        mOnItemSwitchListener = onItemSwitchListener;
    }

}
