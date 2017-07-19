package com.ecxppsdk.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.View;

import com.ecxppsdk.utils.ViewUtils;

/**
 * Author: product-m-31
 * Date: 2017/5/18 14:37
 * Contact:qq 328551489
 * Purpose:此类用于..
 */

public class ThemeTextView extends AppCompatTextView implements ThemeUIInterface {

    private int attr_background = -1;
    private int attr_textColor = -1;
    private int attr_textColorHint = -1;

    public ThemeTextView(Context context) {
        this(context, null);
    }

    public ThemeTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewUtils.getBackground(attrs);
        this.attr_textColor = ViewUtils.getTextColor(attrs);
        this.attr_textColorHint = ViewUtils.getTextColorHint(attrs);
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
        if (attr_textColor != -1) {
            ViewUtils.setTextColor(this, themeId, attr_textColor);
        }
        if (attr_textColorHint != -1) {
            ViewUtils.setTextColorHint(this, themeId, attr_textColorHint);
        }
    }
}
