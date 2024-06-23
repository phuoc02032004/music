package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.model.User;
import com.example.musicapp.utils.SharedPrefManager;

public class HomeActivity extends AppCompatActivity {

    private TextView tvWelcome;
    private Button buttonLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        tvWelcome = findViewById(R.id.tvWelcome);
        buttonLogout = findViewById(R.id.buttonLogout);

        // Lấy thông tin người dùng từ SharedPreferences
        User user = SharedPrefManager.getInstance(this).getUser();
        tvWelcome.setText("Chào mừng " + user.getName() + "!");

        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPrefManager.getInstance(HomeActivity.this).logout();
                Intent intent = new Intent(HomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}