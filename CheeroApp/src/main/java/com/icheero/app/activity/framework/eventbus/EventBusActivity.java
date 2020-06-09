package com.icheero.app.activity.framework.eventbus;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventBusActivity extends BaseActivity
{
    @BindView(R.id.tv_message)
    TextView mTvMessage;
    @BindView(R.id.bt_message)
    Button mBtMessage;
    @BindView(R.id.bt_subscription)
    Button mBtSubscription;

    private boolean mRegister = false;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_bus);
        ButterKnife.bind(this);
    }

    @Override
    protected void onResume()
    {
        super.onResume();

    }

    @Override
    protected void onPause()
    {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.bt_message, R.id.bt_subscription})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.bt_message:
//                startActivity(new Intent(TAG, Ev));
                break;
            case R.id.bt_subscription:
                if (!mRegister)
                    EventBus.getDefault().register(this);
                else
                    EventBus.getDefault().unregister(this);
                mRegister ^= mRegister;
                break;
        }
    }
}
