package com.buildafrique.pn.app.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by ekirapa on 4/13/18.
 * session manager
 */

public class SessionManager {
    private SharedPreferences pref;

    private SharedPreferences.Editor editor;
    private static final int PRIVATE_MODE = 0;

    private Context mContext;
    private static final String PREF_NAME = "UserSession";
    private static final String KEY_BEARER_TOKEN = "token";
    private static final String KEY_IS_LOGGED_IN = "IsLoggedIn";
    private static final String DATE_TOKEN_REFRESHED = "TokenRefreshed";
    private static final String KEY_REFRESH_TOKEN = "refreshToken";
    private static final String USER_NAME = "userName";
    private static final String COOKIE = "Cookie";
    private static final String SET_COOKIE = "SetCookie";


    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
    }

    public void setKeyBearerToken(String token) {
        pref.edit().putString(KEY_BEARER_TOKEN, token).apply();
    }

    public String getKeyBearerToken() {
        return pref.getString(KEY_BEARER_TOKEN, null);
    }

    public void setKeyRefreshToken(String token) {
        pref.edit().putString(KEY_REFRESH_TOKEN, token).apply();
    }

    public String getKeyRefreshToken() {
        return pref.getString(KEY_REFRESH_TOKEN, null);
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void setLoggedIn(boolean loggedIn) {
        pref.edit().putBoolean(KEY_IS_LOGGED_IN, loggedIn).apply();
    }

    public void setDateTokenRefreshed(String date) {
        pref.edit().putString(DATE_TOKEN_REFRESHED, date).apply();
    }

    public String getDateTokenRefreshed() {
        return pref.getString(DATE_TOKEN_REFRESHED, null);
    }

    public void setUserName(String token) {
        pref.edit().putString(USER_NAME, token).apply();
    }

    public String getUserName() {
        return pref.getString(USER_NAME, null);
    }

    public String getCookie() {
        return pref.getString(COOKIE, null);
    }

    public void setCookie(String token) {
        pref.edit().putString(COOKIE, token).apply();
    }

    public String getSetCookie() {
        return pref.getString(SET_COOKIE, null);
    }

    public void setSetCookie(String token) {
        pref.edit().putString(SET_COOKIE, token).apply();
    }

    public void removeToken() {
        pref.edit().remove(COOKIE).apply();
    }

}
