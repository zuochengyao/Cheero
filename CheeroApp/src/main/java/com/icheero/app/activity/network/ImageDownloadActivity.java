package com.icheero.app.activity.network;

import android.os.Bundle;
import android.widget.ProgressBar;

import com.icheero.app.R;
import com.icheero.app.custom.widget.WebImageView;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.manager.DownloadManager;
import com.icheero.sdk.core.network.listener.IDownloadListener;
import com.icheero.sdk.util.Log;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;

public class ImageDownloadActivity extends BaseActivity
{
    private static final Class<ImageDownloadActivity> TAG = ImageDownloadActivity.class;
    @BindView(R.id.web_image)
    WebImageView mWebImage;
    @BindView(R.id.progress)
    ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_image_download);
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        mProgress.setMax(100);
        mWebImage.setPlaceholder(R.mipmap.ic_launcher);
        // mWebImage.setImageUrl("http://149.129.240.18/common/picView?fileName=qsd_01_fd4c5b3b3cad412a8bfe39810ba6db24_20181120.jpg");
        // rxJava()

        DownloadManager.getInstance().download("http://149.129.240.18/common/picView?fileName=qsd_01_fd4c5b3b3cad412a8bfe39810ba6db24_20181120.jpg", new IDownloadListener()
        {
            @Override
            public void onSuccess(File downloadFile)
            {
                Log.d(TAG, "success: " + downloadFile.getAbsolutePath());
            }

            @Override
            public void onFailure(int errorCode, String errorMsg)
            {
                Log.e(TAG, "error: " +  errorCode + ":" + errorMsg);
            }

            @Override
            public void onProgress(int progress)
            {
                runOnUiThread(() -> mProgress.setProgress(progress));
            }
        });
    }



    private void rxJava()
    {
        Observable.create((ObservableOnSubscribe<Integer>) e -> {
            Log.e(TAG, "Observable emit 1" + "\n");
            e.onNext(1);
            Log.e(TAG, "Observable emit 2" + "\n");
            e.onNext(2);
            Log.e(TAG, "Observable emit 3" + "\n");
            e.onNext(3);
            e.onComplete();
            Log.e(TAG, "Observable emit 4" + "\n");
            e.onNext(4);
        }).subscribe(new Observer<Integer>()
        {
            private int i;
            private Disposable mDisposable;

            @Override
            public void onSubscribe(Disposable d)
            {
                this.mDisposable = d;
            }

            @Override
            public void onNext(Integer integer)
            {
                i++;
                if (i == 2)
                    mDisposable.dispose();
            }

            @Override
            public void onError(Throwable e)
            {
                Log.e(TAG, "onError : value : " + e.getMessage());
            }

            @Override
            public void onComplete()
            {
                Log.e(TAG, "onComplete");
            }
        });
    }
}
