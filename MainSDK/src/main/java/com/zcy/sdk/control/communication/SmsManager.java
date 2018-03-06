package com.zcy.sdk.control.communication;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.text.TextUtils;
import android.widget.Toast;

import com.zcy.sdk.util.Common;

/**
 * 短信接收器
 * Created by zuochengyao on 2018/3/6.
 */

public class SmsManager
{
    private static final String SMS_ACTION_RECEIVED = "android.provider.Telephony.SMS_RECEIVED";
    private static final String SMS_ACTION_SENT = "com.zcy.sdk.sms.SENT";
    private static final String SMS_ACTION_DELIVERED = "com.zcy.sdk.sms.DELIVERED";
    private static final String SMS_ADDRESS = "<ENTER YOUR NUMBER HERE>";
    private static final int SMS_REQUEST_CODE_SEND = 0;
    private static final int SMS_FLAGS = 0;

    private android.telephony.SmsManager manager;
    private static volatile SmsManager mInstance;

    private SmsManager()
    {
        manager = android.telephony.SmsManager.getDefault();
    }

    public static SmsManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (SmsManager.class)
            {
                if (mInstance == null)
                    mInstance = new SmsManager();
            }
        }
        return mInstance;
    }

    public void send(Context context, String strDestAddress, String message)
    {
        PendingIntent sIntent = PendingIntent.getBroadcast(context, SMS_REQUEST_CODE_SEND, new Intent(SMS_ACTION_SENT), SMS_FLAGS);
        PendingIntent dIntent = PendingIntent.getBroadcast(context, SMS_REQUEST_CODE_SEND, new Intent(SMS_ACTION_DELIVERED), SMS_FLAGS);
        manager.sendTextMessage(strDestAddress, null, message, sIntent, dIntent);
    }

    public void registerBroadcast(Context context)
    {
        context.registerReceiver(smsReceiver, new IntentFilter(SMS_ACTION_RECEIVED));
        context.registerReceiver(smsSent, new IntentFilter(SMS_ACTION_SENT));
        context.registerReceiver(smsDelivered, new IntentFilter(SMS_ACTION_DELIVERED));
    }

    public void unregisterBroadcast(Context context)
    {
        context.unregisterReceiver(smsReceiver);
        context.unregisterReceiver(smsSent);
        context.unregisterReceiver(smsDelivered);
    }

    private BroadcastReceiver smsSent = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    // 发送成功
                    break;
                case android.telephony.SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                case android.telephony.SmsManager.RESULT_ERROR_NO_SERVICE:
                case android.telephony.SmsManager.RESULT_ERROR_NULL_PDU:
                case android.telephony.SmsManager.RESULT_ERROR_RADIO_OFF:
                {
                    // 发送失败
                    break;
                }
            }
        }
    };

    private BroadcastReceiver smsDelivered = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            switch (getResultCode())
            {
                case Activity.RESULT_OK:
                    // 传递成功
                    break;
                case Activity.RESULT_CANCELED:
                {
                    // 传递失败
                    break;
                }
            }
        }
    };

    private BroadcastReceiver smsReceiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            Bundle bundle = intent.getExtras();
            Object[] messages = (Object[]) bundle.get("pdus");
            SmsMessage[] sms = new SmsMessage[messages.length];
            // 为每个收到的PDU创建短信
            for (int n = 0; n < messages.length; n++)
                sms[n] = SmsMessage.createFromPdu((byte[]) messages[n]);
            for (SmsMessage msg : sms)
            {
                // 检查短信是否来自我们已知的发送方
                if (TextUtils.equals(msg.getOriginatingAddress(), SMS_ADDRESS))
                {
                    abortBroadcast();
                    Common.toast(context, "Received message from the motherShip: " + msg.getMessageBody(), Toast.LENGTH_SHORT);
                }
            }
        }
    };

}
