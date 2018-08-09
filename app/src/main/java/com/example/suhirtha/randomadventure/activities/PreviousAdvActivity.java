package com.example.suhirtha.randomadventure.activities;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.ViewPageAdapter;
import com.example.suhirtha.randomadventure.fragments.MapFragment;

public class PreviousAdvActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTitle("Instagram");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_adv);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.raaBottomNavigation);
        viewPager = (ViewPager) findViewById(R.id.raaViewPager);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new RestaurantListFragment(), "Create Post");
        adapter.addFragment(new MapFragment(), "Map");

        viewPager.setAdapter(adapter);
        //tabLayout.setupWithViewPager(viewPager);
        Menu menu = bottomNavigationView.getMenu();
        menu.findItem(R.id.nav_restaurants).setIcon(R.drawable.filled_star);
        menu.findItem(R.id.nav_map).setIcon(R.drawable.worldwide);

        bottomNavigationView.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        Menu menu = bottomNavigationView.getMenu();
                        MenuItem post = menu.findItem(R.id.nav_restaurants);
                        MenuItem profile = menu.findItem(R.id.nav_map);
                        switch (item.getItemId()) {
                            case R.id.nav_restaurants:
                                viewPager.setCurrentItem(0);
                                menu.findItem(R.id.nav_restaurants).setIcon(R.drawable.filled_star);
                                menu.findItem(R.id.nav_map).setIcon(R.drawable.worldwide);

                                break;
                            case R.id.nav_map:
                                viewPager.setCurrentItem(1);
                                menu.findItem(R.id.nav_restaurants).setIcon(R.drawable.star);
                                menu.findItem(R.id.nav_map).setIcon(R.drawable.filled_earth);

                                break;
                        }
                        return false;
                    }
                });
    }

    public void changeFragment(int index){
        viewPager.setCurrentItem(index);
    }
}
