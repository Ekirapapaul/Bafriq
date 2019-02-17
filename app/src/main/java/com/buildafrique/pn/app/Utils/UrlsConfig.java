package com.buildafrique.pn.app.Utils;

import java.util.Locale;

/**
 * Created by ekirapa on 7/8/18 .
 */
public class UrlsConfig {
    private static final String BASE_IP = "http://portal.buildafrique.com:8069/";
    private static final String BASE_WEB_IP = "http://portal.buildafrique.com:8069/web/";
    private static final String AFFILIATE_IP = "http://portal.buildafrique.com:8069/affiliates/";

    public static final String URL_LOGIN = BASE_WEB_IP + "session/authenticate";
    public static final String URL_CREATE_MESSAGE = AFFILIATE_IP + "messaging/new_message";
    public static final String URL_CREATE_AFFILIATE = AFFILIATE_IP + "registration";
    public static final String URL_DATASET = BASE_WEB_IP + "dataset/call_kw";
    public static final String URL_PROMOTION_MESSAGES = BASE_WEB_IP +  "get_promo_messages/";
    public static final String URL_GET_SHARE_MESSAGE = BASE_WEB_IP + "get_share_message";
    public static final String URL_RESET_PASSWORD = AFFILIATE_IP + "forgot_password";
    public static final String URL_CHANGE_PASSWORD  =AFFILIATE_IP + "forgot_password";
    public static final String URL_REDEEM_OPTIONS = BASE_IP + "loyalty/redeem/options";
    public static final String URL_GET_REFFERAL_MESSAGE = BASE_WEB_IP + "get_referral_message";
    public static final String URL_EDIT_PROFILE = AFFILIATE_IP +  "edit_profile";
    public static final String URL_EDIT_PASSWORD = AFFILIATE_IP +  "edit_password";
    public static final String URL_GET_CONVERSION_RATES = BASE_IP + "loyalty/convert/options";
    public static String getDashboardUrl(String uid){
        return String.format(Locale.getDefault(), "%sdashboard/%s", AFFILIATE_IP, uid);
    }

    public static String getRedeemCommissionUrl(int id){
        return String.format(Locale.getDefault(),"%sredeem/commission/%d", AFFILIATE_IP, id);
    }

    public static String getRedeemBonusUrl(int id){
        return String.format(Locale.getDefault(),"%sredeem/bonus/%d", AFFILIATE_IP, id);
    }

    public static String getRedeemLoyaltyUrl(int optionId, int uid){
        return String.format(Locale.getDefault(),"%sloyalty/redeem/%d/%d", BASE_IP, optionId, uid);
    }

    public static String postReadMessage(String message_id){
        return String.format(Locale.getDefault(), "%smessaging/read/%s",BASE_IP,message_id);
    }
    public static String convertToloyaltyPoints(String option){
        return String.format(Locale.getDefault(),"%sloyalty/convert/%s", BASE_IP, option);
    }
}
