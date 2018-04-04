package com.example.bubba.web13api23;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity {
    WebView web;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        web=(WebView) findViewById(R.id.web);
        ToggleButton toggle=(ToggleButton) findViewById(R.id.togle);
        toggle.setOnCheckedChangeListener(new Escucha());

        web.setWebViewClient(new Callback());
        WebSettings pref=web.getSettings();
        pref.setBuiltInZoomControls(true);
    }

    private class Callback extends WebViewClient{
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request){
            return false;
        }
    }
    private class Escucha implements CompoundButton.OnCheckedChangeListener{

        @Override
        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
            if (b){
                web.loadUrl("https://www.google.com.sv/");
            }else{
                web.loadUrl("https://www.facebook.com/");
            }
        }
    }
}
