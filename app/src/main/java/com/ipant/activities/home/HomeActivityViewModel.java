package com.ipant.activities.home;

import android.app.Application;
import android.support.annotation.NonNull;

import com.ipant.activities.CustomAndroidViewModel;
import com.ipant.network_communication.NetworkConn;
import com.ipant.utils.AppConstants;

public class HomeActivityViewModel extends CustomAndroidViewModel {
    private NetworkConn.onRequestResponse onRequestResponse;


    public HomeActivityViewModel(@NonNull Application application, NetworkConn.onRequestResponse onRequestResponse) {
        super(application);
        this.onRequestResponse = onRequestResponse;


    }

    public void getWalletBalance() {
        NetworkConn networkConn = NetworkConn.getInstance();
        networkConn.makeRequest(networkConn.createGetRequest(AppConstants.CHECK_CURRENT_WALLET_BALANCE), "wallet_current_balance", onRequestResponse);
    }


}
