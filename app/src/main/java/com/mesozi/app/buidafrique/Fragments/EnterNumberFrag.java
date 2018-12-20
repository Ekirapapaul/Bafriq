package com.mesozi.app.buidafrique.Fragments;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.hbb20.CountryCodePicker;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.FragmentModel;
import com.mesozi.app.buidafrique.Utils.RequestBuilder;
import com.mesozi.app.buidafrique.Utils.UrlsConfig;
import com.mesozi.app.buidafrique.Utils.VolleySingleton;
import com.mesozi.app.buidafrique.activity.AccountRecovery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by ekirapa on 11/13/18 .
 */
public class EnterNumberFrag extends Fragment {
    View v;
    EditText etNumber, etEmail;
    ProgressDialog progressDialog;
    CountryCodePicker country_code;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_number_recovery, container, false);
        Objects.requireNonNull(((AccountRecovery) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Account Recovery");
        setViews();
        return v;
    }

    private void setViews() {
        v.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    ((AccountRecovery) Objects.requireNonNull(getActivity())).setEmail(etEmail.getText().toString());
                    ((AccountRecovery) Objects.requireNonNull(getActivity())).setPhoneNumber(etNumber.getText().toString());
                    try {
                        getCode(RequestBuilder.getCode(etEmail.getText().toString(), etNumber.getText().toString()));
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error();
                    }
                }

            }
        });

        etNumber = v.findViewById(R.id.et_number);
        etEmail = v.findViewById(R.id.et_email);
        country_code = v.findViewById(R.id.country_code);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Sending Details");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);
    }

    private boolean check() {
        if (etNumber.getText().toString().isEmpty()) {
            etNumber.requestFocus();
            etNumber.setError(getString(R.string.error_required));
            return false;
        } else if (etEmail.getText().toString().isEmpty()) {
            etEmail.requestFocus();
            etEmail.setError(getString(R.string.error_required));
            return false;
        }
        return true;
    }

    private void getCode(JSONObject jsonObject) {
        Log.d("json", jsonObject.toString());
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_RESET_PASSWORD, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("result") && response.getJSONObject("result").has("error")) {
                        error(response.getJSONObject("result").getString("error"));
                    } else {
                        finishSend();
                        FragmentModel.getInstance().addNewFrag(1);
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
        });
        VolleySingleton.getInstance(getContext()).addToRequestQueue(jsonObjectRequest);
    }

    public void finishSend() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), "Request sent successfully!", Toast.LENGTH_SHORT).show();
    }

    private void error(String message) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), "Can not find a connection right now", Toast.LENGTH_SHORT).show();
    }
}
