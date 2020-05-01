package com.rathore.newsweathertraffic;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;

public class WebViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        Intent intent = getIntent();
        WebView myWebView = (WebView) findViewById(R.id.webview);
        if(intent!=null&&intent.getAction().equals("ScreenSlidePageFragment")){
        String url = intent.getStringExtra("readmore");
        myWebView.loadUrl(url);
    }
    }
}
