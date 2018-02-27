package com.zcy.app.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.zcy.app.R;

public class StyledActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled);
    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.activity_close_enter, R.anim.activity_close_exit);
    }
}
