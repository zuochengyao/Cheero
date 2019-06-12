package com.icheero.app.activity.feature.oreo;

import android.app.PendingIntent;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.NotificationManager;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NotificationActivity extends BaseActivity
{
    @BindView(R.id.notification_title)
    EditText mTitle;
    @BindView(R.id.notification_content)
    EditText mContent;
    @BindView(R.id.notification_send)
    Button mSend;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.notification_send)
    public void OnClickEvent()
    {
        Intent notificationIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.icheero.com/blog"));
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationManager.getInstance().showNotification(pIntent, mTitle.getText(), mContent.getText(), TAG.getName(), 1, NotificationManager.CHANNEL_ID_BLOG);
    }
}
