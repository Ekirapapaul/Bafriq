package com.mesozi.app.buidafrique.Utils;

import android.support.annotation.Nullable;

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
}
