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

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.EmailMessage;
import com.mesozi.app.buidafrique.Models.EmailMessage_Table;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.ArchiveAdapter;
import com.mesozi.app.buidafrique.adapters.EmailAdapter;
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
 * Created by ekirapa on 7/24/18 .
 */
public class ArchiveActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    private List<EmailMessage> emails = new ArrayList<>();
    private RecyclerView recyclerView;
    private SearchView searchView;
    private TextView placeholder;
    private ArchiveAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_archive);
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching Messages");
        progressDialog.setMessage("Please Wait...");

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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


        fetchMessages();

        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                EmailMessage message = adapter.getEmail(position);
                Intent intent = new Intent(getBaseContext(), InboxItemActivity.class);
                intent.putExtra("parcel_data", message);
                startActivity(intent);
            }
        }));

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getBaseContext(), Compose.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.edit:
                startActivity(new Intent(getBaseContext(), Compose.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void fetchMessages() {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("session", sessionManager.getCookie());

        StringBuilder builder = new StringBuilder();
        List<EmailMessage> messages = SQLite.select().from(EmailMessage.class).queryList();
        for (int i = 0; i < messages.size(); i++) {
            builder.append(messages.get(i).getId());
            if ((i + 1) != messages.size()) {
                builder.append(",");
            }
        }
        String existing_ids = builder.toString();
        Log.d("Existing ids", existing_ids);
        try {
            JSONObject jsonObject;
            if (messages.size() == 0) {
                jsonObject = RequestBuilder.inboxObject();
            } else {
                jsonObject = RequestBuilder.inboxObject(existing_ids);
            }
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_DATASET, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("Response ", response.toString());
                    if (response.has("result")) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("result");
                            parseMessages(jsonArray);
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

                @Override
                protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                    Log.d("response", response.toString());
                    return super.parseNetworkResponse(response);
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

    private void parseMessages(JSONArray jsonArray) {
        Gson gson = new Gson();
        Log.d("array size", String.valueOf(jsonArray.length()));
        for (int i = 0; i < jsonArray.length(); i++) {
            Log.d("Processing ", "" + i);

            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EmailMessage emailMessage = gson.fromJson(jsonObject.toString(), EmailMessage.class);
                emailMessage.save();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        if (progressDialog != null) progressDialog.dismiss();
        emails = SQLite.select().from(EmailMessage.class).where(EmailMessage_Table.to_read.eq(false)).queryList();
        adapter = new ArchiveAdapter(getBaseContext(), emails);
        recyclerView.setAdapter(adapter);
        Log.d("emails size", String.valueOf(emails.size()));
    }
}
