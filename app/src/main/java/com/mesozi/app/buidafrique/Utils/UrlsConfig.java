package com.mesozi.app.buidafrique.Utils;

import java.util.Locale;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class UrlsConfig {
    private static final String BASE_IP = "http://portal.buildafrique.com:8089/web/";
    private static final String AFFILIATE_IP = "http://portal.buildafrique.com:8089/affiliates/";

    public static final String URL_LOGIN = BASE_IP + "session/authenticate";
    public static final String URL_CREATE_MESSAGE = AFFILIATE_IP + "messaging/new_message";
    public static final String URL_CREATE_AFFILIATE = AFFILIATE_IP + "affiliates/registration";
    public static final String URL_DATASET = BASE_IP + "dataset/call_kw";

    public static String getDashboardUrl(String uid){
        return String.format(Locale.getDefault(), "%sdashboard/%s", AFFILIATE_IP, uid);
    }
}
