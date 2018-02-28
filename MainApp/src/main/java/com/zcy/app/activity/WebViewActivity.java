package com.zcy.app.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class WebViewActivity extends Activity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        WebView webView = new WebView(this);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(mClient);
        webView.loadUrl("https://www.baidu.com/");
        setContentView(webView);
    }

    private WebViewClient mClient = new WebViewClient()
    {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request)
        {
            Uri uri = request.getUrl();
            if (TextUtils.equals(uri.getAuthority(), "www.google.com"))
                return false;
            Toast.makeText(WebViewActivity.this, "Sorry, buddy!", Toast.LENGTH_SHORT).show();
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon)
        {
            Uri uri = Uri.parse(url);
            if (TextUtils.equals(uri.getAuthority(), "www.google.com"))
                super.onPageStarted(view, url, favicon);
            else
            {
                Toast.makeText(WebViewActivity.this, "Sorry, buddy!", Toast.LENGTH_SHORT).show();
                view.stopLoading();
            }
        }
    };
}
