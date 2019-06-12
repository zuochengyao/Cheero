package com.icheero.sdk.core.xml;

import android.app.NotificationChannel;
import android.app.NotificationChannelGroup;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;

import com.icheero.sdk.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class ChannelXmlReader extends AbsXmlReader
{
    private List<NotificationChannelGroup> mChannelGroups;
    private List<NotificationChannel> mChannels;
    private int mChannelGroupIndex = 0;

    public ChannelXmlReader(Context context, int menuRes)
    {
        super(context, menuRes);
    }

    public List<NotificationChannelGroup> getChannelGroups()
    {
        return mChannelGroups;
    }

    public List<NotificationChannel> getChannels()
    {
        return mChannels;
    }

    @Override
    protected void init()
    {
        mChannelGroups = new ArrayList<>();
        mChannels = new ArrayList<>();
    }

    @Override
    protected void parseGroupStart(AttributeSet attrs)
    {
        TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.channel);
        String id = array.getString(R.styleable.channel_groupId);
        String name = array.getString(R.styleable.channel_channelName);
        NotificationChannelGroup channelGroup = new NotificationChannelGroup(id, name);
        mChannelGroups.add(channelGroup);
        array.recycle();
    }

    @Override
    protected void parseGroupEnd(AttributeSet attrs)
    {
        mChannelGroupIndex++;
    }

    @Override
    protected void parseItemStart(AttributeSet attrs)
    {
        if (mChannelGroups != null && mChannelGroups.size() > 0)
        {
            NotificationChannelGroup channelGroup = mChannelGroups.get(mChannelGroupIndex);
            TypedArray array = mContext.obtainStyledAttributes(attrs, R.styleable.channel);
            String id = array.getString(R.styleable.channel_channelId);
            String name = array.getString(R.styleable.channel_channelName);
            int importance = array.getInt(R.styleable.channel_importance, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationChannel channel = new NotificationChannel(id, name, importance);
            channel.setGroup(channelGroup.getId());
            mChannels.add(channel);
            array.recycle();
        }
    }

    @Override
    protected void parseItemEnd(AttributeSet attrs)
    {

    }
}
