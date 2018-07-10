package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekirapa on 7/6/18 .
 */
public class Login extends AppCompatActivity {
    private EditText etEmail, etPassword;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        registterViews();
    }

    private void registterViews() {
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);

        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check())
                    submitLogin(etEmail.getText().toString(), etPassword.getText().toString());
            }
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Signing in...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);
    }

    private boolean check() {
        if (etEmail.getText().toString().isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError(getString(R.string.error_required));
            return false;
        } else if (etPassword.getText().toString().isEmpty()) {
            etPassword.requestFocus();
            etPassword.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    private void submitLogin(final String username, final String password) {
        try {
            JSONObject jsonObject = RequestBuilder.LoginRequest(username, password);
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    if (response.has("result")) {
                        try {
                            JSONObject results = response.getJSONObject("result");
                            if (!results.getBoolean("username")) {
                                etEmail.requestFocus();
                                etEmail.setError(getString(R.string.error_wrong_combinations));
                            } else {
                                Toast.makeText(Login.this, "Login Successfull", Toast.LENGTH_SHORT).show();
                                finishActivity();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error();
                        }
                    } else {
                        error();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    error.printStackTrace();
                    error();
                }
            }) {
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    return headers;
                }
            };
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void finishActivity() {
        if (progressDialog != null) progressDialog.dismiss();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }
}
