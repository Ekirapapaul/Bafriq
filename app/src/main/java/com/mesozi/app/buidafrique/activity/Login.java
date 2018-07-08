package com.mesozi.app.buidafrique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;

import com.mesozi.app.buidafrique.R;

/**
 * Created by ekirapa on 7/6/18 .
 */
public class Login extends AppCompatActivity {
    private EditText email, password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registterViews();
    }

    private void registterViews() {
        email = findViewById(R.id.et_email);
        password = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean check() {
        if (email.getText().toString().isEmpty()) {
            email.requestFocus();
            email.setError(getString(R.string.error_required));
            return false;
        } else if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            password.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }
}
