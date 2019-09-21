package com.ipant.activities.add_money;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.ipant.BuildConfig;
import com.ipant.R;
import com.ipant.activities.home.HomeActivity;
import com.ipant.utils.AppConstants;
import com.trustly.library.TrustlyJavascriptInterface;

import java.util.TimeZone;


public class DepositMoneyWebViewActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_money_web_view);
        // Get WebView & WebSettings
        WebView myWebView = (WebView) findViewById(R.id.wv);
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("reloaded", "your current url when webpage loading.. finish" + url);
                if (url.contains("fail.html")){
                    Toast.makeText(DepositMoneyWebViewActivity.this, "Din transaktion misslyckades!",
                            Toast.LENGTH_LONG).show();

                    Intent intent = new Intent(DepositMoneyWebViewActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else if(url.contains("success.html")){
                    Toast.makeText(DepositMoneyWebViewActivity.this, "Transaktion har genomförts",
                            Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(DepositMoneyWebViewActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                return super.shouldOverrideUrlLoading(view, url);
            }
        });
        WebSettings webSettings = myWebView.getSettings();
        myWebView.setHorizontalScrollBarEnabled(false);
        myWebView.setVerticalScrollBarEnabled(false);
        // Enable javascript and DOM Storage
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);

        // Add TrustlyJavascriptInterface
        myWebView.addJavascriptInterface(
                new TrustlyJavascriptInterface(this), TrustlyJavascriptInterface.NAME);

        String mobile_no =  AppConstants.USER_DATA_BEAN_OBJ == null ? "" : "" + AppConstants.USER_DATA_BEAN_OBJ.getData().get(0).getMobileNo();
        String email = AppConstants.USER_DATA_BEAN_OBJ == null ? "placeholder@mailinator.com" : "" + AppConstants.USER_DATA_BEAN_OBJ.getData().get(0).getEmail()!=null? AppConstants.USER_DATA_BEAN_OBJ.getData().get(0).getEmail():"placeholder@mailinator.com";
        String appv = BuildConfig.VERSION_NAME;
        String auth = AppConstants.USER_DATA_BEAN_OBJ == null ? "" : "" + AppConstants.USER_DATA_BEAN_OBJ.getData().get(0).getAuthorization();
        String timezone = TimeZone.getDefault().getID();
        String lan = AppConstants.LANGUAGE;
        String fcmt = AppConstants.FCM_TOKEN;


        String url = "https://admin.ipant.se/paymentserver/example/www/deposit.php?enduserid="+mobile_no+"&auth="+auth+"&appv="+appv+"&timezone="+timezone+"&lan="+lan+"&dev=android"+"&fcmt="+fcmt+"&email="+email;
        Log.d("sendurl:", url);
        myWebView.loadUrl(url);


    }
}
