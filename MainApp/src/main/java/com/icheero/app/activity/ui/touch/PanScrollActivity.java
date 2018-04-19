package com.icheero.app.activity.ui.touch;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.icheero.app.R;
import com.icheero.app.custom.widget.scrollview.PanScrollView;

public class PanScrollActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        PanScrollView scrollView = new PanScrollView(this);
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        for (int i = 0; i < 5; i++)
        {
            ImageView iv = new ImageButton(this);
            iv.setImageResource(R.mipmap.ic_launcher);
            layout.addView(iv, new LinearLayout.LayoutParams(1000, 500));
        }
        scrollView.addView(layout);
        setContentView(scrollView);
    }
}
