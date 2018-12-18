package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.Account;
import com.mesozi.app.buidafrique.R;
import com.raizlabs.android.dbflow.sql.language.SQLite;

/**
 * Created by ekirapa on 12/10/18 .
 */
public class ProfileActivity extends AppCompatActivity {
    TextView email, username;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setViews();
    }

    private void setViews() {
        username = findViewById(R.id.tv_username);
        email = findViewById(R.id.tv_email);

        Account account = SQLite.select().from(Account.class).querySingle();
        if (account != null) {
            username.setText(String.format("Username : %s", account.getUsername()));
            email.setText(String.format("Email : %s", account.getEmail()));
        }
    }
}