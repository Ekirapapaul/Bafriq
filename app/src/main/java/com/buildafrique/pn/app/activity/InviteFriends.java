package com.buildafrique.pn.app.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.buildafrique.pn.app.Models.Account;
import com.buildafrique.pn.app.Models.RefferalMessage;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.CommonUtils;
import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.util.Objects;

/**
 * Created by ekirapa on 10/21/18 .
 */
public class InviteFriends extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final TextView textView = findViewById(R.id.tv_mesage);
        TextView code = findViewById(R.id.tv_code);

        final Account account = SQLite.select().from(Account.class).querySingle();
        if (account != null) {
            code.setText(account.getReferral_code());
        }
        final RefferalMessage refferalMessage = SQLite.select().from(RefferalMessage.class).querySingle();
        if (refferalMessage != null) {
            textView.setText(CommonUtils.fromHtml(refferalMessage.getMessage().replace("{promo_code}", Objects.requireNonNull(account).getReferral_code())));
        }

        findViewById(R.id.btn_invite).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "“Buildafrique™ Partner Network”, Kenya\n" +
                        "Mobile Referral and Loyalty Program for real estate &amp; development\n" +
                        "solutions.\n use my code FGG543 to sign up today";
                if (refferalMessage != null) {
                    message = refferalMessage.getMessage().replace("{promo_code}", Objects.requireNonNull(account).getReferral_code());
                    textView.setText(CommonUtils.fromHtml(message));

                }
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, CommonUtils.fromHtml(message));
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.invite_via)));
            }
        });
    }
}
