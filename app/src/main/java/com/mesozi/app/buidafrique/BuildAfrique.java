package com.mesozi.app.buidafrique;

import android.app.Application;

import com.mesozi.app.buidafrique.Utils.AppDatabase;
import com.mesozi.app.buidafrique.Utils.PersistentCookieStore;
import com.raizlabs.android.dbflow.config.DatabaseConfig;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowManager;

import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.CookiePolicy;

/**
 * Created by ekirapa on 7/24/18 .
 */
public class BuildAfrique extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //CookieHandler.setDefault(new CookieManager());
        CookieManager cookieManager = new CookieManager(new PersistentCookieStore(this), CookiePolicy.ACCEPT_ORIGINAL_SERVER);
        CookieHandler.setDefault(cookieManager);
        FlowManager.init(FlowConfig.builder(this)
                .addDatabaseConfig(DatabaseConfig.builder(AppDatabase.class)
                        .databaseName("AppDatabase")
                        .build())
                .build());
        FlowManager.init(new FlowConfig.Builder(this).build());
    }
}
