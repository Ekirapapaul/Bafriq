package com.buildafrique.pn.app.Utils;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;

import com.buildafrique.pn.app.Models.Bonus;
import com.buildafrique.pn.app.Models.Commission;
import com.buildafrique.pn.app.Models.EmailMessage;
import com.buildafrique.pn.app.Models.Lead;
import com.buildafrique.pn.app.Models.Loyalty;
import com.buildafrique.pn.app.activity.WelcomeActivity;
import com.raizlabs.android.dbflow.sql.language.Delete;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by ekirapa on 7/25/18 .
 */
public class CommonUtils {
    @Nullable
    public static Date parseStringToDate(String string) throws ParseException {
        if (string.isEmpty()) {
            return null;
        } else {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            return formatter.parse(string);
        }
    }

    public static Spanned fromHtml(String html) {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            if (html == null) return null;
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        } else {
            if (html == null) return null;
            result = Html.fromHtml(html);
        }
        return result;
    }

    public static String TO_EMAIL = "partner@buildafrique.com";

    public static String sanitizeNumber(String number) {
        String sanitized = number;

        if (number.length() == 13 && number.substring(0, Math.min(number.length(), 4)).equals("+254")) {
            sanitized = number.substring(4);
        } else if (number.length() == 10 && number.charAt(0) == '0') {
            sanitized = number.substring(1);
        }else if(number.length() == 12 && number.substring(0, Math.min(number.length(), 3)).equals("254")){
            sanitized = number.substring(3);
        }

        return sanitized;
    }

    public void logOut(AppCompatActivity context){
        new SessionManager(context).setLoggedIn(false);
        try {
            Delete.table(Lead.class);
//                    Delete.table(SalesOrder.class);
            Delete.table(EmailMessage.class);
            Delete.table(Commission.class);
            Delete.table(Loyalty.class);
            Delete.table(Bonus.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(context, WelcomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
