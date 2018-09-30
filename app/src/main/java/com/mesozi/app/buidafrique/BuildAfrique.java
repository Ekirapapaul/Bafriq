package com.mesozi.app.buidafrique;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.mesozi.app.buidafrique.Utils.PersistentCookieStore;
import com.mesozi.app.buidafrique.Utils.SessionManager;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.util.Map;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class BuildAfrique extends Application {

    private static final String SET_COOKIE_KEY = "Set-Cookie";
    private static final String COOKIE_KEY = "Cookie";
    private static final String SESSION_COOKIE = "sessionid";

    @Override
    public void onCreate() {
        CookieHandler.setDefault(new CookieManager());
        super.onCreate();
        FlowManager.init(FlowConfig.builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(AppDatabase.class)
                        .databaseName("buildAfrique")
                        .build())
                .build());
        FlowManager.init(new FlowConfig.Builder(this).build());
    }

    /**
     * Checks the response headers for session cookie and saves it
     * if it finds it.
     * @param headers Response Headers.
     */
    public static void checkSessionCookie(Map<String, String> headers, Context context) {
        SessionManager sessionManager = new SessionManager(context);
            String cookie = headers.get(SET_COOKIE_KEY);
            if (cookie.length() > 0) {
                String[] splitCookie = cookie.split(";");
                String[] splitSessionId = splitCookie[0].split("=");
                cookie = splitSessionId[1];
                Log.d("Setting cookie", cookie);
                sessionManager.setSetCookie(cookie);
            }

    }

    /**
     * Adds session cookie to headers if exists.
     * @param headers
     */
    public static Map<String, String>  addSessionCookie(Map<String, String> headers, Context context) {
        SessionManager sessionManager = new SessionManager(context);
        String sessionId = sessionManager.getSetCookie();
        if (sessionId.length() > 0) {
            StringBuilder builder = new StringBuilder();
            builder.append(SESSION_COOKIE);
            builder.append("=");
            builder.append(sessionId);
            if (headers.containsKey(COOKIE_KEY)) {
                builder.append("; ");
                builder.append(headers.get(COOKIE_KEY));
            }
            headers.put(COOKIE_KEY, builder.toString());
        }
        Log.d("setting", headers.toString());
        return headers;
    }
}
