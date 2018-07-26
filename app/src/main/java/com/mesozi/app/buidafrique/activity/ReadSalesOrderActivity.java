package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.SalesOrder;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.ReadSalesOrderAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class ReadSalesOrderActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private List<SalesOrder> salesOrders = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView placeholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leads);
        TextView title = findViewById(R.id.tv_title);
        title.setText("Sales Orders");

        placeholder = findViewById(R.id.tv_search_placeholder);
        searchView = findViewById(R.id.search_view);
        searchView.setFocusable(true);// searchView is null
        searchView.setFocusableInTouchMode(true);

        placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeholder.setVisibility(View.GONE);
                searchView.onActionViewExpanded();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Sales Orders");
        progressDialog.setMessage("Please Wait...");

        fetchLeads();
//        fetch();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
    }

    private void fetchLeads() {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("session", sessionManager.getCookie());
        try {
            JSONObject jsonObject = RequestBuilder.salesRequest();
            Log.i("json ", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response ", response.toString());
                    if (response.has("result")) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            parseLeads(jsonArray);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            error();
                        }
                    } else if (response.has("error")) {
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
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void finishFetchLeads() {
        if (progressDialog != null) progressDialog.dismiss();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }

    private void parseLeads(JSONArray jsonArray) {
        Gson gson = new Gson();
        Log.d("array size", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                if (jsonObject.get("message_follower_ids") instanceof Boolean) {
                    jsonObject.remove("message_follower_ids");
                } else if (jsonObject.get("order_line") instanceof Boolean) {
                    jsonObject.remove("order_line");
                } else if ((jsonObject.get("currency_id") instanceof Boolean && !jsonObject.getBoolean("currency_id")) || jsonObject.get("currency_id").toString().equals("false")) {
                    jsonObject.remove("currency_id");
                } else if (jsonObject.get("write_uid") instanceof Boolean) {
                    jsonObject.remove("write_uid");
                } else if (jsonObject.get("message_follower_ids") instanceof Boolean) {
                    jsonObject.remove("message_follower_ids");
                }else if (jsonObject.get("invoice_ids") instanceof Boolean) {
                    jsonObject.remove("invoice_ids");
                }else if (jsonObject.get("partner_id") instanceof Boolean) {
                    jsonObject.remove("partner_id");
                }else if (jsonObject.get("company_id") instanceof Boolean) {
                    jsonObject.remove("company_id");
                }else if (jsonObject.get("pricelist_id") instanceof Boolean) {
                    jsonObject.remove("pricelist_id");
                }else if (jsonObject.get("message_follower_ids") instanceof Boolean) {
                    jsonObject.remove("message_follower_ids");
                }else if (jsonObject.get("payment_term") instanceof Boolean) {
                    jsonObject.remove("payment_term");
                }

                try {
                    SalesOrder salesOrder = gson.fromJson(jsonObject.toString(), SalesOrder.class);
                    salesOrders.add(salesOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (progressDialog != null) progressDialog.dismiss();
        recyclerView.setAdapter(new ReadSalesOrderAdapter(getBaseContext(), salesOrders));
        Log.d("salesOrders size", String.valueOf(salesOrders.size()));
    }
}
