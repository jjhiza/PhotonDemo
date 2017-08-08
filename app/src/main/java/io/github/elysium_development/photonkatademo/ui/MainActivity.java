package io.github.elysium_development.photonkatademo.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import io.github.elysium_development.photonkatademo.R;
import io.github.elysium_development.photonkatademo.adapters.ViewPagerAdapter;

/**
 * Launcher activity demostrating sample dataset fragment and custom data set fragment
 */
public class MainActivity extends AppCompatActivity {

    //Reference to Adapter for Viewpager
    private ViewPagerAdapter pagerAdapter;

    //Reference for view pager
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPager = (ViewPager) findViewById(R.id.container);
        viewPager.setAdapter(pagerAdapter);

        //Setting tabs for view pager
        TabLayout tabs = (TabLayout) findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
    }
}

