package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
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
import com.google.gson.JsonObject;
import com.buildafrique.pn.app.Models.AbstractSalesOrder;
import com.buildafrique.pn.app.Models.SalesOrder;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.RecyclerItemClickListener;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.buildafrique.pn.app.adapters.ReadSalesOrderAdapter;
import com.buildafrique.pn.app.adapters.SalesOrderAdapter;

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
public class SalesOrderActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private List<AbstractSalesOrder> salesOrders = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView placeholder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_orders);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Sales Orders");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        fetchSalesOrders();
//        fetch();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AbstractSalesOrder salesOrder = salesOrders.get(position);
                Intent intent = new Intent(getBaseContext(), SalesOrderDetails.class);
                intent.putExtra("parcel_data", salesOrder);
                startActivity(intent);
            }
        }));
    }

    private void fetchSalesOrders() {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("session", sessionManager.getCookie());
        try {
            JSONObject jsonObject = RequestBuilder.salesOrders();
            Log.i("json ", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response ", response.toString());
                    if (response.has("result")) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            parseSalesOrders(jsonArray);
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

    private void parseSalesOrders(JSONArray jsonArray) {
        Gson gson = new Gson();
        Log.d("array size", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = null;
            try {
                jsonObject = jsonArray.getJSONObject(i);
                try {
                    AbstractSalesOrder salesOrder = gson.fromJson(jsonObject.toString(), AbstractSalesOrder.class);
                    salesOrders.add(salesOrder);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
        if (progressDialog != null) progressDialog.dismiss();
        recyclerView.setAdapter(new SalesOrderAdapter(getBaseContext(), salesOrders));
        Log.d("salesOrders size", String.valueOf(salesOrders.size()));
    }
}
