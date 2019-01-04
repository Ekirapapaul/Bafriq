package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.Customer_Table;
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.Lead_Table;
import com.mesozi.app.buidafrique.Utils.AppConstants;
import com.mesozi.app.buidafrique.Utils.DataNotifier;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerAdapter;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerEmailAdapter;
import com.mesozi.app.buidafrique.adapters.CustomSpinnerNumberAdapter;
import com.mesozi.app.buidafrique.adapters.CustomerAdapter;
import com.mesozi.app.buidafrique.adapters.LeadsAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import com.mesozi.app.buidafrique.Models.Customer;
import com.mesozi.app.buidafrique.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ekirapa on 9/25/18 .
 */
public class AddLead extends AppCompatActivity {
    private Customer customer;
    private EditText etName, etDescription, etCustName, etMobile, etEmail;
    SearchableSpinner searchableSpinner, spinnerNumber, spinnerEmail;
    List<Customer> customersEmail;
    List<Customer> customersPhone;
    List<Customer> customers;
    TextView textView;
    private ProgressDialog progressDialog;
    int selectedId = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_lead);

        if (getIntent().getParcelableExtra("parcel_data") != null) {
            customer = getIntent().getParcelableExtra("parcel_data");
        }
        setViews();
    }

    private void setViews() {
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
        progressDialog.setTitle("Creating Lead");
        progressDialog.setMessage("Please Wait...");

        textView = findViewById(R.id.tv_cust_details);
        etName = findViewById(R.id.et_name);
        etDescription = findViewById(R.id.et_description);
        etCustName = findViewById(R.id.et_cust_name);
        etMobile = findViewById(R.id.et_cust_mobile);
        etEmail = findViewById(R.id.et_cust_email);

        searchableSpinner = findViewById(R.id.spinner_name);
        searchableSpinner.setTitle("Select Customer");
        customers = SQLite.select().from(Customer.class).queryList();
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(AddLead.this, android.R.layout.simple_spinner_item, customers);
        searchableSpinner.setAdapter(adapter);
        searchableSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customers.size()) {
                    customer = customers.get(i);
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        if (customer != null) {
            if (customer.getName() != null && !customer.getName().isEmpty()) {
                etCustName.setText(customer.getName());
                int position = customers.indexOf(customer);
                searchableSpinner.setSelection(position);
            }
            if (customer.getMobile() != null && !customer.getMobile().isEmpty()) {
                etMobile.setText(customer.getMobile());
            }
            if (customer.getEmail() != null && !customer.getEmail().isEmpty()) {
                etEmail.setText(customer.getEmail());
            }
        }

        spinnerNumber = findViewById(R.id.spinner_mobile);
        spinnerNumber.setTitle("Select Customer");
        customersPhone = SQLite.select().from(Customer.class).queryList();
        CustomSpinnerNumberAdapter customSpinnerNumberAdapter = new CustomSpinnerNumberAdapter(AddLead.this, android.R.layout.simple_spinner_item, customers);
        spinnerNumber.setAdapter(customSpinnerNumberAdapter);
        spinnerNumber.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customers.size()) {
                    customer = customers.get(i);
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spinnerEmail = findViewById(R.id.spinner_email);
        spinnerEmail.setTitle("Select Customer");
        customersEmail = SQLite.select().from(Customer.class).queryList();
        CustomSpinnerEmailAdapter customSpinnerEmailAdapter = new CustomSpinnerEmailAdapter(AddLead.this, android.R.layout.simple_spinner_item, customers);
        spinnerEmail.setAdapter(customSpinnerEmailAdapter);
        spinnerEmail.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i < customersEmail.size()) {
                    customer = customers.get(i);
                    Log.d("customer", customer.getName());
                    setCustomer(customer);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        setCustomer(customer);

        findViewById(R.id.btn_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    try {
                        JSONObject jsonObject = RequestBuilder.createLeads(etName.getText().toString(), etName.getText().toString(), etDescription.getText().toString(), customer.getEmail(), customer.getName(), customer.getEmail(), customer.getMobile(), customer.getId());
                        createLead(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    private void setCustomer(Customer customer) {
//        if (customersEmail.contains(customer)) {
//            spinnerEmail.setSelection(customersEmail.indexOf(customer));
//        }
//        if (customers.contains(customer)) {
//            Toast.makeText(this, "Found at " + customers.indexOf(customer), Toast.LENGTH_SHORT).show();
//            searchableSpinner.setSelection(customers.indexOf(customer));
//        }
//        if (customersPhone.contains(customer)) {
//            spinnerNumber.setSelection(customersPhone.indexOf(customer));
//        }
        Log.d("position", " pos " + customers.indexOf(customer));
        spinnerEmail.setSelection(customers.indexOf(customer));
        searchableSpinner.setSelection(customers.indexOf(customer));
        spinnerNumber.setSelection(customers.indexOf(customer));

    }

    private boolean check() {
        if (etName.getText().toString().isEmpty()) {
            etName.requestFocus();
            etName.setError(getString(R.string.error_required));
            return false;
        } else if (customer == null) {
            textView.requestFocus();
            textView.setError(getString(R.string.select_customer));
            return false;
        }
        return true;
    }

    private void createLead(JSONObject jsonObject) {
        Log.d("json", jsonObject.toString());
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("result")) {
                    try {
                        selectedId = response.getInt("result");
                        fetchLeads();
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
                Log.d("error","error occured");
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


    public void finishSending(int id) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Lead Created Successfully", Toast.LENGTH_LONG).show();
        DataNotifier.getInstance().dataChanged(AppConstants.NOTIFY_LEADS, id);
        finish();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
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
            jsonObjectRequest.setShouldCache(false);
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }
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
        if (selectedId > 0) {
            Lead lead = SQLite.select().from(Lead.class).where(Lead_Table.id.eq(selectedId)).querySingle();
            Intent intent = new Intent(getBaseContext(), LeadDetailsActivity.class);
            intent.putExtra("parcel_data", lead);
            if (progressDialog != null) progressDialog.dismiss();
            Toast.makeText(this, "Lead Created Successfully", Toast.LENGTH_LONG).show();
            DataNotifier.getInstance().dataChanged(AppConstants.NOTIFY_LEADS, selectedId);
            startActivity(intent);

            finish();
        }else {
            finishSending(0);
        }
    }
}
