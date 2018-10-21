package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mesozi.app.buidafrique.R;

/**
 * Created by ekirapa on 9/13/18 .
 */
public class AddCustomer extends AppCompatActivity {
    EditText name, email, mobile;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Creating Customer");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        setContentView(R.layout.activity_add_customer);
        Toolbar toolbar = findViewById(R.id.toolbar);

        name = findViewById(R.id.et_name);
        email = findViewById(R.id.et_email);
        mobile = findViewById(R.id.et_mobile);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btnn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private boolean check() {
        if (name.getText().toString().isEmpty()) {
            name.requestFocus();
            name.setError(getString(R.string.error_required));
            return false;
        } else if (mobile.getText().toString().isEmpty()) {
            mobile.requestFocus();
            mobile.setError(getString(R.string.error_required));
            return false;
        } else if (email.getText().toString().isEmpty()) {
            email.requestFocus();
            email.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    private void createCustomer() {
        progressDialog.show();
    }

    public void finishCreate() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Customer Created!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }

}
