package com.buildafrique.pn.app.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.buildafrique.pn.app.Models.EmailMessage;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.CommonUtils;
import com.buildafrique.pn.app.Utils.RequestBuilder;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.buildafrique.pn.app.Utils.UrlsConfig;
import com.buildafrique.pn.app.Utils.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ekirapa on 10/5/18 .
 */
public class InboxItemActivity extends AppCompatActivity {
    TextView name, from, toUser, subject, body, initials;
    EmailMessage message = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox_item);
        message = getIntent().getParcelableExtra("parcel_data");

        setViews();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void setViews() {
        name = findViewById(R.id.tv_name);
        from = findViewById(R.id.tv_from);
        toUser = findViewById(R.id.tv_to);
        subject = findViewById(R.id.tv_subject);
        body = findViewById(R.id.tv_body);
        initials = findViewById(R.id.tv_initials);

        if (message != null) {

            name.setText(message.getDisplay_name());
            from.setText(String.format("From %s", message.getEmail_from()));
            toUser.setText(String.format("To %s", message.getEmail_from()));
            subject.setText(CommonUtils.fromHtml(message.getSubject().equals("false") ? "No Subject" : message.getSubject()));
            body.setText(CommonUtils.fromHtml(message.getBody()));
            if (message.getEmail_from().length() > 0) {
                char letter = message.getEmail_from().toUpperCase().charAt(0);
                initials.setText(String.valueOf(letter));
            }

            if (message.isTo_read()) {
                try {
                    sendRead(RequestBuilder.readMessage());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }
    }

    private void sendRead(JSONObject jsonObject) {
        String url = UrlsConfig.postReadMessage(message.getId());
        final SessionManager sessionManager = new SessionManager(getBaseContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response.has("result")) {
                    try {
                        message.setTo_read(false);
                        message.save();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

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

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                Log.d("response", response.toString());
                return super.parseNetworkResponse(response);
            }
        };
        VolleySingleton.getInstance(getBaseContext()).addToRequestQueue(jsonObjectRequest);
    }
}
