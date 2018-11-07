package com.icheero.app.activity.network;

import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.icheero.sdk.util.Common;
import com.icheero.sdk.util.Log;

public class WebViewActivity extends Activity
{
    private static final Class<WebViewActivity> TAG = WebViewActivity.class;

    private static final String WEB_BAIDU_URL = "https://www.baidu.com/";
    private static final String WEB_ASSETS_URL = "file:///android_asset/web/index.html";
    private static final String WEB_GOOGLE_URL = "https://www.google.com";

    private static final String JS_SETELEMENT = "javascript:document.getElementById('%s').value='%s'";
    private static final String JS_GETELEMENT = "javascript:window.BRIDGE.storeElement('%s', document.getElementById('%s').value)";

    private static final int TYPE_NETWORK = 0;
    private static final int TYPE_ASSETS = 1;

    private static final String KEY_ELEMENT_ID = "emailAddress";
    private static final String KEY_BRIDGE = "BRIDGE";

    private int mType = TYPE_ASSETS;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        webView.setWebViewClient(mClient);
        webView.addJavascriptInterface(new MyJavaScriptInterface(), KEY_BRIDGE);
        if (mType == TYPE_ASSETS)
            webView.loadUrl(WEB_ASSETS_URL);
        else if (mType == TYPE_NETWORK)
            webView.loadUrl(WEB_BAIDU_URL);
        setContentView(webView);
    }

    private WebViewClient mClient = new WebViewClient()
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            Log.d(TAG, "WebViewClient shouldOverrideUrlLoading mType = " + mType);
            if (mType == TYPE_NETWORK)
            {
                Uri uri = request.getUrl();
                if (TextUtils.equals(uri.getAuthority(), WEB_GOOGLE_URL))
                    return false;
                Common.toast(WebViewActivity.this, "Sorry, buddy!", Toast.LENGTH_SHORT);
            }
            else if (mType == TYPE_ASSETS)
            {
                view.loadUrl(String.format(JS_GETELEMENT, KEY_ELEMENT_ID, KEY_ELEMENT_ID));
                return false;
            }
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            Log.d(TAG, "WebViewClient onPageStarted mType = " + mType);
            Uri uri = Uri.parse(url);
            if (mType == TYPE_NETWORK)
            {
                if (TextUtils.equals(uri.getAuthority(), "www.google.com"))
                    super.onPageStarted(view, url, favicon);
                else
                {
                    Common.toast(WebViewActivity.this, "Sorry, buddy!", Toast.LENGTH_SHORT);
                    view.stopLoading();
                }
            }
            else if (mType == TYPE_ASSETS)
                view.loadUrl(String.format(JS_GETELEMENT, KEY_ELEMENT_ID, KEY_ELEMENT_ID));
        }

        @Override
        public void onPageFinished(WebView view, String url)
        {
            Log.d(TAG, "WebViewClient onPageFinished mType = " + mType);
            SharedPreferences sp = getPreferences(Activity.MODE_PRIVATE);
            view.loadUrl(String.format(JS_SETELEMENT, KEY_ELEMENT_ID, sp.getString(KEY_ELEMENT_ID, "")));
        }
    };

    private class MyJavaScriptInterface
    {
        @JavascriptInterface
        public void storeElement(String id, String element)
        {
            Log.d(TAG, "MyJavaScriptInterface storeElement mType = " + mType);
            SharedPreferences.Editor editor = getPreferences(Activity.MODE_PRIVATE).edit();
            editor.putString(id, element);
            editor.commit();
            if (!TextUtils.isEmpty(element))
                Common.toast(WebViewActivity.this, element, Toast.LENGTH_SHORT);
        }
    }
}
