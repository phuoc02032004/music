package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.model.User;
import com.example.musicapp.request.LoginRequest;
import com.example.musicapp.response.LoginResponse;
import com.example.musicapp.service.LoginService;
import com.example.musicapp.utils.APIClient;
import com.example.musicapp.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etUsername, etPassword;
    private Button btnLogin;
    private TextView tvRegister;
    private LoginService loginService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Khởi tạo các thành phần giao diện
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvRegister = findViewById(R.id.tvRegister);

        // Khởi tạo dịch vụ đăng nhập
        loginService = APIClient.getApiService();

        // Xử lý sự kiện bấm nút đăng nhập
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Lấy thông tin username và password từ EditText
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();

                // Tạo đối tượng LoginRequest
                LoginRequest loginRequest = new LoginRequest(username, password);

                // Gọi API đăng nhập
                Call<LoginResponse> call = loginService.login(loginRequest);
                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        // Xử lý phản hồi từ API
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse.message.equals("Login successful")) {
                                User user = loginResponse.user;
                                // Lưu thông tin user vào SharedPreferences
                                SharedPrefManager.getInstance(MainActivity.this).saveUser(user);
                                // Chuyển hướng đến HomeActivity
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                                finish();
                            } else {
                                // Hiển thị thông báo lỗi
                                Toast.makeText(MainActivity.this, loginResponse.message, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            // Xử lý lỗi API
                            Toast.makeText(MainActivity.this, "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Xử lý lỗi kết nối mạng
                        Toast.makeText(MainActivity.this, "Kết nối mạng bị lỗi", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        // Xử lý sự kiện bấm vào TextView "Đăng Ký"
        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Chuyển hướng đến trang đăng ký (RegisterActivity)
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}