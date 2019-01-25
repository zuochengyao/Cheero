package com.icheero.database.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "t_download")
public class Download
{
    @Id
    @Property(nameInDb = "d_id")
    private Long id;
    @Property(nameInDb = "d_position_progress")
    private long progress;
    @Property(nameInDb = "d_position_start")
    private long start;
    @Property(nameInDb = "d_position_end")
    private long end;
    @Property(nameInDb = "d_download_url")
    private String downloadUrl;
    @Property(nameInDb = "d_thread_id")
    private int threadId;

    public Download(Long id, long progress, long start, long end, String downloadUrl, int threadId)
    {
        this.id = id;
        this.progress = progress;
        this.start = start;
        this.end = end;
        this.downloadUrl = downloadUrl;
        this.threadId = threadId;
    }

    public Download()
    {
    }

    public Long getId()
    {
        return this.id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public long getProgress()
    {
        return this.progress;
    }

    public void setProgress(long progress)
    {
        this.progress = progress;
    }

    public long getStart()
    {
        return this.start;
    }

    public void setStart(long start)
    {
        this.start = start;
    }

    public long getEnd()
    {
        return this.end;
    }

    public void setEnd(long end)
    {
        this.end = end;
    }

    public String getDownloadUrl()
    {
        return this.downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl)
    {
        this.downloadUrl = downloadUrl;
    }

    public int getThreadId()
    {
        return this.threadId;
    }

    public void setThreadId(int threadId)
    {
        this.threadId = threadId;
    }

}
