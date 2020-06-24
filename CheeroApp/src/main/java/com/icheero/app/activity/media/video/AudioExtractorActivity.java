package com.icheero.app.activity.media.video;

import android.media.MediaExtractor;
import android.media.MediaFormat;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.util.FileUtils;
import com.icheero.sdk.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AudioExtractorActivity extends BaseActivity
{
    private final String PATH_VIDEO = FileUtils.DIR_PATH_CHEERO_VIDEOS + "jctq.mp4";
    private final String PATH_AUDIO = FileUtils.DIR_PATH_CHEERO_AUDIOS + "jctq.aac";

    private final int PROFILE = 2; // AAC LC
    private final int FREQ_INDEX = 4; // 标识 44100
    private final int CHANNEL_CFG = 2; // 音频声道数为两个

    @BindView(R.id.btn_extract)
    Button mBtnExtract;
    @BindView(R.id.btn_play)
    Button mBtnPlay;

    private MediaExtractor mExtractor;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio_extractor);
        doInitView();
    }

    private void doInitView()
    {
        ButterKnife.bind(this);
        mExtractor = new MediaExtractor();
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mExtractor != null)
            mExtractor.release();
    }

    @OnClick({R.id.btn_extract, R.id.btn_play})
    public void onViewClicked(View view)
    {
        switch (view.getId())
        {
            case R.id.btn_extract:
                extractVideo();
                break;
            case R.id.btn_play:
                break;
        }
    }

    private void extractVideo()
    {
        File videoFile = new File(PATH_AUDIO);
        if (videoFile.exists())
            videoFile.delete();
        try
        {
            // 设置视频源路径
            mExtractor.setDataSource(PATH_VIDEO);
            // 获取所有轨道数
            int trackCount = mExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++)
            {
                MediaFormat format = mExtractor.getTrackFormat(i);
                String mime = format.getString(MediaFormat.KEY_MIME);
                Log.i(TAG, "Mime: " + mime);

                // Log.i(TAG, mExtractor.getSampleSize()+"");api28
                assert mime != null;
                if (mime.startsWith("audio"))
                {
                    Log.i(TAG, "Channel Count: " + format.getInteger(MediaFormat.KEY_CHANNEL_COUNT));
                    Log.i(TAG, "Sample Rate: " + format.getInteger(MediaFormat.KEY_SAMPLE_RATE));
                    Log.i(TAG, "Key Duration: " + format.getLong(MediaFormat.KEY_DURATION));

                    Log.i(TAG, "Sample Flags: " + mExtractor.getSampleFlags());
                    Log.i(TAG, "Sample Time: " + mExtractor.getSampleTime());
                    Log.i(TAG,  "Sample Track index: " + mExtractor.getSampleTrackIndex());
                    mExtractor.selectTrack(i);
                    ByteBuffer byteBuffer = ByteBuffer.allocate(100 * 1024);
                    FileOutputStream fos = new FileOutputStream(videoFile, true);
                    while (true)
                    {
                        int readSampleCount = mExtractor.readSampleData(byteBuffer, 0);
                        if (readSampleCount < 0)
                            break;
                        byte[] buf = new byte[readSampleCount];
                        byteBuffer.get(buf);
                        // ADTS header占7个字节
                        byte[] newBuf = new byte[readSampleCount + 7];
                        addADTSHeader(newBuf, readSampleCount + 7);
                        System.arraycopy(buf, 0, newBuf, 7, readSampleCount);
                        fos.write(newBuf);
                        byteBuffer.clear();
                        mExtractor.advance();
                    }
                    fos.flush();
                    fos.close();
                    break;
                }
            }
        }
        catch (IOException e)
        {
            Log.i(TAG, "未找到路径视频内容");
        }
    }

    private void addADTSHeader(byte[] packet, int packetLen)
    {
        // fill in ADTS data
        packet[0] = (byte) 0xFF;//1111 1111
        packet[1] = (byte) 0xF9;//1111 1001  1111 还是syncword
        // 1001 第一个1 代表MPEG-2,接着00为常量，最后一个1，标识没有CRC

        packet[2] = (byte) (((PROFILE - 1) << 6) + (FREQ_INDEX << 2) + (CHANNEL_CFG >> 2));
        packet[3] = (byte) (((CHANNEL_CFG & 3) << 6) + (packetLen >> 11));
        packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
        packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
        packet[6] = (byte) 0xFC;
    }
}