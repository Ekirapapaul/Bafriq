package com.mesozi.app.buidafrique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.mesozi.app.buidafrique.Fragments.CreateAcountFrag;
import com.mesozi.app.buidafrique.Fragments.SignUpFrag;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.FragmentModel;

import java.util.Objects;

/**
 * Created by ekirapa on 7/6/18 .
 */
public class SignUp extends AppCompatActivity implements FragmentModel.FragStateChangeListener {
    private FragmentTransaction transaction;
    private int position = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        transaction = getSupportFragmentManager().beginTransaction();

        if (savedInstanceState == null) {
            transaction.add(R.id.frame, new CreateAcountFrag()
                    , "Create Account").commit();

        }
        FragmentModel.getInstance().setListener(this);
    }

    @Override
    public void nextFrag(int number) {
        position = number;
        transaction = getSupportFragmentManager().beginTransaction();
        switch (number) {
            case 0:
                transaction.replace(R.id.frame, new CreateAcountFrag(), "Create Account").commit();
                transaction.addToBackStack(null);
                break;
            case 1:
                transaction.replace(R.id.frame, new SignUpFrag(), "SignUp").commit();
                transaction.addToBackStack(null);
                break;
            case 2:
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
                break;
            default:
                finish();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (position > 0) {
            getSupportFragmentManager().popBackStack();
            position = 0;
        } else {
            super.onBackPressed();
        }

    }
}
