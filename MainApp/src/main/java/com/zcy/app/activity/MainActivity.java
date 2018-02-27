package com.zcy.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.zcy.app.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity
{
    @BindView(R.id.to_styled_activity)
    Button toStyledActivity;
    @BindView(R.id.to_custom_view_activity)
    Button toCustomViewActivity;
    @BindView(R.id.to_sections_activity)
    Button toSectionsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        ButterKnife.bind(this);
    }

    @OnClick({R.id.to_styled_activity, R.id.to_custom_view_activity, R.id.to_sections_activity})
    public void OnClickEvent(View v)
    {
        switch (v.getId())
        {
            case R.id.to_styled_activity:
            {
                startActivity(new Intent(this, StyledActivity.class));
                overridePendingTransition(R.anim.activity_open_enter, R.anim.activity_open_exit);
                break;
            }
            case R.id.to_custom_view_activity:
            {
                startActivity(new Intent(this, CustomViewActivity.class));
                break;
            }
            case R.id.to_sections_activity:
            {
                startActivity(new Intent(this, SectionsActivity.class));
                break;
            }
        }
    }

}
