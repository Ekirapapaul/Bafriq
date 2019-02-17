package com.buildafrique.pn.app.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Scroller;
import android.widget.TextView;

import com.buildafrique.pn.app.Models.PromoMessage;
import com.buildafrique.pn.app.Models.ShareMessage;
import com.buildafrique.pn.app.R;
import com.buildafrique.pn.app.Utils.CommonUtils;
import com.buildafrique.pn.app.Utils.SessionManager;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private int[] backgrounds;
    private Timer timer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (new SessionManager(getBaseContext()).isLoggedIn() && !new SessionManager(getBaseContext()).getCookie().isEmpty()) {
            Intent intent = new Intent(getBaseContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_welcome);

        ImageView imageView = findViewById(R.id.img_logo);
        Picasso.get().load(R.mipmap.logo).into(imageView);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        findViewById(R.id.tv_privacy).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://partner-network.buildafrique.com/privacy-policy/"));
                startActivity(intent);
            }
        });
        findViewById(R.id.tv_terms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://partner-network.buildafrique.com/terms-and-conditions/"));
                startActivity(intent);
            }
        });

        layouts = new int[]{
                R.layout.fragment_welcome,
                R.layout.fragment_welcome_2,
                R.layout.fragment_welcome_3,
                R.layout.fragment_welcome_4};

        backgrounds = new int[]{
                R.mipmap.image9,
                R.mipmap.img2,
                R.mipmap.img3,
                R.mipmap.img4
        };
        addBottomDots(0);

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        registerViews();
        setupAutoPager();
    }

    private void registerViews() {
        Button login = findViewById(R.id.btn_login);
        Button register = findViewById(R.id.btn_register);
        Button invite = findViewById(R.id.btn_invite);

        login.setOnClickListener(this);
        register.setOnClickListener(this);
        invite.setOnClickListener(this);
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int colorActive = ContextCompat.getColor(getBaseContext(), R.color.white);
        int colorInActive = ContextCompat.getColor(getBaseContext(), R.color.dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorActive);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }


    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_login:
                startActivity(new Intent(getBaseContext(), Login.class));
                break;
            case R.id.btn_register:
                startActivity(new Intent(getBaseContext(), SignUp.class));
                break;
            case R.id.btn_invite:
                ShareMessage shareMessage = SQLite.select().from(ShareMessage.class).querySingle();
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                String mesage = ((shareMessage != null) ? shareMessage.getMessage() : getString(R.string.invite_message));
                mesage = String.valueOf(CommonUtils.fromHtml(mesage));
                sendIntent.putExtra(Intent.EXTRA_TEXT, mesage);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.share_via)));
                break;
        }
    }

    /**
     * View pager adapter
     */
    public class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;
        List<PromoMessage> messages = SQLite.select().from(PromoMessage.class).queryList();

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater != null ? layoutInflater.inflate(layouts[position], container, false) : null;
            TextView textView = Objects.requireNonNull(view).findViewById(R.id.tv_title);
            ImageView imageView = view.findViewById(R.id.img_background);
            Picasso.get()
                    .load(backgrounds[position])
                    .into(imageView);

            if (position < messages.size()) {
                Log.d("message", messages.get(position).getMessage());
                textView.setText(CommonUtils.fromHtml(messages.get(position).getMessage()));
            }
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    private int currentPage = 0;

    private void setupAutoPager() {
        final Handler handler = new Handler();

        final Runnable update = new Runnable() {
            public void run() {

                viewPager.setCurrentItem(currentPage, true);
                if (currentPage == myViewPagerAdapter.getCount()) {
                    currentPage = 0;
                } else {
                    ++currentPage;
                }
            }
        };


        timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                handler.post(update);
            }
        }, 1000, 5000);
    }

    private void getMessages() {

    }
}
