package com.buildafrique.pn.app.activity.Resources;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.buildafrique.pn.app.R;

/**
 * Created by ekirapa on 10/21/18 .
 */
public class MarketingMaterials extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marketing);
        setViews();
    }

    private void setViews() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        findViewById(R.id.btn_share1).setOnClickListener(this);
        findViewById(R.id.btn_share2).setOnClickListener(this);
        findViewById(R.id.btn_view1).setOnClickListener(this);
        findViewById(R.id.btn_view2).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_share1:
                share("“Do you intend to venture into real estate investment or development  project?\n\n" +
                        "Buildafrique Consulting Group  is your prefered partner for inclusive \"End-to-End\" Real Estate and Development Solutions.\n\n" +
                        "Learn more in the organization website below:\nhttps://buildafrique.com/");
                break;
            case R.id.btn_share2:
                share("“Do you intend to venture into real estate investment or development  project?\n\n" +
                        "Buildafrique Consulting Group  is your prefered partner for inclusive \"End-to-End\" Real Estate and Development Solutions.\n\n" +
                        "Learn more in the organization brochure below:\nhttps://buildafrique.com/wp-content/uploads/2019/01/BCG-Brochure-JANUARY-2019.pdf");
                break;
            case R.id.btn_view1:
                Intent vidIntent = new Intent(Intent.ACTION_VIEW);
                vidIntent.setData(Uri.parse("https://buildafrique.com/"));
                startActivity(vidIntent);
                break;
            case R.id.btn_view2:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://buildafrique.com/wp-content/uploads/2019/01/BCG-Brochure-JANUARY-2019.pdf"));
                startActivity(intent);
                break;
        }
    }

    private void share(String message) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, message);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_via)));
    }
}
