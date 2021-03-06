package com.buildafrique.pn.app.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
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
import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.Models.Commission;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

/**
 * Created by ekirapa on 10/29/18 .
 */
public class RedeemCommissions extends AppCompatActivity {
    private final int MPESA = 1;
    private final int BANK = 2;
    private final int PAYPAL = 3;
    int chosen = 1;
    CardView cardPaypal, cardMpesa, cardBank;
    TextView textView;
    private EditText amount, mpesaName, mpesaNum, accName, accNum, bankName, branch, email;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_redeem_commission);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        setViews();

        cardPaypal = findViewById(R.id.card_paypal);
        cardMpesa = findViewById(R.id.card_mpesa);
        cardBank = findViewById(R.id.card_bank);
        textView = findViewById(R.id.tv_available);

        cardBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(BANK);
            }
        });

        cardPaypal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(PAYPAL);
            }
        });

        cardMpesa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                change(MPESA);
            }
        });


        final Commission commission = getIntent().getParcelableExtra("parcel_data");
        if (commission != null) {
            textView.setText(String.format(Locale.getDefault(), "Available Commission : KSH %d", commission.getAvailable()));
        }

        findViewById(R.id.btn_convert).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), ConvertCommission.class);
                intent.putExtra("parcel_data", commission);
                startActivity(intent);
            }
        });

        findViewById(R.id.btn_redeem).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    try {
                        JSONObject jsonObject = null;
                        if (chosen == MPESA) {
                            String payment_method = String.format(Locale.getDefault(), "MPESA %s %s", mpesaName.getText().toString(), mpesaNum.getText().toString());
                            jsonObject = RequestBuilder.redeemCommission(Integer.parseInt(amount.getText().toString()), payment_method);
                        } else if (chosen == BANK) {
                            String payment_method = String.format(Locale.getDefault(), "BANK %s %s %s %s", accName.getText().toString(), accNum.getText().toString(), bankName.getText().toString(), branch.getText().toString());
                            jsonObject = RequestBuilder.redeemCommission(Integer.parseInt(amount.getText().toString()), payment_method);
                        } else if (chosen == PAYPAL) {
                            String payment_method = String.format(Locale.getDefault(), "PAYPAL %s", email.getText().toString());
                            jsonObject = RequestBuilder.redeemCommission(Integer.parseInt(amount.getText().toString()), payment_method);
                        }

                        Account account = SQLite.select().from(Account.class).querySingle();
                        if (account != null) {
                            redeem(jsonObject, UrlsConfig.getRedeemCommissionUrl(Integer.parseInt(account.getUid())));
                        } else {
                            error();
                        }
                    } catch (JSONException e) {
                        error();
                    }

                }
            }
        });

    }

    private void setViews() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Redeeming Commission");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        amount = findViewById(R.id.et_amount);
        accName = findViewById(R.id.et_bank_acc_name);
        accNum = findViewById(R.id.et_bank_acc_number);
        bankName = findViewById(R.id.et_bank_name);
        branch = findViewById(R.id.et_bank_branch);
        email = findViewById(R.id.et_paypal_email);
        mpesaName = findViewById(R.id.et_mpesa_name);
        mpesaNum = findViewById(R.id.et_mpesa_number);
    }

    private void change(int number) {
        if (number == MPESA) {
            chosen = MPESA;
            findViewById(R.id.linear_mpesa).setVisibility(View.VISIBLE);
            findViewById(R.id.linear_bank).setVisibility(View.GONE);
            findViewById(R.id.linear_paypal).setVisibility(View.GONE);
        } else if (number == BANK) {
            chosen = BANK;
            findViewById(R.id.linear_mpesa).setVisibility(View.GONE);
            findViewById(R.id.linear_bank).setVisibility(View.VISIBLE);
            findViewById(R.id.linear_paypal).setVisibility(View.GONE);

        } else if (number == PAYPAL) {
            chosen = PAYPAL;
            findViewById(R.id.linear_mpesa).setVisibility(View.GONE);
            findViewById(R.id.linear_bank).setVisibility(View.GONE);
            findViewById(R.id.linear_paypal).setVisibility(View.VISIBLE);
        }
    }

    private boolean check() {
        if (amount.getText().toString().isEmpty()) {
            amount.requestFocus();
            amount.setError(getString(R.string.error_required));
            return false;
        } else {
            if (chosen == MPESA) {
                if (mpesaName.getText().toString().isEmpty()) {
                    mpesaName.requestFocus();
                    mpesaName.setError(getString(R.string.error_required));
                    return false;
                } else if (mpesaNum.getText().toString().isEmpty()) {
                    mpesaNum.requestFocus();
                    mpesaNum.setError(getString(R.string.error_required));
                    return false;
                }
            } else if (chosen == BANK) {
                if (accName.getText().toString().isEmpty()) {
                    accName.requestFocus();
                    accName.setError(getString(R.string.error_required));
                    return false;
                } else if (accNum.getText().toString().isEmpty()) {
                    accNum.requestFocus();
                    accNum.setError(getString(R.string.error_required));
                    return false;
                } else if (bankName.getText().toString().isEmpty()) {
                    bankName.requestFocus();
                    bankName.setError(getString(R.string.error_required));
                    return false;
                } else if (branch.getText().toString().isEmpty()) {
                    branch.requestFocus();
                    branch.setError(getString(R.string.error_required));
                    return false;
                }
            } else if (chosen == PAYPAL) {
                if (email.getText().toString().isEmpty()) {
                    email.requestFocus();
                    email.setError(getString(R.string.error_required));
                    return false;
                }
            }
        }
        return true;
    }

    private String getPaymentMethod(int number) {
        if (number == MPESA) {
            return "MPESA";
        } else if (number == BANK) {
            return "BANK";
        } else if (number == PAYPAL) {
            return "PAYPAL";
        }

        return null;
    }

    private void redeem(JSONObject jsonObject, String url) {
        progressDialog.show();
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        Log.d("url", url);
        Log.d("Commission json", jsonObject.toString());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                if (response.has("error")) {
                    error();
                } else if (response.has("result")) {
                    successfullDialog(getString(R.string.successfull_redeem_commission), getString(R.string.message_successfull_commission_redemption));
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
        Toast.makeText(this, "Commission Redeemed successfully!", Toast.LENGTH_SHORT).show();
        finish();
    }

    private void successfullDialog(String title, String message) {
        if (progressDialog != null) progressDialog.dismiss();
        AlertDialog alertDialog = new AlertDialog.Builder(Objects.requireNonNull(this)).create();
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

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(this, "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }

}
