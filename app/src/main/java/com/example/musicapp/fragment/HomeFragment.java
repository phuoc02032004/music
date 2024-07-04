package com.example.musicapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.musicapp.R;
import com.example.musicapp.adapter.SongAdapter;
import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;

import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewSongs = root.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        // Khởi tạo Retrofit
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/") // Thay thế bằng URL API của bạn
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MusicService musicService = retrofit.create(MusicService.class);

        // Gọi API để lấy danh sách bài hát
        Call<List<Song>> call = musicService.getAllSongs();
        call.enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(@NonNull Call<List<Song>> call, @NonNull Response<List<Song>> response) {
                if (response.isSuccessful()) {
                    List<Song> songs = response.body();
                    songAdapter = new SongAdapter(songs);

                    // Thiết lập listener cho SongAdapter
                    songAdapter.setOnItemClickListener(song -> {
                    });

                    recyclerViewSongs.setAdapter(songAdapter);
                    songAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu", Toast.LENGTH_SHORT).show();
                    Log.e("HomeFragment", "Lỗi API: " + response.message());
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<Song>> call, @NonNull Throwable t) {
                Toast.makeText(getContext(), "Kết nối API thất bại", Toast.LENGTH_SHORT).show();
                Log.e("HomeFragment", "Lỗi kết nối: " + t.getMessage());
            }
        });
        return root;
    }
}