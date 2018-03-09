package com.zcy.app.custom.widget;

import android.app.AlertDialog;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.preference.DialogPreference;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.SeekBar;

import com.zcy.app.R;

/**
 * Created by zuochengyao on 2018/3/9.
 */

public class ColorPreference extends DialogPreference
{
    private static final int COLOR_DEFAULT = Color.WHITE;
    private int mCurrentColor; // 当前颜色
    private SeekBar mRedBar, mGreenBar, mBlueBar;

    public ColorPreference(Context context)
    {
        this(context, null);
    }

    public ColorPreference(Context context, AttributeSet attrs)
    {
        this(context, attrs, 0);
    }

    public ColorPreference(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 构造新的对话框，点击首选项时显示此对话框。
     * 为每个实例创建并设置新的内容视图。
     * @param builder
     */
    @Override
    protected void onPrepareDialogBuilder(AlertDialog.Builder builder)
    {
        View rootView = LayoutInflater.from(getContext()).inflate(R.layout.preference_color, null);
        mRedBar = rootView.findViewById(R.id.seek_bar_red);
        mRedBar.setProgress(Color.red(mCurrentColor));
        mGreenBar = rootView.findViewById(R.id.seek_bar_green);
        mGreenBar.setProgress(Color.red(mCurrentColor));
        mBlueBar = rootView.findViewById(R.id.seek_bar_blue);
        mBlueBar.setProgress(Color.red(mCurrentColor));
        builder.setView(rootView);
        super.onPrepareDialogBuilder(builder);
    }

    /**
     * 关闭对话框时调用
     * @param positiveResult
     */
    @Override
    protected void onDialogClosed(boolean positiveResult)
    {
        if (positiveResult)
        {
            int color = Color.rgb(mRedBar.getProgress(), mGreenBar.getProgress(), mBlueBar.getProgress());
            setCurrentColor(color);
        }
    }

    /**
     * 由框架调用，用于获得传入首选项XML定义的缺省值
     */
    @Override
    protected Object onGetDefaultValue(TypedArray a, int index)
    {
        ColorStateList value = a.getColorStateList(index);
        if (value == null)
            return COLOR_DEFAULT;
        return value.getDefaultColor();
    }

    /**
     * 由框架调用，设置首选项的初始值
     * 该值来自默认值或上一次持久化的值
     */
    @Override
    protected void onSetInitialValue(boolean restorePersistedValue, Object defaultValue)
    {
        if (defaultValue != null)
            setCurrentColor(restorePersistedValue? getPersistedInt(COLOR_DEFAULT) : (Integer) defaultValue);
    }

    /**
     * 基于当前设置返回自定义概要值
     */
    @Override
    public CharSequence getSummary()
    {
        Spannable summary = null;
        try
        {
            int color = getPersistedInt(COLOR_DEFAULT);
            String content = String.format("Current Value is 0x%02X%02X%02X", Color.red(color), Color.green(color), Color.blue(color));
            summary = new SpannableString(content);
            summary.setSpan(new ForegroundColorSpan(color), 0, summary.length(), 0);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return summary;
    }

    private void setCurrentColor(int colorValue)
    {
        this.mCurrentColor = colorValue;
        // 保存新值
        persistInt(colorValue);
        // 通知首选项监听器
        notifyDependencyChange(shouldDisableDependents());
        notifyChanged();
    }
}
