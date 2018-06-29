package com.icheero.app.activity.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.transition.Explode;

import com.icheero.app.R;

public class StyledActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_styled);
        String transition = getIntent().getStringExtra("transition");
        if (transition.equals("explode"))
        {
            Explode explode = new Explode();
            explode.setDuration(700l);
            getWindow().setEnterTransition(explode);
        }
    }

    @Override
    public void finish()
    {
        super.finish();
    }
}
