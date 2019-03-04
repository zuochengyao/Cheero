package com.icheero.app.activity.network;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;

import com.icheero.app.R;
import com.icheero.sdk.base.BaseActivity;
import com.icheero.sdk.core.api.CheeroApi;
import com.icheero.sdk.core.api.FaceIDApi;
import com.icheero.sdk.core.manager.IOManager;
import com.icheero.sdk.core.network.listener.IResponseListener;
import com.icheero.sdk.util.Log;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestActivity extends BaseActivity
{
    private static final String BASE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String PATH_DETECT = BASE_PATH + "/tencent/MicroMsg/WeiXin/mmexport1532420385035.jpg";
    private static final String PATH_IDCARD_FRONT_0 = BASE_PATH + "/tencent/MicroMsg/WeiXin/mmexport1532576175564.jpg";
    private static final String PATH_IDCARD_FRONT_90 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_171947.jpg";
    private static final String PATH_IDCARD_FRONT_180 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_174229.jpg";
    private static final String PATH_IDCARD_FRONT_270 = BASE_PATH + "/tencent/MicroMsg/WeiXin/IMG_20180808_174451.jpg";
    private static final String PATH_IDCARD_BACK = BASE_PATH + "/tencent/MicroMsg/WeiXin/mmexport1532593355508.jpg";
    private static final String PATH_BANKCARD = BASE_PATH + "/DCIM/Camera/IMG_20180727_180356.jpg";

    @BindView(R.id.request_hello_btn)
    Button mHello;
    @BindView(R.id.request_idcard_ocr_btn)
    Button mIDCardOCR;

    private Key mFaceIDConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        ButterKnife.bind(this);
        CheeroApi.getFaceIDConfig("http://10.155.2.130:8080/cheero/getFaceIDConfig.action", new IResponseListener<Key>()
        {
            @Override
            public void onSuccess(Key data)
            {
                mFaceIDConfig = data;
                Log.i(TAG, "success:[" + data.toString() + "]");
            }

            @Override
            public void onFailure(int errorCode, String errorMessage)
            {
                Log.i(TAG, "error:[" + errorCode + ":" + errorMessage + "]");
            }
        });
    }

    @OnClick({R.id.request_hello_btn, R.id.request_idcard_ocr_btn})
    public void OnSendRequest(View v)
    {
        switch (v.getId())
        {
            case R.id.request_hello_btn:
            {
                CheeroApi.hello("http://10.155.2.130:8080/cheero/hello.action", "Cheero", "123456", new IResponseListener<Person>()
                {
                    @Override
                    public void onSuccess(Person data)
                    {
                        Log.i(TAG, "success:[" + data.toString() + "]");
                    }

                    @Override
                    public void onFailure(int errorCode, String errorMessage)
                    {
                        Log.i(TAG, "error:[" + errorCode + ":" + errorMessage + "]");
                    }
                });
                break;
            }
            case R.id.request_idcard_ocr_btn:
            {
                byte[] imageRef = IOManager.getInstance().bitmapToByte(BitmapFactory.decodeResource(getResources(), R.drawable.me));
                File file = new File(PATH_DETECT);
                if (file.exists())
                {
                    FaceIDApi.detect("https://api.faceid.com/faceid/v1/detect", mFaceIDConfig.key, mFaceIDConfig.secret, imageRef, "1", new IResponseListener()
                    {
                        @Override
                        public void onSuccess(Object data)
                        {
                            Log.i(TAG, "success:[" + data.toString() + "]");
                        }

                        @Override
                        public void onFailure(int errorCode, String errorMessage)
                        {
                            Log.i(TAG, "error:[" + errorCode + ":" + errorMessage + "]");
                        }
                    });
                }
                break;
            }
        }
    }

    public class Key
    {
        private String id;
        private String key;
        private String secret;
        private int userId;

        public String getId()
        {
            return id;
        }

        public void setId(String id)
        {
            this.id = id;
        }

        public String getKey()
        {
            return key;
        }

        public void setKey(String key)
        {
            this.key = key;
        }

        public String getSecret()
        {
            return secret;
        }

        public void setSecret(String secret)
        {
            this.secret = secret;
        }

        public int getUserId()
        {
            return userId;
        }

        public void setUserId(int userId)
        {
            this.userId = userId;
        }

        @Override
        public String toString()
        {
            return "FaceID {key=" + key + "," + "secret=" + secret + "}";
        }
    }

    class Person
    {
        public int id;
        public String username;
        public String password;
        public short type;

        @Override
        public String toString()
        {
            return "Person {id=" + id + "," + "username=" + username + "," + "password=" + password + "," + "type=" + type + "}";
        }
    }
}
