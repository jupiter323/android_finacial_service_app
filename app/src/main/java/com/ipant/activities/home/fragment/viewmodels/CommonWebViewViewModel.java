package com.ipant.activities.home.fragment.viewmodels;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ipant.R;
import com.ipant.activities.CustomAndroidViewModel;
import com.ipant.network_communication.NetworkConn;
import com.ipant.utils.AppConstants;

public class CommonWebViewViewModel extends CustomAndroidViewModel {
    private NetworkConn.onRequestResponse onRequestResponse;


    private String title = "", url = "";

    public CommonWebViewViewModel(@NonNull Application application, NetworkConn.onRequestResponse onRequestResponse) {
        super(application);
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
                    } else {
                        if(title.equalsIgnoreCase(getStringFromVM(R.string.txt_faq))){
                            url = AppConstants.FAQ_URL;
                        }
                    }
                }
            }

            networkConn.makeRequest(networkConn.createGetRequest(url), "requestPageUrl", onRequestResponse);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
