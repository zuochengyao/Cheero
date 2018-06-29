package com.icheero.app.activity.network;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import android.preference.PreferenceManager;
import android.widget.ImageView;

import java.io.FileInputStream;

public class DownloadActivity extends Activity
{
    private static final String DL_ID = "downloadId";
    private static final String RESOURCE_URL = "http://www.bigfoto.com/dog-animal.jpg";

    private SharedPreferences prefs;
    private DownloadManager dm;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        imageView = new ImageView(this);
        setContentView(imageView);

        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        if (!prefs.contains(DL_ID))
        {
            Uri resource = Uri.parse(RESOURCE_URL);
            DownloadManager.Request request = new DownloadManager.Request(resource);
            // 设置允许的连接来处理下载
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
            request.setAllowedOverRoaming(false);
            // 在通知栏上显示
            request.setTitle("Download Sample");
            long id = dm.enqueue(request);
            prefs.edit().putLong(DL_ID, id).apply();
        }
        else
            queryDownloadStatus();
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(receiver);
    }

    private void queryDownloadStatus()
    {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(prefs.getLong(DL_ID, 0));
        Cursor c = dm.query(query);
        if (c.moveToFirst())
        {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status)
            {
                case DownloadManager.STATUS_PAUSED:
                case DownloadManager.STATUS_PENDING:
                case DownloadManager.STATUS_RUNNING:
                {
                    break;
                }
                case DownloadManager.STATUS_SUCCESSFUL:
                {
                    try
                    {
                        ParcelFileDescriptor file = dm.openDownloadedFile(prefs.getLong(DL_ID, 0));
                        FileInputStream fis = new ParcelFileDescriptor.AutoCloseInputStream(file);
                        imageView.setImageBitmap(BitmapFactory.decodeStream(fis));
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    break;
                }
                case DownloadManager.STATUS_FAILED:
                {
                    // 清除下载并稍后重试
                    dm.remove(prefs.getLong(DL_ID, 0));
                    prefs.edit().clear().apply();
                    break;
                }
            }
        }
    }

    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            queryDownloadStatus();
        }
    };
}
