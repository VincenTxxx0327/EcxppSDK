package com.ecxppsdk.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.View;

import com.ecxppsdk.utils.ViewUtils;


/**
 * Author: product-m-31
 * Date: 2017/5/18 14:32
 * Contact:qq 328551489
 * Purpose:此类用于..
 */

public class ThemeSwipeRefreshLayout extends SwipeRefreshLayout implements ThemeUIInterface {

    private int attr_background = -1;

    public ThemeSwipeRefreshLayout(Context context) {
        this(context, null);
    }

    public ThemeSwipeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
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
}
