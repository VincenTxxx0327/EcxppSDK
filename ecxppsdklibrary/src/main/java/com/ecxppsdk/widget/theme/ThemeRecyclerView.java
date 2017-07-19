package com.ecxppsdk.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.ecxppsdk.base.BaseAdapter;
import com.ecxppsdk.utils.ViewUtils;

/**
 * Author: product-m-31
 * Date: 2017/5/18 14:32
 * Contact:qq 328551489
 * Purpose:此类用于..
 */

public class ThemeRecyclerView extends RecyclerView implements ThemeUIInterface {

    private int attr_background = -1;

    public ThemeRecyclerView(Context context) {
        this(context, null);
    }

    public ThemeRecyclerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeRecyclerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewUtils.getBackground(attrs);
    }


    @Override
    public View getView() {
        return this;
    }

    @Override
    public void setTheme(Resources.Theme themeId) {
        if (attr_background != -1) {
            ViewUtils.setBackground(this, themeId, attr_background);
        }
    }

    public void setHeaderView(BaseAdapter adapter) {
        adapter.setHeaderView();
    }

    public void setHeaderView(BaseAdapter adapter, View headerView) {
        adapter.setHeaderView(headerView);
    }

    public void setHeaderView(BaseAdapter adapter, View headerViewFirst, View headerViewSecond) {
        adapter.setHeaderView(headerViewFirst, headerViewSecond);
    }

    public void setHeaderView(BaseAdapter adapter, View headerViewFirst, View headerViewSecond, View headerViewThird) {
        adapter.setHeaderView(headerViewFirst, headerViewSecond, headerViewThird);
    }

    public void setFooterView(BaseAdapter adapter) {
        adapter.setFooterView();
    }

    public void setFooterView(BaseAdapter adapter, View footerView) {
        adapter.setFooterView(footerView);
    }

}
