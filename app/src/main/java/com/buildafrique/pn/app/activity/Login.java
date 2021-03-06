package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.Models.ProductCategory;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.DatabaseInit;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.raizlabs.android.dbflow.sql.language.Delete;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
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

        findViewById(R.id.tv_forgot_password).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AccountRecovery.class));
            }
        });
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
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        try {
            JSONObject jsonObject = RequestBuilder.LoginRequest(username, password);
            Log.d("Json ", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    if (response.has("result")) {
                        try {
                            JSONObject results = response.getJSONObject("result");
                            if (results.get("uid") instanceof Boolean && !results.getBoolean("uid")) {
                                etEmail.requestFocus();
                                etEmail.setError(getString(R.string.error_wrong_combinations));
                                progressDialog.cancel();
                            } else {
                                try {
                                    Delete.table(Account.class);
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                                Gson gson = new Gson();
                                Account account = gson.fromJson(results.toString(), Account.class);
                                account.setPassword(password);
                                account.save();
                                sessionManager.setLoggedIn(true);
                                String sessionId = results.getString("session_id");
                                sessionManager.setKeyBearerToken(sessionId);
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

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Log.i("response", response.headers.toString());
                    Map<String, String> responseHeaders = response.headers;
                    String cookie = responseHeaders.get("Set-Cookie");
                    sessionManager.setCookie(cookie);
                    Log.i("cookies", cookie);
                    return super.parseNetworkResponse(response);
                }
            };
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void finishActivity() {
        if (progressDialog != null) progressDialog.dismiss();
        DatabaseInit.buildCategoriesDB();
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
        finishActivity();
    }


}
