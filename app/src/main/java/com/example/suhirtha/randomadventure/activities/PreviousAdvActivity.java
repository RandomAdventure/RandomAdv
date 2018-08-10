package com.example.suhirtha.randomadventure.activities;

import android.arch.persistence.room.Room;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.suhirtha.randomadventure.AppDatabase;
import com.example.suhirtha.randomadventure.R;
import com.example.suhirtha.randomadventure.ViewPageAdapter;
import com.example.suhirtha.randomadventure.fragments.MapFragment;
import com.example.suhirtha.randomadventure.fragments.RestaurantListFragment;

public class PreviousAdvActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    private ViewPageAdapter adapter;
    private int REQUEST_CODE_SELECTION = 150;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_previous_adv);
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.raaBottomNavigation);
        viewPager = (ViewPager) findViewById(R.id.raaViewPager);
        adapter = new ViewPageAdapter(getSupportFragmentManager());

        adapter.addFragment(new RestaurantListFragment(), "Create Post");
        adapter.addFragment(new MapFragment(), "Map");

        viewPager.setAdapter(adapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_general, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) { //empty on purpose
        return true;
    }

    public void onClickBack(View view) {
        Intent intent = new Intent(this, StartActivity.class);
        startActivityForResult(intent, REQUEST_CODE_SELECTION); //wrapping
    }

    public void onClickDelete(View view) {
        Log.d("RChosenActivity", "Deleting database completely");
        AppDatabase db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "saved_restaurants")
                .allowMainThreadQueries()
                .build();
        db.restaurantDao().deleteAll();
        Toast.makeText(this, "Database deleted!", Toast.LENGTH_SHORT).show();
    }
}
