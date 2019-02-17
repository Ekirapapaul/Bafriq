package com.buildafrique.pn.app.Fragments;

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
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.FragmentModel;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;
import com.buildafrique.pn.app.activity.AccountRecovery;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

/**
 * Created by ekirapa on 11/13/18 .
 */
public class ChangePasswordFrag extends Fragment {
    EditText password, confirm, code;
    View v;
    ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frag_change_password, container, false);
        Objects.requireNonNull(((AccountRecovery) Objects.requireNonNull(getActivity())).getSupportActionBar()).setTitle("Change Password");
        setViews();
        return v;
    }

    private void setViews() {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Changing Password");
        progressDialog.setMessage("Please Wait");
        progressDialog.setCancelable(false);

        v.findViewById(R.id.btn_next).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (check()) {
                    ((AccountRecovery) Objects.requireNonNull(getActivity())).setPassword(password.getText().toString());
                    try {
                        String email = ((AccountRecovery) Objects.requireNonNull(getActivity())).getEmail();
                        String phone = ((AccountRecovery) Objects.requireNonNull(getActivity())).getPhoneNumber();
                        JSONObject jsonObject = RequestBuilder.changePassword(code.getText().toString(), password.getText().toString(), email,phone);
                        changePassword(jsonObject);
                    } catch (JSONException e) {
                        e.printStackTrace();
                        error();
                    }
                }
            }
        });

        password = v.findViewById(R.id.et_password);
        code = v.findViewById(R.id.et_code);
        confirm = v.findViewById(R.id.et_confirm_password);
    }

    private boolean check() {
        if (code.getText().toString().isEmpty()) {
            code.requestFocus();
            code.setError(getString(R.string.error_required));
            return false;
        } else if (password.getText().toString().isEmpty()) {
            password.requestFocus();
            password.setError(getString(R.string.error_required));
            return false;
        } else if (confirm.getText().toString().isEmpty()) {
            confirm.requestFocus();
            confirm.setError(getString(R.string.error_required));
            return false;
        } else if (!password.getText().toString().equals(confirm.getText().toString())) {
            confirm.requestFocus();
            confirm.setError("Passwords do not match");
            return false;
        }
        return true;
    }
    private void changePassword(JSONObject jsonObject) {
        Log.d("json", jsonObject.toString());
        progressDialog.show();
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, UrlsConfig.URL_CHANGE_PASSWORD, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Log.d("response", response.toString());
                try {
                    if (response.has("error") || response.getJSONObject("result").has("error")) {
                        error(response.getJSONObject("result").getString("error"));
                    } else {
                        finishSend();
                        FragmentModel.getInstance().addNewFrag(2);
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
        Toast.makeText(getContext(), "Password changed successfully!", Toast.LENGTH_LONG).show();
    }

    private void error(String message) {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
    }

    private void error() {
        if (progressDialog != null) progressDialog.dismiss();
        Toast.makeText(getContext(), "Can not find a connection right now", Toast.LENGTH_LONG).show();
    }
}
