package com.ipant.activities.webview;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ipant.R;
import com.ipant.RequestResultInterface;
import com.ipant.activities.CustomAndroidViewModel;
import com.ipant.network_communication.NetworkConn;
import com.ipant.utils.AppConstants;

public class WebViewViewModel extends CustomAndroidViewModel {
    private NetworkConn.onRequestResponse onRequestResponse;
    private RequestResultInterface requestResultInterface;

    private String title = "", url = "";

    public WebViewViewModel(@NonNull Application application, RequestResultInterface requestResultInterface, NetworkConn.onRequestResponse onRequestResponse) {
        super(application);
        this.requestResultInterface = requestResultInterface;
        this.onRequestResponse = onRequestResponse;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public void attemptRequestWebViewUrl() {
        try {

            NetworkConn networkConn = NetworkConn.getInstance();

            if (title.equalsIgnoreCase(getStringFromVM(R.string.txt_privacy_policy))) {
                url = AppConstants.PRIVACY_POLICY_URL;
            } else {
                if (title.equalsIgnoreCase(getStringFromVM(R.string.txt_about_app))) {
                    url = AppConstants.ABOUT_APP_URL;
                } else {
                    if (title.equalsIgnoreCase(getStringFromVM(R.string.txt_terms_and_conditions))) {
                        url = AppConstants.TERMNS_AND_CONDITIONS_URL;
                    }
                }
            }

            networkConn.makeRequest(networkConn.createGetRequest(url), "requestPageUrl", onRequestResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
