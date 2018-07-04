package com.icheero.app.activity.network;

import android.os.Bundle;

import com.icheero.app.R;
import com.icheero.app.activity.base.BaseActivity;
import com.icheero.app.custom.widget.WebImageView;
import com.icheero.common.util.Log;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImageDownloadActivity extends BaseActivity
{
    private static final Class<ImageDownloadActivity> TAG = ImageDownloadActivity.class;
    @BindView(R.id.web_image)
    WebImageView mWebImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        setContentView(R.layout.activity_image_download);
        super.onCreate(savedInstanceState);
        mWebImage.setPlaceholder(R.mipmap.ic_launcher);
        mWebImage.setImageUrl("http://lorempixel.com/400/200");
        rxJava();
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
