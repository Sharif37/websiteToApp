package com.example.websiteToApp;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CustomWebViewClient extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        // Load the URL within the WebView
        view.loadUrl(url);
        // Return true to indicate that the host application handles the URL
        return true;
    }
}

