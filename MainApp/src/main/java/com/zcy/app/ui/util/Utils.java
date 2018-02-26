package com.zcy.app.ui.util;

import android.content.Context;

/**
 * Created by zuochengyao on 2018/2/26.
 */

public class Utils
{
    public static int dp2px(Context context, float dp)
    {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }
}
