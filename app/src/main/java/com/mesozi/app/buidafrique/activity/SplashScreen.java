package com.mesozi.app.buidafrique.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.PromoMessage;
import com.mesozi.app.buidafrique.Models.RefferalMessage;
import com.mesozi.app.buidafrique.Models.ShareMessage;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekirapa on 10/23/18 .
 */
public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        SessionManager sessionManager = new SessionManager(getBaseContext());
        if (!sessionManager.isLoggedIn()) {
            try {
                getRefferalMessage();
                getMessages();
            } catch (JSONException e) {
                e.printStackTrace();

            }
        } else {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }

    }

    private void getMessages() throws JSONException {
        JSONObject jsonObject = new JSONObject("{\n" +
                "  \"jsonrpc\":\"2.0\",\n" +
                "  \"method\":\"call\"\n" +
                "}");
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_PROMOTION_MESSAGES, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("result")) {
                    try {
                        parseMessages(response.getJSONArray("result"));
                        getShareMessage();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        try {
                            getShareMessage();
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                            moveNext();
                        }
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                moveNext();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getShareMessage() throws JSONException {
        JSONObject jsonObject = RequestBuilder.getShareMessage();
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_GET_SHARE_MESSAGE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("result")) {
                    try {
                        parseShareMessages(response.getJSONArray("result"));
                        moveNext();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        moveNext();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                moveNext();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getRefferalMessage() throws JSONException {
        JSONObject jsonObject = RequestBuilder.getShareMessage();
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_GET_REFFERAL_MESSAGE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("result")) {
                    try {
                        parseRefferalMessages(response.getJSONArray("result"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void parseRefferalMessages(JSONArray jsonArray) {
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                RefferalMessage refferalMessage = gson.fromJson(jsonObject.toString(), RefferalMessage.class);
                refferalMessage.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseMessages(JSONArray jsonArray) {
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                PromoMessage promoMessage = gson.fromJson(jsonObject.toString(), PromoMessage.class);
                promoMessage.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void parseShareMessages(JSONArray jsonArray) {
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                ShareMessage shareMessage = gson.fromJson(jsonObject.toString(), ShareMessage.class);
                shareMessage.save();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void moveNext() {
        Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
