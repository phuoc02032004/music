package com.example.musicapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.R;
import com.example.musicapp.adapter.PlaylistAdapter;
import com.example.musicapp.model.Playlist;
import com.example.musicapp.model.User;
import com.example.musicapp.service.PlaylistService;
import com.example.musicapp.utils.APIClient;
import com.example.musicapp.utils.SharedPrefManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaylistFragment extends Fragment {

    private RecyclerView playlistRV;
    private PlaylistAdapter playlistAdapter;
    private PlaylistService playlistService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        playlistRV = view.findViewById(R.id.playlistRV);
        ImageButton addPlaylistIcon = view.findViewById(R.id.addPlayListIcon);

        playlistRV.setLayoutManager(new LinearLayoutManager(getContext()));

        playlistService = APIClient.getPlaylistService();

        addPlaylistIcon.setOnClickListener(v -> showAddPlaylistDialog());

        loadPlaylists();
        return view;
    }

    private void loadPlaylists() {
        Call<List<Playlist>> call = playlistService.getAllPlaylists();
        call.enqueue(new Callback<List<Playlist>>() {
            @Override
            public void onResponse(@NonNull Call<List<Playlist>> call, @NonNull Response<List<Playlist>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    playlistAdapter = new PlaylistAdapter(response.body());
                    playlistRV.setAdapter(playlistAdapter);
                } else {
                    // Xử lý lỗi khi API trả về không thành công
                    Toast.makeText(getContext(), "Lỗi tải danh sách playlist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Playlist>> call, @NonNull Throwable t) {
                // Xử lý lỗi khi API không thể kết nối
                Toast.makeText(getContext(), "Không thể kết nối đến API!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showAddPlaylistDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_add_playlist, null);
        TextInputEditText nameInput = dialogView.findViewById(R.id.playlistNameInput);
        TextInputEditText descriptionInput = dialogView.findViewById(R.id.playlistDescriptionInput);

        // Lấy thông tin User đã đăng nhập từ SharedPrefManager
        User currentUser = SharedPrefManager.getInstance(getContext()).getUser();

        if (currentUser == null) {
            // Xử lý khi user chưa đăng nhập (ví dụ: hiển thị thông báo lỗi, chuyển hướng)
            Toast.makeText(getContext(), "Bạn cần đăng nhập để thêm playlist!", Toast.LENGTH_SHORT).show();
            return;
        }

        new MaterialAlertDialogBuilder(getContext())
                .setTitle("Thêm Playlist")
                .setView(dialogView)
                .setPositiveButton("Thêm", (dialog, which) -> {
                    String name = nameInput.getText().toString();
                    String description = descriptionInput.getText().toString();

                    // Tạo Playlist
                    Playlist newPlaylist = new Playlist(null, name, description, new ArrayList<>(), currentUser);

                    createPlaylist(newPlaylist);
                })
                .setNegativeButton("Hủy", (dialog, which) -> dialog.dismiss())
                .show();
    }

    private void createPlaylist(Playlist playlist) {
        Call<Playlist> call = playlistService.createPlaylist(playlist);
        call.enqueue(new Callback<Playlist>() {
            @Override
            public void onResponse(@NonNull Call<Playlist> call, @NonNull Response<Playlist> response) {
                if (response.isSuccessful() && response.body() != null) {
                    playlistAdapter.addPlaylist(response.body());
                    Toast.makeText(getContext(), "Thêm playlist thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    // Xử lý lỗi khi API trả về không thành công
                    Toast.makeText(getContext(), "Lỗi thêm playlist!", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Playlist> call, @NonNull Throwable t) {
                // Xử lý lỗi khi API không thể kết nối
                Toast.makeText(getContext(), "Không thể kết nối đến API!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Đăng xuất khi Fragment bị hủy
        SharedPrefManager.getInstance(getContext()).logout();
    }
}