package com.example.musicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.example.musicapp.LoginActivity;
import com.example.musicapp.R;
import com.example.musicapp.model.User;
import com.example.musicapp.request.ChangePasswordRequest;
import com.example.musicapp.service.UserService;
import com.example.musicapp.utils.APIClient;
import com.example.musicapp.utils.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserFragment extends Fragment {

    private TextView userName;
    private Button changePasswordButton, logoutButton;
    private SharedPrefManager sharedPrefManager;
    private UserService userService;
    private AlertDialog dialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPrefManager = SharedPrefManager.getInstance(getActivity());
        userService = APIClient.getUserService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userName = view.findViewById(R.id.user_name);
        changePasswordButton = view.findViewById(R.id.change_password_button);
        logoutButton = view.findViewById(R.id.logout_button);


        if (sharedPrefManager.isLoggedIn()) {
            User user = sharedPrefManager.getUser();
            userName.setText(user.getUsername());
            changePasswordButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showChangePasswordDialog();
                }
            });

            // Đặt sự kiện click cho nút "Đăng xuất"
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sharedPrefManager.logout();
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }
            });
        } else {
            Intent intent = new Intent(getActivity(), LoginActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

        return view;
    }

    private void showChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_change_password, null);

        final EditText currentPasswordEditText = dialogView.findViewById(R.id.current_password);
        final EditText newPasswordEditText = dialogView.findViewById(R.id.new_password);
        final EditText confirmNewPasswordEditText = dialogView.findViewById(R.id.confirm_new_password);
        Button changePasswordButton = dialogView.findViewById(R.id.change_password_button);

        builder.setView(dialogView);

        // Xử lý sự kiện click cho nút đổi mật khẩu
        changePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = currentPasswordEditText.getText().toString();
                String newPassword = newPasswordEditText.getText().toString();
                String confirmNewPassword = confirmNewPasswordEditText.getText().toString();

                if (newPassword.equals(confirmNewPassword)) {
                    changePassword(sharedPrefManager.getUser().getId(), currentPassword, newPassword);
                } else {
                    Toast.makeText(getActivity(), "Mật khẩu mới không khớp", Toast.LENGTH_SHORT).show();
                }
            }
        });

        dialog = builder.create();
        dialog.show();
    }

    private void changePassword(String userId, String currentPassword, String newPassword) {
        ChangePasswordRequest request = new ChangePasswordRequest();
        request.currentPassword = currentPassword;
        request.newPassword = newPassword;

        Call<Void> call = userService.changePassword(userId, request);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getActivity(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                } else {
                    Toast.makeText(getActivity(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(getActivity(), "Lỗi mạng", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
