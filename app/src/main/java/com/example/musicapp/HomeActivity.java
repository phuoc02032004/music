package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.musicapp.fragment.HomeFragment;
import com.example.musicapp.fragment.PlayerFragment;
import com.example.musicapp.fragment.SearchFragment;
import com.example.musicapp.fragment.UserFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navView = findViewById(R.id.navigationBar);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment = null;
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    fragment = new HomeFragment();
                } else if (id == R.id.menu_player) {
                    fragment = new PlayerFragment();
                } else if (id == R.id.menu_search) {
                    fragment = new SearchFragment();
                } else if (id == R.id.menu_user) {
                    fragment = new UserFragment();
                }

                if (fragment != null) {
                    FragmentManager fragmentManager = getSupportFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .commit();
                    return true;
                }
                return false;
            }
        });

        // Gọi method để hiển thị HomeFragment
        showHomeFragmentIfNecessary(savedInstanceState);
    }

    // Method để hiển thị HomeFragment nếu cần
    private void showHomeFragmentIfNecessary(Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            // Chỉ hiển thị HomeFragment khi chưa có fragment nào trong container
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }
    }
}