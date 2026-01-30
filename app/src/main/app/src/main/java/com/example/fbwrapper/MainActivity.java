package com.example.fbwrapper;

import android.app.Activity;
import android.os.Bundle;
import android.os.Message;
import android.webkit.*;

public class MainActivity extends Activity {

    WebView webView;

    @Override
    protected void onCreate(Bundle b) {
        super.onCreate(b);

        webView = new WebView(this);
        setContentView(webView);

        WebSettings s = webView.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setSupportMultipleWindows(true);
        s.setJavaScriptCanOpenWindowsAutomatically(true);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                v.loadUrl(r.getUrl().toString());
                return true;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onCreateWindow(
                    WebView view,
                    boolean dialog,
                    boolean userGesture,
                    Message msg
            ) {
                WebView popup = new WebView(view.getContext());
                popup.getSettings().setJavaScriptEnabled(true);

                popup.setWebViewClient(new WebViewClient() {
                    @Override
                    public boolean shouldOverrideUrlLoading(WebView v, WebResourceRequest r) {
                        view.loadUrl(r.getUrl().toString());
                        return true;
                    }
                });

                WebView.WebViewTransport t =
                        (WebView.WebViewTransport) msg.obj;
                t.setWebView(popup);
                msg.sendToTarget();
                return true;
            }
        });

        webView.loadUrl("https://m.facebook.com");
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack()) webView.goBack();
        else super.onBackPressed();
    }
}
