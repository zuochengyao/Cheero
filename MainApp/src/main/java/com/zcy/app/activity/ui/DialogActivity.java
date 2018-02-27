package com.zcy.app.activity.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class DialogActivity extends Activity implements View.OnClickListener, DialogInterface.OnClickListener
{
    private static final String[] ZONES = {"Pacific Time", "Mountain Time", "Central Time", "Eastern Time", "Atlantic Time"};

    Button mButton;
    AlertDialog mActions;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        mButton = new Button(this);
        mButton.setText("Click for Time Zones");
        mButton.setOnClickListener(this);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select Time Zone");
        builder.setItems(ZONES, this);
        builder.setNegativeButton("Cancel", null);
        mActions = builder.create();
        setContentView(mButton);
    }

    @Override
    public void onClick(View v)
    {
        mActions.show();
    }

    @Override
    public void onClick(DialogInterface dialog, int which)
    {
        String selected = ZONES[which];
        mButton.setText(selected);
    }
}
