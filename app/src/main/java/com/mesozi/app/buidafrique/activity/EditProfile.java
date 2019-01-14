package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    TextInputEditText location, email, occupation;
    TextInputEditText current, newPassword, confirm;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        setViews();
    }

    private void setViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Updating Profile");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        location = findViewById(R.id.et_location);
        email = findViewById(R.id.et_user_email);
        occupation = findViewById(R.id.et_occupation);

        current = findViewById(R.id.et_current);
        newPassword = findViewById(R.id.et_new);
        confirm = findViewById(R.id.et_confirm);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    try {
                        updateProfile(buildJSON());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error();
                    }
                } else {
                    Toast.makeText(EditProfile.this, "You haven't made any change", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.btn_change).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkPassword()) {

                }
                try {
                    updatePassword(buildPasswordJSON());
                } catch (JSONException e) {
                    e.printStackTrace();
                    error();
                }
            }
        });
    }

    private boolean check() {
        return !location.getText().toString().isEmpty() || !email.getText().toString().isEmpty();
    }

    private boolean checkPassword() {
        if (current.getText().toString().isEmpty()) {
            current.requestFocus();
            current.setError(getString(R.string.error_required));
            return false;
        } else if (newPassword.getText().toString().isEmpty()) {
            newPassword.requestFocus();
            newPassword.setError(getString(R.string.error_required));
            return false;
        } else if (confirm.getText().toString().isEmpty()) {
            confirm.requestFocus();
            confirm.setError(getString(R.string.error_required));
            return false;
        } else if (!newPassword.getText().toString().equals(confirm.getText().toString())) {
            confirm.requestFocus();
            confirm.setError("Passwords Do not Match");
            return false;
        }
        return true;
    }

    private JSONObject buildPasswordJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("method", "call");

        JSONObject params = new JSONObject();
        if (!current.getText().toString().isEmpty()) {
            params.put("old_password", current.getText().toString());
        }
        if (!newPassword.getText().toString().isEmpty()) {
            params.put("password", newPassword.getText().toString());
        }
        if (!confirm.getText().toString().isEmpty()) {
            params.put("password_confirm", confirm.getText().toString());
        }

        jsonObject.put("params", params);

        return jsonObject;
    }

    private JSONObject buildJSON() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("method", "call");

        JSONObject params = new JSONObject();
        if (!location.getText().toString().isEmpty()) {
            params.put("location", location.getText().toString());
        }
        if (!email.getText().toString().isEmpty()) {
            params.put("email", email.getText().toString());
        }
        if (!occupation.getText().toString().isEmpty()) {
            params.put("occupation", occupation.getText().toString());
        }

        jsonObject.put("params", params);

        return jsonObject;
    }

    private void updateProfile(JSONObject jsonObject) {
        progressDialog.setTitle("Updating Profile");
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_EDIT_PROFILE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("error") || response.getJSONObject("result").has("error")) {
                        Toast.makeText(EditProfile.this, response.getJSONObject("result").getString("error"), Toast.LENGTH_LONG).show();
                    } else if (response.has("result")) {
                        finishCreate();
                    } else {
                        error();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    private void updatePassword(JSONObject jsonObject) {
        progressDialog.setTitle("Updating Password");
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_EDIT_PASSWORD, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("error") || response.getJSONObject("result").has("error")) {
                        if (progressDialog != null) progressDialog.dismiss();
                        Toast.makeText(EditProfile.this, response.getJSONObject("result").getString("error"), Toast.LENGTH_LONG).show();
                    } else if (response.has("result")) {
                        finishCreate();
                    } else {
                        error();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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

    public void finishCreate() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Profile Updated!", Toast.LENGTH_LONG).show();
        //DataNotifier.getInstance().dataChanged(1);
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_LONG).show();
    }
}
