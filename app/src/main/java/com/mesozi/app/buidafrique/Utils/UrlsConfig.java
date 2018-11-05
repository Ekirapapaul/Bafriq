package com.mesozi.app.buidafrique.Utils;

import java.util.Locale;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class UrlsConfig {
    private static final String BASE_IP = "http://portal.buildafrique.com:8089/";
    private static final String BASE_WEB_IP = "http://portal.buildafrique.com:8089/web/";
    private static final String AFFILIATE_IP = "http://portal.buildafrique.com:8089/affiliates/";

    public static final String URL_LOGIN = BASE_WEB_IP + "session/authenticate";
    public static final String URL_CREATE_MESSAGE = AFFILIATE_IP + "messaging/new_message";
    public static final String URL_CREATE_AFFILIATE = AFFILIATE_IP + "affiliates/registration";
    public static final String URL_DATASET = BASE_WEB_IP + "dataset/call_kw";
    public static final String URL_PROMOTION_MESSAGES = BASE_WEB_IP +  "get_promo_messages/";
    public static final String URL_GET_SHARE_MESSAGE = BASE_WEB_IP + "get_share_message";

    public static String getDashboardUrl(String uid){
        return String.format(Locale.getDefault(), "%sdashboard/%s", AFFILIATE_IP, uid);
    }

    public static String getRedeemCommissionUrl(int id){
        return String.format(Locale.getDefault(),"%sredeem/commission/%d", AFFILIATE_IP, id);
    }

    public static String getRedeemBonusUrl(int id){
        return String.format(Locale.getDefault(),"%sredeem/bonus/%d", AFFILIATE_IP, id);
    }

    public static String getRedeemLoyaltyUrl(int id, int amount){
        return String.format(Locale.getDefault(),"%sredeem/loyalty/%d/%d", BASE_WEB_IP, id, amount);
    }

    public static String postReadMessage(int message_id){
        return String.format(Locale.getDefault(), "%smessaging/read/%s",BASE_IP,message_id);
    }
}
