package com.icheero.app.activity.framework.eventbus;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.app.model.MessageEvent;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusSecondActivity extends AppCompatActivity
{
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.bt_message)
    Button mBtMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus_second);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bt_message)
    public void onViewClicked()
    {
        EventBus.getDefault().postSticky(new MessageEvent("Hello EventBus."));
    }
}