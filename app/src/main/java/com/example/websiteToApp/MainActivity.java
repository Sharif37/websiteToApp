package com.example.websiteToApp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;
public class MainActivity extends AppCompatActivity implements TextToSpeech.OnInitListener {
    private WebView webView;
    private TextToSpeech textToSpeech;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webview);
        configureWebView();

        // Initialize Text-to-Speech engine
        textToSpeech = new TextToSpeech(this, this);

        // Load the website URL into the WebView
        webView.loadUrl("https://www.facebook.com/");
    }

    // Configure WebView settings
    @SuppressLint("SetJavaScriptEnabled")
    private void configureWebView() {
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);

        // Set WebViewClient to handle page loading events
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                // Execute JavaScript to trigger TTS on the website
                String javascript = "document.getElementById('play').click();";
                webView.evaluateJavascript(javascript, null);

                // Extract text content from the webpage and speak it
                webView.evaluateJavascript(
                        "(function() { return document.body.innerText; })();",
                        new ValueCallback<String>() {
                            @Override
                            public void onReceiveValue(String value) {
                                speakText(value);
                            }
                        }
                );
            }
        });
    }

    // Method to handle text-to-speech functionality
    private void speakText(String text) {
        if (textToSpeech != null && !text.isEmpty()) {
            textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
        }
    }

    // Release TTS engine resources
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            // TTS engine initialized successfully
        } else {
            // TTS initialization failed
        }
    }
}
