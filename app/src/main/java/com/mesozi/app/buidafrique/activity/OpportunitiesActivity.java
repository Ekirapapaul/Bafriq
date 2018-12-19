package com.mesozi.app.buidafrique.activity;

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
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.Lead_Table;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.LeadsAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

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
public class OpportunitiesActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private List<Lead> leads = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView placeholder;
    String filter = "Qualified";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_opportunities);
        Bundle b = getIntent().getBundleExtra("filter");
        filter = b.getString("filter");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar.setTitle((filter.contains("WON") ? filter : String.format("%s Opportunity", filter)));
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Leads");
        progressDialog.setMessage("Please Wait...");

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


        fetchLeads();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Lead lead = leads.get(position);
                Intent intent = new Intent(getBaseContext(), LeadDetailsActivity.class);
                intent.putExtra("parcel_data", lead);
                startActivity(intent);
            }
        }));
    }

    private void fetchLeads() {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("session", sessionManager.getCookie());
        try {
            JSONObject jsonObject = RequestBuilder.readLeads();
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
        leads = SQLite.select().from(Lead.class).where(Lead_Table.stage_id.eq(filter)).and(Lead_Table.type.eq("opportunity")).queryList();
        recyclerView.setAdapter(new LeadsAdapter(getBaseContext(), leads));
    }

    private void parseLeads(JSONArray jsonArray) {
        Gson gson = new Gson();
        Log.d("array size", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.d("Processing ", "" + i);
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    Lead lead = gson.fromJson(jsonObject.toString(), Lead.class);
                    lead.save();

                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Failed at", String.format(" with %s", jsonObject.toString()));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (progressDialog != null) progressDialog.dismiss();
        if (filter.equals("Qualified")) {
            leads = SQLite.select().from(Lead.class).where(Lead_Table.stage_id.eq(filter)).and(Lead_Table.type.eq("opportunity")).queryList();
            leads.addAll(SQLite.select().from(Lead.class).where(Lead_Table.stage_id.eq("Negotiation")).and(Lead_Table.type.eq("opportunity")).queryList());
            leads.addAll(SQLite.select().from(Lead.class).where(Lead_Table.stage_id.eq("Quotation (Proposition)")).and(Lead_Table.type.eq("opportunity")).queryList());
        } else {
            leads = SQLite.select().from(Lead.class).where(Lead_Table.stage_id.eq(filter)).and(Lead_Table.type.eq("opportunity")).queryList();
        }
        recyclerView.setAdapter(new LeadsAdapter(getBaseContext(), leads));
        Log.d("leads size", String.valueOf(leads.size()));
    }
}
