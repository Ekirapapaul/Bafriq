package com.mesozi.app.buidafrique.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.google.gson.Gson;
import com.mesozi.app.buidafrique.Models.Account;
import com.mesozi.app.buidafrique.Models.Commission;
import com.mesozi.app.buidafrique.Models.Loyalty;
import com.mesozi.app.buidafrique.Models.RefferalOption;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.CommonUtils;
import com.mesozi.app.buidafrique.Utils.RecyclerItemClickListener;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.adapters.RefferalAdapter;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ekirapa on 10/29/18 .
 */
public class RedeemLoyalty extends AppCompatActivity implements View.OnClickListener {
    private TextView tvAmount, tvTitle;
    private RelativeLayout relativeLayout;
    private ProgressDialog progressDialog;
    RefferalAdapter adapter;
    int number = 0;
    RecyclerView recyclerView;
    List<RefferalOption> refferalOptions = new ArrayList<>();
    RefferalOption refferalOption ;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actiity_redeem_loyalty_points);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        tvAmount = findViewById(R.id.tv_available);
        tvTitle = findViewById(R.id.tv_title);
        relativeLayout = findViewById(R.id.frame);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        tvAmount.setText(String.format(Locale.getDefault(), "Amount Available : 420"));
        final Loyalty loyalty = getIntent().getParcelableExtra("parcel_data");
        if (loyalty != null) {
            tvAmount.setText(String.format(Locale.getDefault(), "Amount Available : %d", loyalty.getAvailable()));
        }
        try {
            getOptions();
        } catch (JSONException e) {
            e.printStackTrace();
            error();
        }
        recyclerView = findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getBaseContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RefferalOption refferalOption = adapter.getOPtion(position);
                String message = "Redeem <b>" + refferalOption.getLoyalty_amount() + " </b> points for  : " + refferalOption.getName() + " ?";
                number = refferalOption.getProduct_id();
                showDialog(message);
            }
        }));

        findViewById(R.id.tv_positive).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    try {
                        redeemLoyalty(number);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error();
                    }

                } else {
                    closeDialog();
                }
            }
        });

        findViewById(R.id.tv_negative).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeDialog();
            }
        });
    }

    private boolean check() {
        Loyalty loyalty = SQLite.select().from(Loyalty.class).querySingle();
        if (Objects.requireNonNull(loyalty).getAvailable() < number) {
            Toast.makeText(getBaseContext(), "Available amount can not be less than amount to redeem", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void showDialog(String title) {
        relativeLayout.setVisibility(View.VISIBLE);
        tvTitle.setText(CommonUtils.fromHtml(title));
    }

    private void closeDialog() {
        relativeLayout.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.card1:
//                showDialog(getString(R.string.loyalty_1));
//                break;
//            case R.id.card2:
//                number = 2;
//                showDialog(getString(R.string.loyalty_2));
//                break;
//            case R.id.card3:
//                number = 3;
//                showDialog(getString(R.string.loyalty_3));
//                break;
//            case R.id.card4:
//                number = 4;
//                showDialog(getString(R.string.loyalty_4));
//                break;
//            case R.id.card5:
//                number = 5;
//                showDialog(getString(R.string.loyalty_5));
//                break;
//            case R.id.card6:
//                number = 6;
//                showDialog(getString(R.string.loyalty_6));
//                break;
//        }
    }

    private void redeem(int number) {
        if (number == 1) {
            try {
                redeemLoyalty(200);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }

        } else if (number == 2) {
            try {
                redeemLoyalty(250);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }
        } else if (number == 3) {
            try {
                redeemLoyalty(300);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }

        } else if (number == 4) {
            try {
                redeemLoyalty(400);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }

        } else if (number == 5) {
            try {
                redeemLoyalty(500);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }

        } else if (number == 6) {
            try {
                redeemLoyalty(600);
            } catch (JSONException e) {
                e.printStackTrace();
                error();
            }
        }
    }


    private void redeemLoyalty(int optionId) throws JSONException {
        progressDialog.setTitle("Redeeming Loyalty Points");
        progressDialog.show();
        JSONObject jsonObject = RequestBuilder.redeemLoyalty();
        Account account = SQLite.select().from(Account.class).querySingle();
        if (account != null) {
            String url = UrlsConfig.getRedeemLoyaltyUrl(optionId, Integer.parseInt(account.getUid()));
            Log.d("url", url);
            Log.d("json", jsonObject.toString());
            final SessionManager sessionManager = new SessionManager(getBaseContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Log.d("response", response.toString());
                    try {
                        if (response.has("error") || response.getJSONObject("result").has("error")) {
                            error( response.getJSONObject("result").getString("error"));
                        } else if (response.has("result")) {
                            finishRedemption();
                        } else {
                            error();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
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
        } else {
            error();
        }
    }

    private void getOptions() throws JSONException {
        progressDialog.setTitle("Fetching redeeming Options");

        progressDialog.show();
        final JSONObject jsonObject = RequestBuilder.redeemLoyalty();
        Account account = SQLite.select().from(Account.class).querySingle();

        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_REDEEM_OPTIONS, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("error")) {
                    error();
                } else if (response.has("result")) {
                    try {
                        JSONArray jsonArray = response.getJSONArray("result");
                        Gson gson = new Gson();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                            RefferalOption option = gson.fromJson(jsonObject1.toString(), RefferalOption.class);
                            option.save();
                        }
                        adapter = new RefferalAdapter(getBaseContext());
                        recyclerView.setAdapter(adapter);
                        if (progressDialog != null) progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } else {
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

    private void finishRedemption() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Commission Redeemed successfully!", Toast.LENGTH_LONG).show();
        finish();
    }
    private void finishRedemption(String error) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
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

}
