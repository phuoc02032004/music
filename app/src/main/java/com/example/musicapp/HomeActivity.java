package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.musicapp.fragment.HomeFragment;
import com.example.musicapp.fragment.PlaybackFragment;
import com.example.musicapp.fragment.PlaylistFragment;
import com.example.musicapp.fragment.SearchFragment;
import com.example.musicapp.fragment.UserFragment;
import com.example.musicapp.model.Playlist;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {
    public HomeFragment homeFragment;
    public PlaybackFragment playbackFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        homeFragment = new HomeFragment();
        playbackFragment = new PlaybackFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, homeFragment)
                .commit();

        BottomNavigationView navView = findViewById(R.id.navigationBar);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Fragment fragment = null;
                int id = item.getItemId();
                if (id == R.id.menu_home) {
                    fragment = homeFragment;
                } else if (id == R.id.menu_playback) {
                    fragment = playbackFragment;
                } else if (id == R.id.menu_search) {
                    fragment = new SearchFragment();
                } else if (id == R.id.menu_playlist) {
                    fragment = new PlaylistFragment();
                } else if (id == R.id.menu_user) {
                    fragment = new UserFragment();
                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, fragment)
                            .addToBackStack(null)
                            .commit();
                    return true;
                }
                return false;
            }
        });
    }
}