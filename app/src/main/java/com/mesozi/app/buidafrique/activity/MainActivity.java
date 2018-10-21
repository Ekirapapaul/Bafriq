package com.mesozi.app.buidafrique.activity;

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
import com.mesozi.app.buidafrique.Models.Account;
import com.mesozi.app.buidafrique.Models.Bonus;
import com.mesozi.app.buidafrique.Models.Commission;
import com.mesozi.app.buidafrique.Models.EmailMessage;
import com.mesozi.app.buidafrique.Models.Lead;
import com.mesozi.app.buidafrique.Models.Loyalty;
import com.mesozi.app.buidafrique.Models.SalesOrder;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.raizlabs.android.dbflow.sql.language.Delete;
import com.raizlabs.android.dbflow.sql.language.Method;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.Select;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/**
 * Home activity class.
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private DrawerLayout drawerLayout;
    private TextView qualified, won, lost;
    ProgressDialog progressDialog;
    TextView tvCommission, tvBonus, tvLoyalty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        registerViews();
        Account account = SQLite.select().from(Account.class).querySingle();
        if(account != null) {
            submitLogin(account.getUsername(), account.getPassword());
        }else {
            error();
        }
    }

    private void registerViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setTitle("Fetching User details");
        progressDialog.setMessage("Please Wait...");

        tvBonus = findViewById(R.id.tv_bonuses);
        tvCommission = findViewById(R.id.tv_commission);
        tvLoyalty = findViewById(R.id.tv_loyalty);

        qualified = findViewById(R.id.tv_qualified_val);
        won = findViewById(R.id.tv_won_val);
        lost = findViewById(R.id.tv_lost_val);

        qualified.setText(String.valueOf(6));
        won.setText(String.valueOf(4));
        lost.setText(String.valueOf(3));
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
        NavigationView navigationView = findViewById(R.id.nav_view);
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
                        startLeadsFilter("Lost");
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
        //A very unclean way to do this
        try {
            inbox = new Select(Method.count()).from(EmailMessage.class).count();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        try {
            leads = new Select(Method.count()).from(Lead.class).count();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        Menu menu = navigationView.getMenu();
        menu.findItem(R.id.nav_inbox).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.inbox), inbox));
        menu.findItem(R.id.nav_qualified).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.qualified), leads));
        menu.findItem(R.id.nav_won).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.won), leads));
        menu.findItem(R.id.nav_lost).setTitle(String.format(Locale.getDefault(), "%s (%d)", getString(R.string.lost), leads));
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
                startActivity(new Intent(getBaseContext(), Compose.class));
                break;
            case R.id.inbox:
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
                startLeadsFilter("Lost");
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

    private void populate() {
        Bonus bonus = SQLite.select().from(Bonus.class).querySingle();
        if (bonus != null) {
            tvBonus.setText(String.format(Locale.getDefault(), "KES %d", bonus.getAvailable()));
        }
        Commission commission = SQLite.select().from(Commission.class).querySingle();
        if (commission != null) {
            tvCommission.setText(String.format(Locale.getDefault(), "KES %d",commission.getAvailable()));
        }
        Loyalty loyalty = SQLite.select().from(Loyalty.class).querySingle();
        if (loyalty != null) {
            tvLoyalty.setText(String.format(Locale.getDefault(), "KES %d", loyalty.getAvailable()));
        }
        finishFetching();
    }

    private void finishFetching() {
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
                        getDashboard(account.getUid());
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

}
