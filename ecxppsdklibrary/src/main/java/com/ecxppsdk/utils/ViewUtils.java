package com.ecxppsdk.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ecxppsdk.R;
import com.ecxppsdk.widget.theme.ThemeUIInterface;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by product-m-31 on 2016/9/8.
 */
public class ViewUtils {

    public static void setVisible(View view, boolean isVisible) {
        if (view != null) {
            if (isVisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.GONE);
            }
        }
    }

    public static void setInvisible(View view, boolean isInvisible) {
        if (view != null) {
            if (isInvisible) {
                view.setVisibility(View.VISIBLE);
            } else {
                view.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static boolean getVisible(View view) {
        if (view != null) {
            if (view.getVisibility() == View.VISIBLE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public static boolean getInvisible(View view) {
        if (view != null) {
            if (view.getVisibility() == View.INVISIBLE) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 设置状态栏透明
     *
     * @param context
     * @param resId
     */
    public static void setStatusBarTranslate(Activity context, int resId) {

        SystemBarTintManager tintManager = new SystemBarTintManager(context);
        if (resId == android.R.color.transparent) {
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(false);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(false);
            //noinspection deprecation
        } else {
            // enable status bar tint
            tintManager.setStatusBarTintEnabled(true);
            // enable navigation bar tint
            tintManager.setNavigationBarTintEnabled(true);
            // enable navigation bar tint
        }
        tintManager.setStatusBarTintColor(context.getResources().getColor(resId));

    }

    private static int getAttribute(AttributeSet attr, int paramInt) {
        int value = -1;
        int count = attr.getAttributeCount();
        for (int i = 0; i < count; i++) {
            if (attr.getAttributeNameResource(i) == paramInt) {
                String str = attr.getAttributeValue(i);
                if (null != str && str.startsWith("?")) {
                    value = Integer.valueOf(str.substring(1, str.length()));
                    return value;
                }
            }
        }
        return value;
    }

    public static int getBackground(AttributeSet attr) {
        return getAttribute(attr, android.R.attr.background);
    }

    public static int getTextColor(AttributeSet attr) {
        return getAttribute(attr, android.R.attr.textColor);
    }

    public static int getTextColorHint(AttributeSet attr) {
        return getAttribute(attr, android.R.attr.textColorHint);
    }

    public static void setBackground(ThemeUIInterface uiInterface, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        Drawable drawable = ta.getDrawable(0);
        if (null != uiInterface) {
            (uiInterface.getView()).setBackground(drawable);
        }
        ta.recycle();
    }

    public static void setTextColor(ThemeUIInterface uiInterface, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        ColorStateList resourceId = ta.getColorStateList(0);
        if (null != uiInterface && uiInterface instanceof TextView) {
            ((TextView) uiInterface.getView()).setTextColor(resourceId);
        }
        ta.recycle();
    }

    public static void setTextColorHint(ThemeUIInterface uiInterface, Resources.Theme theme, int paramInt) {
        TypedArray ta = theme.obtainStyledAttributes(new int[]{paramInt});
        int resourceId = ta.getColor(0, 0);
        if (null != uiInterface && uiInterface instanceof TextView) {
            ((TextView) uiInterface.getView()).setHintTextColor(resourceId);
        }
        ta.recycle();
    }

    /**
     * 切换应用主题
     *
     * @param rootView
     */
    public static void changeTheme(View rootView, Resources.Theme theme) {
        //这里逻辑很简单 就是递归调用changeTheme-----递归调用setTheme了。
        //注意 你们如果是listview也包含在里面的话 listview自定义实现接口的时候要稍微复杂一些，看你们需要不需要也刷新listview里的item了
        //这里为了简单 我就不写那么复杂了，就这一个逻辑：先set自己的theme 然后遍历自己的子控件 逐一set
        if (rootView instanceof ThemeUIInterface) {
            ((ThemeUIInterface) rootView).setTheme(theme);
            if (rootView instanceof ViewGroup) {
                int count = ((ViewGroup) rootView).getChildCount();
                for (int i = 0; i < count; i++) {
                    changeTheme(((ViewGroup) rootView).getChildAt(i), theme);
                }
            }
        }
    }

    public static ViewGroup.LayoutParams setLayoutParamsHeight(View view, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = height;
        return layoutParams;
    }

    public static ViewGroup.LayoutParams setLayoutParamsWidth(View view, int width) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        return layoutParams;
    }

    public static ViewGroup.LayoutParams setLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        return layoutParams;
    }

    public static Bitmap getBitmap(View view) {
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
        view.setDrawingCacheEnabled(false);
        return bitmap;
    }

    public static View addView(Context context, View view, Bitmap bitmap) {
        View maskView = new View(context.getApplicationContext());
        maskView.setBackground(new BitmapDrawable(context.getResources(), bitmap));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        ((ViewGroup) view).addView(maskView, params);
        return maskView;
    }

}
