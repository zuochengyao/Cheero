package com.icheero.app.activity.ui;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.app.custom.view.MoveView;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MoveViewActivity extends AppCompatActivity
{
    @BindView(R.id.move_view)
    MoveView mMoveView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_view);
        ButterKnife.bind(this);
    }
}
