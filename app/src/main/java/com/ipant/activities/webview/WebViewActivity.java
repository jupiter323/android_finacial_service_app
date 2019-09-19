package com.ipant.activities.webview;

import android.app.Dialog;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.google.gson.Gson;
import com.ipant.R;
import com.ipant.RequestResultInterface;
import com.ipant.activities.BaseActivity;
import com.ipant.activities.webview.bean.CommonBean;
import com.ipant.databinding.ActivityWebViewBinding;
import com.ipant.network_communication.NetworkConn;
import com.ipant.utils.AppDialogs;

public class WebViewActivity extends BaseActivity implements NetworkConn.onRequestResponse, RequestResultInterface {
    private ActivityWebViewBinding activityWebViewBinding;
    private WebViewViewModel webViewViewModel;
    private Dialog dialogLoader;

    public static Intent getInstance(Context context, Bundle bundle) {
        Intent intent = new Intent(context, WebViewActivity.class);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        webViewViewModel = ViewModelProviders.of(this, new WebViewViewModelFactory(getApplication(), this, this)).get(WebViewViewModel.class);
        activityWebViewBinding.setViewModel(webViewViewModel);
        activityWebViewBinding.wv.getSettings().setJavaScriptEnabled(true);
        activityWebViewBinding.wv.setWebViewClient(new MyWebClient());

        getIntentData();
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected Toolbar getToolbar() {
        activityWebViewBinding = (ActivityWebViewBinding) getBindingObject();
        return activityWebViewBinding.layoutAppbar.toolbarView;
    }

    @Override
    protected boolean toolbarHomeEnable() {
        return true;
    }

    @Override
    protected boolean toolbarTitleEnable() {
        return false;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void getIntentData() {
        Bundle bundle = getIntent().getExtras();
        String title = bundle.getString("title");
        webViewViewModel.setTitle(title);

        activityWebViewBinding.layoutAppbar.toolbarTitle.setText(title);
        dialogLoader=AppDialogs.getInstance().showLoader(this);
        webViewViewModel.attemptRequestWebViewUrl();

    }

    @Override
    public void startNewActivity(Bundle bundle, Class newClass) {

    }

    @Override
    public void onSucess(String eventType) {

    }

    @Override
    public void onRequestRequirementFail(String failureMsg) {

    }

    @Override
    public void onResponse(String response, String eventType) {


        if (eventType.equalsIgnoreCase("requestPageUrl")) {
            CommonBean commonBean = new Gson().fromJson(response.toString(), CommonBean.class);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    AppDialogs.getInstance().hideLoader(dialogLoader);
                    activityWebViewBinding.wv.loadUrl(commonBean.getData().get(0).getUrl());
                }
            });
        }

    }

    @Override
    public void onNoNetworkConnectivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppDialogs.getInstance().hideLoader(dialogLoader);
                noNetworkConnectionDialog();
            }
        });
    }

    @Override
    public void onRequestRetry() {

    }

    @Override
    public void onRequestFailed(String msg) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppDialogs.getInstance().hideLoader(dialogLoader);
                appUtil.showMessageError(WebViewActivity.this, activityWebViewBinding.coordinatorLayout, msg);
            }
        });

    }


    @Override
    public void onSessionExpire() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppDialogs.getInstance().hideLoader(dialogLoader);
                sessionExpireError();
            }
        });

    }

    @Override
    public void onAppHardUpdate() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AppDialogs.getInstance().hideLoader(dialogLoader);
                updateAppVersion();
            }
        });

    }

    public class MyWebClient extends WebViewClient {
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            // TODO Auto-generated method stub
            super.onPageStarted(view, url, favicon);
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            // TODO Auto-generated method stub

            view.loadUrl(url);
            return true;

        }
    }
}
