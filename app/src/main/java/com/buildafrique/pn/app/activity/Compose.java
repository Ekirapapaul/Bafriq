package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.R;
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
 * Compose message activity.
 */
public class Compose extends AppCompatActivity {
    EditText etFrom, subject, message;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.compose_activity);
        registerViews();
    }

    private void registerViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Sending Message");
        progressDialog.setMessage("Please Wait...");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        TextView from = findViewById(R.id.tv_from);
        from.setText("From : partnernetwork@buildafrique.com");
        etFrom = findViewById(R.id.et_from);
        message = findViewById(R.id.et_message);
        subject = findViewById(R.id.et_subject);


        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    String sub = ((subject.getText().toString().isEmpty()) ? " " : subject.getText().toString());
                    Account account = SQLite.select().from(Account.class).querySingle();
                    if (account != null) {
                        try {
                            JSONObject jsonObject = RequestBuilder.createMessge(sub, message.getText().toString());
                            sendMessage(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error();
                        }

                    } else {
                        error();
                    }
                }
            }
        });


    }

    private void sendMessage(JSONObject jsonObject) {
        progressDialog.show();
        Log.d("json", jsonObject.toString());
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_CREATE_MESSAGE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("error")) {
                    error();
                } else if (response.has("result")) {
                    finishSending();
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
                headers.put("Content-Type", "application/json");
                headers.put("Cookie", sessionManager.getCookie());
                return headers;
            }
        };
        jsonObjectRequest.setShouldCache(false);
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);

    }

    private boolean check() {
        if (message.getText().toString().isEmpty()) {
            message.requestFocus();
            message.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.compose_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.send:
                if (check()) {
                    String sub = ((subject.getText().toString().isEmpty()) ? " " : subject.getText().toString());
                    Account account = SQLite.select().from(Account.class).querySingle();
                    if (account != null) {
                        try {
                            JSONObject jsonObject = RequestBuilder.createMessge(sub, message.getText().toString());
                            sendMessage(jsonObject);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error();
                        }

                    } else {
                        error();
                    }
                }
                break;
            case R.id.discard:
                break;
            case R.id.save_draft:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void finishSending() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Message Sent Successfully", Toast.LENGTH_LONG).show();
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }
}
