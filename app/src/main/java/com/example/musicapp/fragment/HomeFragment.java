package com.example.musicapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.HomeActivity;
import com.example.musicapp.R;
import com.example.musicapp.adapter.SongAdapter;
import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;
import com.example.musicapp.utils.APIClient;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerViewSongs;
    private SongAdapter songAdapter;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerViewSongs = root.findViewById(R.id.recyclerViewSongs);
        recyclerViewSongs.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));

        MusicService musicService = APIClient.getMusicservice();

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
                        // Cập nhật thông tin bài hát cho PlaybackFragment
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("song", song);
                        ((HomeActivity) getActivity()).playbackFragment.setArguments(bundle);
                        ((HomeActivity) getActivity()).playbackFragment.loadSongDetails(song);

                        // Thay đổi giao diện cho PlayerFragment (để hiển thị PlaybackFragment)
                        FragmentManager fragmentManager = getParentFragmentManager();
                        fragmentManager.beginTransaction()
                                .replace(R.id.fragment_container, ((HomeActivity) getActivity()).playbackFragment) // Thay thế fragmentContainerView
                                .addToBackStack(null) // Thêm vào stack back
                                .commit();
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