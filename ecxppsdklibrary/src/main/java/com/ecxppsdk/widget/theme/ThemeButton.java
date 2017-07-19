package com.ecxppsdk.widget.theme;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;

import com.ecxppsdk.utils.ViewUtils;

/**
 * Author: product-m-31
 * Date: 2017/5/18 14:32
 * Contact:qq 328551489
 * Purpose:此类用于..
 */

public class ThemeButton extends Button implements ThemeUIInterface {

    private int attr_background = -1;
    private int attr_textColor = -1;

    public ThemeButton(Context context) {
        this(context, null);
    }

    public ThemeButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThemeButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.attr_background = ViewUtils.getBackground(attrs);
        this.attr_textColor = ViewUtils.getTextColor(attrs);
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
    }
}
