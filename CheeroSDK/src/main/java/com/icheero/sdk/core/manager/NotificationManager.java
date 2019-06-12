package com.icheero.sdk.core.manager;

import android.app.AppOpsManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.icheero.sdk.R;
import com.icheero.sdk.base.BaseApplication;
import com.icheero.sdk.core.xml.ChannelXmlReader;
import com.icheero.sdk.util.RefInvoke;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class NotificationManager
{
    private static final Class TAG = NotificationManager.class;

    private static final String FILED_NAME = "OP_POST_NOTIFICATION";
    private static final String METHOD_NAME = "checkOpNoThrow";
    public static final String CHANNEL_ID_BLOG = "blog";
    public static final String CHANNEL_ID_HOME = "home";

    private android.app.NotificationManager mNotificationManager;
    private Context mContext;

    private static volatile NotificationManager mInstance;

    private NotificationManager()
    {
        this.mContext = BaseApplication.getAppInstance().getApplicationContext();
        this.mNotificationManager = (android.app.NotificationManager) this.mContext.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O)
        {
            ChannelXmlReader reader = new ChannelXmlReader(mContext, R.menu.notification_channel);
            mNotificationManager.createNotificationChannelGroups(reader.getChannelGroups());
            mNotificationManager.createNotificationChannels(reader.getChannels());
        }
    }

    public static NotificationManager getInstance()
    {
        if (mInstance == null)
        {
            synchronized (NotificationManager.class)
            {
                if (mInstance == null)
                    mInstance = new NotificationManager();
            }
        }
        return mInstance;
    }

    private boolean isNotificationEnable()
    {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT_WATCH)
            return true;
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N_MR1)
        {
            AppOpsManager appOps = (AppOpsManager) mContext.getSystemService(Context.APP_OPS_SERVICE);
            ApplicationInfo appInfo = mContext.getApplicationInfo();
            String className = AppOpsManager.class.getName();
            Class[] paramType = new Class[3];
            paramType[0] = Integer.TYPE;
            paramType[1] = Integer.TYPE;
            paramType[2] = String.class;
            Object[] paramValue = new Object[3];
            paramValue[0] = RefInvoke.getFieldObject(className, Integer.class, FILED_NAME);
            paramValue[1] = appInfo.uid;
            paramValue[2] = mContext.getPackageName();
            return ((int) RefInvoke.invokeMethod(AppOpsManager.class.getName(), METHOD_NAME, appOps, paramType, paramValue)) == AppOpsManager.MODE_ALLOWED;
        }
        else
            return NotificationManagerCompat.from(mContext).areNotificationsEnabled();
    }

    public void showNotification(PendingIntent pIntent, CharSequence title, CharSequence content, String tag, int id, String channelId)
    {
        if (!isNotificationEnable())
        {
            Intent intent = new Intent();

            //下面这种方案是直接跳转到当前应用的设置界面。
            //https://blog.csdn.net/ysy950803/article/details/71910806
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", mContext.getPackageName(), null);
            intent.setData(uri);
            mContext.startActivity(intent);
            return;
        }
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            builder = new NotificationCompat.Builder(mContext, null);
        else
            builder = new NotificationCompat.Builder(mContext, channelId);
        builder.setContentIntent(pIntent);
        builder.setSmallIcon(R.mipmap.ic_launcher);
        builder.setLargeIcon(BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.ic_launcher_round));
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setAutoCancel(true);
        mNotificationManager.notify(tag, id, builder.build());
    }
}
