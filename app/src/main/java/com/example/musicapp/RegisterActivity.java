package com.example.musicapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.musicapp.model.User;
import com.example.musicapp.service.RegisterService;
import com.example.musicapp.utils.APIClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText re_etUsername, re_etPassword, rre_etPassword, re_etEmail, re_etName;
    private Button btnRegister;
    private TextView tvLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resgister_main);

        re_etUsername = findViewById(R.id.re_etUsername);
        re_etPassword = findViewById(R.id.re_etPassword);
        rre_etPassword = findViewById(R.id.rre_etPassword);
        re_etEmail = findViewById(R.id.re_etEmail);
        re_etName = findViewById(R.id.re_etName);
        btnRegister = findViewById(R.id.btnRegister);
        tvLogin = findViewById(R.id.tvLogin);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = re_etUsername.getText().toString();
                String password = re_etPassword.getText().toString();
                String rePassword = rre_etPassword.getText().toString();
                String email = re_etEmail.getText().toString();
                String name = re_etName.getText().toString();

                if (username.isEmpty() || password.isEmpty() || rePassword.isEmpty() || email.isEmpty() || name.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(rePassword)) {
                    Toast.makeText(RegisterActivity.this, "Mật khẩu không khớp", Toast.LENGTH_SHORT).show();
                    return;
                }

                User registerRequest = new User();
                registerRequest.setUsername(username);
                registerRequest.setPassword(password);
                registerRequest.setEmail(email);
                registerRequest.setName(name);
                registerRequest.setSubscriptionType("free");

                RegisterService registerService = APIClient.getRegisterService();
                Call<User> call = registerService.register(registerRequest);
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            User registeredUser = response.body();
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thất bại!", Toast.LENGTH_SHORT).show();
                            Log.e("Error", response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Lỗi kết nối!", Toast.LENGTH_SHORT).show();
                        Log.e("Error", t.getMessage());
                    }
                });
            }
        });


        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}