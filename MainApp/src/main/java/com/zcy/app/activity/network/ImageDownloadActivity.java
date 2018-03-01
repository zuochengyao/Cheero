package com.zcy.app.activity.network;

import android.os.Bundle;

import com.zcy.app.R;
import com.zcy.app.activity.base.BaseActivity;
import com.zcy.app.custom.widget.WebImageView;

import butterknife.BindView;

public class ImageDownloadActivity extends BaseActivity
{
    @BindView(R.id.web_image)
    WebImageView mWebImage;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_image_download);
        super.onCreate(savedInstanceState);
        mWebImage.setPlaceholder(R.mipmap.ic_launcher);
        mWebImage.setImageUrl("http://lorempixel.com/400/200");
    }
}
