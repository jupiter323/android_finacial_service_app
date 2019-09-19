package com.ipant.activities.landing_page;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.ipant.R;
import com.ipant.activities.BaseActivity;
import com.ipant.activities.bank_Id_integration.BankIdLogin;
import com.ipant.activities.login.LoginActivity;
import com.ipant.activities.signup.CompleteProfileActivity;

public class LandingActivity extends BaseActivity {

    public static Intent getInstance(Context context) {
        Intent intent=new Intent(context, LandingActivity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    protected int getLayoutResourceId() {
        return R.layout.activity_landing;
    }

    @Override
    protected Toolbar getToolbar() {
        return null;
    }

    @Override
    protected boolean toolbarHomeEnable() {
        return false;
    }

    @Override
    protected boolean toolbarTitleEnable() {
        return false;
    }

    public void  loginClick(View view){
        Intent intent=new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void  signUpClick(View view){
        /*Intent intent=new Intent(this, BankIdLogin.class);*/

        Intent intent=new Intent(this, CompleteProfileActivity.class);
        startActivity(intent);
    }
}
