package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.AbstractSalesOrder;
import com.mesozi.app.buidafrique.Models.Account;
import com.mesozi.app.buidafrique.Models.Customer;
import com.mesozi.app.buidafrique.Models.Customer_Table;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.AppConstants;
import com.mesozi.app.buidafrique.Utils.DataNotifier;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.CustomerAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ekirapa on 7/9/18 .
 */
public class CustomerActivity extends AppCompatActivity implements CustomerAdapter.CustomerAdapterListener, DataNotifier.StateChangeListener {
    private List<Customer> customers = new ArrayList<>();
    CustomerAdapter adapter;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView placeholder;
    int selectedId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item);
        customers = SQLite.select()
                .from(Customer.class)
                .where(Customer_Table.email.notEq("false"))
                .queryList();
        ;
        DataNotifier.getInstance().setListener(this);
        registerViews();

    }


    private void registerViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Customers");
        progressDialog.setMessage("Please Wait...");

        placeholder = findViewById(R.id.tv_search_placeholder);
        searchView = findViewById(R.id.search_view);
        searchView.setFocusable(true);// searchView is null
        searchView.setFocusableInTouchMode(true);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchView.setSearchableInfo(Objects.requireNonNull(searchManager)
                .getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted

                adapter.getFilter().filter(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                if(adapter != null && adapter.getFilter() != null)
                adapter.getFilter().filter(query);
                return false;
            }
        });


        placeholder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                placeholder.setVisibility(View.GONE);
                searchView.onActionViewExpanded();
            }
        });


        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Customer customer = adapter.getCustomer(position);
                Intent intent = new Intent(getBaseContext(), CustomerDetails.class);
                intent.putExtra("parcel_data", customer);
                startActivity(intent);
            }
        }));
        fetchCustomers();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), AddCustomer.class));
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startActivity(new Intent(getBaseContext(), AddCustomer.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchCustomers() {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("session", sessionManager.getCookie());
        try {
            Account account = SQLite.select().from(Account.class).querySingle();
            JSONObject jsonObject = RequestBuilder.customersObject(Objects.requireNonNull(account).getUid());
            Log.d("json", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response ", response.toString());
                    if (response.has("result")) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            parseCustomers(jsonArray);
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

    private void parseCustomers(JSONArray jsonArray) {
        Gson gson = new Gson();
        Log.d("array size", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                try {
                    Customer customer = gson.fromJson(jsonObject.toString(), Customer.class);
                    customer.save();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.d("Failed at", String.format(" with %s", jsonObject.toString()));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        if (progressDialog != null) progressDialog.dismiss();

        customers = SQLite.select()
                .from(Customer.class)
                .queryList();
        adapter = new CustomerAdapter(getBaseContext(), customers);
        recyclerView.setAdapter(adapter);
        if (selectedId > 0) {
            Customer customer = SQLite.select().from(Customer.class).where(Customer_Table.id.eq(selectedId)).querySingle();
            if (customer != null) {
                Intent intent = new Intent(getBaseContext(), CustomerDetails.class);
                intent.putExtra("parcel_data", customer);
                startActivity(intent);
            }
        }
    }

    @Override
    public void onContactSelected(Customer customer) {

    }

    @Override
    public void notifyData(int number, int id) {
        selectedId = id;
        if (number == AppConstants.NOTIFY_CUSTOMERS) {
            fetchCustomers();
//            if (recyclerView != null) {
//                adapter = new CustomerAdapter(getBaseContext(), customers);
//                recyclerView.setAdapter(adapter);
//            }
        }
    }
}
