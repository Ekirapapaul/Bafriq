package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
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
import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.Models.Bonus;
import com.buildafrique.pn.app.Models.Commission;
import com.buildafrique.pn.app.Models.ConversionRate;
import com.buildafrique.pn.app.Models.EmailMessage;
import com.buildafrique.pn.app.Models.EmailMessage_Table;
import com.buildafrique.pn.app.Models.Lead;
import com.buildafrique.pn.app.Models.Lead_Table;
import com.buildafrique.pn.app.Models.Loyalty;
import com.buildafrique.pn.app.Models.SalesOrder;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.buildafrique.pn.app.adapters.EmailAdapter;
import com.buildafrique.pn.app.adapters.LeadsAdapter;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Home activity class.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView qualified, won, lost;
    ProgressDialog progressDialog;
    TextView tvCommission, tvBonus, tvLoyalty;
    Account account;
    long numQualified = 0;
    NavigationView navigationView;
    private boolean leadsFinished = false;
    private boolean dashBoardFinished = false;
    private boolean inboxFinished = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        account = SQLite.select().from(Account.class).querySingle();
        registerViews();
        if (account != null) {
            Log.d("account", account.getUsername() + account.getPassword());
            submitLogin(account.getUsername(), account.getPassword());
        } else {
            error();
        }
    }

    private void registerViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(true);
        progressDialog.setTitle("Fetching User details");
        progressDialog.setMessage("Please Wait...");

        tvBonus = findViewById(R.id.tv_bonuses);
        tvCommission = findViewById(R.id.tv_commission);
        tvLoyalty = findViewById(R.id.tv_loyalty);

        qualified = findViewById(R.id.tv_qualified_val);
        won = findViewById(R.id.tv_won_val);
        lost = findViewById(R.id.tv_lost_val);

        numQualified = numQualified + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Qualified")).and(Lead_Table.type.eq("opportunity")).count()
                + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Negotiation")).and(Lead_Table.type.eq("opportunity")).count()
                + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Quotation (Proposition)")).and(Lead_Table.type.eq("opportunity")).count();

        qualified.setText(String.valueOf(numQualified));
        won.setText(String.valueOf(new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("WON Opportunity")).count()));
        lost.setText(String.valueOf(new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("LOST Opportunity")).count()));
        setDrawer();

        findViewById(R.id.menu_commissions).setOnClickListener(this);
        findViewById(R.id.menu_bonuses).setOnClickListener(this);
        findViewById(R.id.menu_loyalty).setOnClickListener(this);

        findViewById(R.id.card_lost).setOnClickListener(this);
        findViewById(R.id.card_qualified).setOnClickListener(this);
        findViewById(R.id.card_won).setOnClickListener(this);

    }

    private void setDrawer() {
        android.support.v7.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });
        navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        TextView navUsername = (TextView) headerView.findViewById(R.id.tv_email);
        headerView.findViewById(R.id.img_profile).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getBaseContext(), ProfileActivity.class));
            }
        });
        navUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getBaseContext(), ProfileActivity.class));
            }
        });
        headerView.findViewById(R.id.img_spinner).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.closeDrawer(GravityCompat.START);
                startActivity(new Intent(getBaseContext(), ProfileActivity.class));
            }
        });
        if(account != null) {
            navUsername.setText(account.getUsername());
        }else{
            navUsername.setText("User Name");
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawer(GravityCompat.START);
                switch (item.getItemId()) {
                    case R.id.nav_compose:
                        startActivity(new Intent(getBaseContext(), Compose.class));
                        break;
                    case R.id.nav_inbox:
                        startActivity(new Intent(getBaseContext(), InboxActivity.class));
                        break;
                    case R.id.nav_archive:
                        startActivity(new Intent(getBaseContext(), ArchiveActivity.class));
                        break;
                    case R.id.nav_products:
                        startActivity(new Intent(getBaseContext(), ResourcesActivity.class));
                        break;
                    case R.id.nav_invite:
                        startActivity(new Intent(getBaseContext(), InviteFriends.class));
                        break;
                    case R.id.nav_customers:
                        startActivity(new Intent(getBaseContext(), CustomerActivity.class));
                        break;
                    case R.id.nav_lost:
                        startLeadsFilter("LOST Opportunity");
                        break;
                    case R.id.nav_qualified:
                        startLeadsFilter("Qualified");
                        break;
                    case R.id.nav_won:
                        startLeadsFilter("WON Opportunity");
                        break;
                    case R.id.nav_leads:
                        startActivity(new Intent(getBaseContext(), LeadsActivity.class));
                        break;
                    case R.id.nav_sales:
                        startActivity(new Intent(getBaseContext(), SalesOrderActivity.class));
                        break;
                }
                return false;
            }
        });

        long inbox = 0;
        List<EmailMessage> messages = SQLite.select().from(EmailMessage.class).queryList();
        long leads = 0;
        long won = 0;
        long lost = 0;
        long inProgress = 0;
        //A very unclean way to do this
        try {
            inbox = new Select(Method.count()).from(EmailMessage.class).where(EmailMessage_Table.to_read.eq(true)).count();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        try {
            leads = new Select(Method.count()).from(Lead.class).count();
            won = new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("WON Opportunity")).count();
            lost = new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("LOST Opportunity")).count();
            inProgress = numQualified;
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_inbox).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.inbox), inbox));
        menu.findItem(R.id.nav_qualified).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.qualified), inProgress));
        menu.findItem(R.id.nav_won).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.won), won));
        menu.findItem(R.id.nav_lost).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.lost), lost));
        //menu.findItem(R.id.nav_pkg_manage).setVisible(false);//In case you want to remove menu item
    }

    private void startLeadsFilter(String filter) {
        Intent intent = new Intent(getBaseContext(), OpportunitiesActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("filter", filter);
        intent.putExtra("filter", bundle);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                break;
            case R.id.compose:
                startActivity(new Intent(getBaseContext(), CustomerActivity.class));
                break;
            case R.id.inbox:
                startActivity(new Intent(getBaseContext(), InboxActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Log out?");
        builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                new SessionManager(getBaseContext()).setLoggedIn(false);
                try {
                    Delete.table(Lead.class);
//                    Delete.table(SalesOrder.class);
                    Delete.table(EmailMessage.class);
                    Delete.table(Commission.class);
                    Delete.table(Loyalty.class);
                    Delete.table(Bonus.class);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent intent = new Intent(getBaseContext(), WelcomeActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);

            }
        });
        builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.menu_bonuses:
                startActivity(new Intent(getBaseContext(), BonusesDetails.class));
                break;
            case R.id.menu_loyalty:
                startActivity(new Intent(getBaseContext(), PointsDetails.class));
                break;
            case R.id.menu_commissions:
                startActivity(new Intent(getBaseContext(), CommissionsDetails.class));
                break;
            case R.id.card_lost:
                startLeadsFilter("LOST Opportunity");
                break;
            case R.id.card_qualified:
                startLeadsFilter("Qualified");
                break;
            case R.id.card_won:
                startLeadsFilter("WON Opportunity");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu, menu);
        return true;
    }

    private void getDashboard(String uid) throws JSONException {
        JSONObject jsonObject = RequestBuilder.getDashboard();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.getDashboardUrl(uid), jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("result") && response.getJSONObject("result").has("bonus")) {
                        JSONObject result = response.getJSONObject("result");
                        try {
                            Delete.table(Commission.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Delete.table(Loyalty.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        try {
                            Delete.table(Bonus.class);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        Gson gson = new Gson();
                        Commission commission = gson.fromJson(result.getJSONObject("commission").toString(), Commission.class);
                        Bonus bonus = gson.fromJson(result.getJSONObject("bonus").toString(), Bonus.class);
                        Loyalty loyalty = gson.fromJson(result.getJSONObject("loyalty").toString(), Loyalty.class);
                        commission.save();
                        bonus.save();
                        loyalty.save();
                        finishFetching();
                        populate();
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
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void getConversionRate() throws JSONException {
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
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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
                headers.put("Cookie", sessionManager.getCookie());
                return headers;
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }

    private void populate() {
        Bonus bonus = SQLite.select().from(Bonus.class).querySingle();
        if (bonus != null) {
            tvBonus.setText(String.format(Locale.getDefault(), "KES %d", bonus.getAvailable()));
        }
        Commission commission = SQLite.select().from(Commission.class).querySingle();
        if (commission != null) {
            tvCommission.setText(String.format(Locale.getDefault(), "KES %d", commission.getAvailable()));
        }
        Loyalty loyalty = SQLite.select().from(Loyalty.class).querySingle();
        if (loyalty != null) {
            tvLoyalty.setText(String.format(Locale.getDefault(), "%d", loyalty.getAvailable()));
        }
        dashBoardFinished = true;
        finishFetching();
    }

    private void finishFetching() {
        if (dashBoardFinished && leadsFinished &&  inboxFinished)
            if (progressDialog != null) progressDialog.dismiss();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }

    private void submitLogin(final String username, final String password) {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        try {
            JSONObject jsonObject = RequestBuilder.LoginRequest(username, password);
            Log.d("Json ", jsonObject.toString());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    Account account = SQLite.select().from(Account.class).querySingle();
                    try {
                        getConversionRate();
                        fetchLeads();
                        fetchMessages();
                        getDashboard(Objects.requireNonNull(account).getUid());
                        finishFetching();
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
            VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void fetchLeads() {
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
        populateLeads();
    }

    private void populateLeads() {
        numQualified = 0;
        numQualified = numQualified + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Qualified")).and(Lead_Table.type.eq("opportunity")).count()
                + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Negotiation")).and(Lead_Table.type.eq("opportunity")).count()
                + new Select(Method.count()).from(Lead.class).where(Lead_Table.stage_id.eq("Quotation (Proposition)")).and(Lead_Table.type.eq("opportunity")).count();

        qualified.setText(String.valueOf(numQualified));
        won.setText(String.valueOf(new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("WON Opportunity")).count()));
        lost.setText(String.valueOf(new Select(Method.count()).from(Lead.class).where(Lead_Table.type.eq("opportunity")).and(Lead_Table.stage_id.eq("LOST Opportunity")).count()));

        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_qualified).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.qualified), numQualified));
        menu.findItem(R.id.nav_won).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.won), Integer.valueOf(won.getText().toString())));
        menu.findItem(R.id.nav_lost).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.lost), Integer.valueOf(lost.getText().toString())));
        leadsFinished = true;
        finishFetching();
    }

    private void fetchMessages() {
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        StringBuilder builder = new StringBuilder();
        List<EmailMessage> messages = SQLite.select().from(EmailMessage.class).queryList();
        for (int i = 0; i < messages.size(); i++) {
            builder.append(messages.get(i).getId());
            if ((i + 1) != messages.size()) {
                builder.append(",");
            }
        }
        String existing_ids = builder.toString();
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

    private void parseMessages(JSONArray jsonArray) {
        Gson gson = new Gson();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                EmailMessage emailMessage = gson.fromJson(jsonObject.toString(), EmailMessage.class);
                emailMessage.save();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }
        inboxFinished = true;
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_inbox).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.inbox), new Select(Method.count()).from(EmailMessage.class).where(EmailMessage_Table.to_read.eq(true)).count()));
        finishFetching();
    }

}
