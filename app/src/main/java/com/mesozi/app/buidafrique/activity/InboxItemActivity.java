package com.mesozi.app.buidafrique.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.mesozi.app.buidafrique.Models.EmailMessage;
import com.mesozi.app.buidafrique.R;
import com.mesozi.app.buidafrique.Utils.CommonUtils;

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
            toUser.setText(String.format("To %s", message.getReply_to()));
            subject.setText(message.getSubject().equals("false") ? "No Subject" : message.getSubject());
            body.setText(CommonUtils.fromHtml(message.getBody()));
            if(message.getEmail_from().length() > 0){
                char letter = message.getEmail_from().toUpperCase().charAt(0);
                initials.setText(String.valueOf(letter));
            }

        }
    }
}
