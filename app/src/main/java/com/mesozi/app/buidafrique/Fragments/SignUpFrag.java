package com.mesozi.app.buidafrique.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.DatabaseInit;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.activity.Login;
import com.mesozi.app.buidafrique.activity.MainActivity;
import com.mesozi.app.buidafrique.activity.SignUp;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class SignUpFrag extends Fragment {
    private View v;
    private EditText location, occupation;
    private ProgressDialog progressDialog;
    private String name, email, phone, password;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_create_account, container, false);
        name = ((SignUp) Objects.requireNonNull(getActivity())).getName();
        email = ((SignUp) Objects.requireNonNull(getActivity())).getEmail();
        phone = ((SignUp) Objects.requireNonNull(getActivity())).getNumber();
        password = ((SignUp) Objects.requireNonNull(getActivity())).getPassword();

        registerViews();
        return v;
    }

    private void registerViews() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Signing up...");
        progressDialog.setMessage("Please wait");
        progressDialog.setCancelable(false);

        v.findViewById(R.id.btn_create_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    submitLogin("admin", "admin");
                }
            }
        });
        location = v.findViewById(R.id.et_location);
        occupation = v.findViewById(R.id.et_occupation);
    }

    private boolean check() {
        if (location.getText().toString().isEmpty()) {
            location.requestFocus();
            location.setError(getString(R.string.error_required));
            return false;
        } else if (occupation.getText().toString().isEmpty()) {
            occupation.requestFocus();
            occupation.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    private void createAffiliate() throws JSONException {
        final SessionManager sessionManager = new SessionManager(getContext());
        JSONObject jsonObject = RequestBuilder.registerAffiliateJson(name, email,"", phone, password, location.getText().toString(), occupation.getText().toString());
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_CREATE_AFFILIATE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("id")) {
                    Toast.makeText(getContext(), "Affiliate successfully registered", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                    if (progressDialog != null) progressDialog.dismiss();

                    sessionManager.setLoggedIn(true);
                    startActivity(intent);
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
                headers.put("Cookie", sessionManager.getCookie());
                return headers;
            }
        };
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void submitLogin(final String username, final String password) {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getContext());
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
                            if (results.get("username") instanceof Boolean && !results.getBoolean("username")) {
                                progressDialog.cancel();
                            } else {

                                String sessionId = results.getString("session_id");
                                sessionManager.setKeyBearerToken(sessionId);
                                createAffiliate();
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
            VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void finishActivity() {
        if (progressDialog != null) progressDialog.dismiss();
        DatabaseInit.buildCategoriesDB();
        Intent intent = new Intent(getContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }
}
