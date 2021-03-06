package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.buildafrique.pn.app.Models.ConversionRate;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ekirapa on 10/29/18 .
 */
public class ConvertCommission extends AppCompatActivity {
    private EditText etAmount;
    private TextView tvPoints;
    private int CONVERSION_RATE = 1000;
    ProgressDialog progressDialog;
    String option = "commission";
    private ConversionRate conversionRate;
    TextView tvVRate;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Converting to loyalty points");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        try {
            getConversionRate();
        } catch (JSONException e) {
            e.printStackTrace();
            showAlertDialog("FAILED TO GET RATE","Failed to get conversion Rate from the server. You may not see the correct Conversion on your phone but you can still proceed.");
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Bundle bundle = getIntent().getBundleExtra("option");
        if (bundle != null) {
            option = bundle.getString("option");
        }


        etAmount = findViewById(R.id.et_amount);
        tvPoints = findViewById(R.id.tv_points);
        tvVRate = findViewById(R.id.tv_conversion_rate);

        try {
            conversionRate = SQLite.select().from(ConversionRate.class).querySingle();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (conversionRate != null) {
            if (option.equals("bonus")) {
                tvVRate.setText(conversionRate.getBonus_to_loyalty());
                CONVERSION_RATE = Integer.parseInt(conversionRate.getBonus_to_loyalty());
            } else {
                tvVRate.setText(conversionRate.getCommission_to_loyalty());
                CONVERSION_RATE = Integer.parseInt(conversionRate.getCommission_to_loyalty());
            }
        }

        etAmount.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence != null) {
                    try {
                        int amount = Integer.parseInt(String.valueOf(charSequence));
                        int points = amount / CONVERSION_RATE;
                        tvPoints.setText(String.valueOf(points));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        findViewById(R.id.btn_convert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etAmount.getText().toString().isEmpty()) {
                    etAmount.requestFocus();
                    etAmount.setError("Enter an amount to redeem");
                } else {
                    try {
                        convertToLoyalty(buildJSOn());
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error();
                    }
                }
            }
        });
    }

    private JSONObject buildJSOn() throws JSONException {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("jsonrpc", "2.0");
        jsonObject.put("method", "call");

        JSONObject params = new JSONObject();
        params.put("amount", Integer.valueOf(etAmount.getText().toString()));
        jsonObject.put("params", params);

        jsonObject.put("id", 123);
        return jsonObject;
    }

    private void convertToLoyalty(JSONObject jsonObject) {
        progressDialog.setTitle("Converting to loyalty points");
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        String url = UrlsConfig.convertToloyaltyPoints(option);
        Log.d("url", url);
        Log.d("json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("result")) {
                        finishCreate();
                    } else if (response.has("error")) {
                        error();
                    } else if (response.getJSONObject("result").has("error")) {
                        error(response.getJSONObject("result").getString("error"));
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

    public void finishCreate() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Conversion to Loyalty Points Successfull!", Toast.LENGTH_LONG).show();
        //DataNotifier.getInstance().dataChanged(1);
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_LONG).show();
    }

    private void error(String error) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }


    private void getConversionRate() throws JSONException {
        progressDialog.setTitle("Fetching Conversion Rate");
        progressDialog.show();
        JSONObject jsonObject = RequestBuilder.getConversioRate();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_GET_CONVERSION_RATES, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("result")) {
                        JSONObject result = response.getJSONObject("result");
                        try {
                            Delete.table(ConversionRate.class);
                            Gson gson = new Gson();
                            ConversionRate conversionRate = gson.fromJson(result.toString(), ConversionRate.class);
                            conversionRate.save();
                            if (option.equals("bonus")) {
                                tvVRate.setText(conversionRate.getBonus_to_loyalty());
                                CONVERSION_RATE = Integer.parseInt(conversionRate.getBonus_to_loyalty());
                            } else {
                                tvVRate.setText(conversionRate.getCommission_to_loyalty());
                                CONVERSION_RATE = Integer.parseInt(conversionRate.getCommission_to_loyalty());
                            }
                            if (progressDialog != null) progressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            showAlertDialog("FAILED TO GET RATE","Failed to get conversion Rate from the server. You may not see the correct Conversion on your phone but you can still proceed.");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showAlertDialog("FAILED TO GET RATE","Failed to get conversion Rate from the server. You may not see the correct Conversion on your phone but you can still proceed.");
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                showAlertDialog("FAILED TO GET RATE","Failed to get conversion Rate from the server. You may not see the correct Conversion on your phone but you can still proceed.");
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("Cookie", sessionManager.getCookie());
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void showAlertDialog(String title, String message) {
        if (progressDialog != null) progressDialog.dismiss();
        AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(ConvertCommission.this)).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
        alertDialog.show();
    }

}
