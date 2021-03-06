package com.buildafrique.pn.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buildafrique.pn.app.Fragments.ChangePasswordFrag;
import com.buildafrique.pn.app.Fragments.CodeFrag;
import com.buildafrique.pn.app.Fragments.EnterNumberFrag;
import com.buildafrique.pn.app.Fragments.SignUpFrag;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.FragmentModel;

public class AccountRecovery extends AppCompatActivity implements FragmentModel.FragStateChangeListener {
    private FragmentTransaction transaction;
    private int position = 0;
    private String code, phoneNumber, password, email;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recovery);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        transaction = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
            transaction.add(R.id.frame, new EnterNumberFrag()
                    , "Account Recovery").commit();

        }
        FragmentModel.getInstance().setListener(this);
    }

    @Override
    public void nextFrag(int number) {
        position = number;
        transaction = getSupportFragmentManager().beginTransaction();
        switch (number) {
            case 0:
                transaction.replace(R.id.frame, new EnterNumberFrag(), "Account Recovery").commit();
                transaction.addToBackStack(null);
                break;
            case 1:
                transaction.replace(R.id.frame, new ChangePasswordFrag(), "Change Password").commit();
                transaction.addToBackStack(null);
                break;
            case 2:
                Intent intent = new Intent(getBaseContext(), Login.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void nextFrag(String name, String email, String number, String password) {

    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
