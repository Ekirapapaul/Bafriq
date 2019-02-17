package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
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
import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.DataNotifier;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
                if (check()) {
                    try {
                        Account account = SQLite.select().from(Account.class).querySingle();
                        JSONObject jsonObject = RequestBuilder.createCustomer(name.getText().toString(), name.getText().toString(), email.getText().toString(), "", mobile.getText().toString(), account.getUid());
                        createCustomer(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
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
        }
        return true;
    }

    private void createCustomer(JSONObject jsonObject) {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.has("result")) {
                        int id = response.getInt("result");
                        finishCreate(id);
                    } else if (response.has("error") || response.getJSONObject("result").has("error")) {
                        Toast.makeText(AddCustomer.this, response.getJSONObject("result").getString("error"), Toast.LENGTH_LONG).show();
                    } else {
                        error();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                headers.put("Cookie", sessionManager.getCookie());
                return headers;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void finishCreate(int id) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Customer Created successfully!", Toast.LENGTH_LONG).show();
        DataNotifier.getInstance().dataChanged(1,id);
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_LONG).show();
    }

}
