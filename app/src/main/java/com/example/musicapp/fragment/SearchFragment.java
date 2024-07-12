package com.example.musicapp.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.musicapp.R;
import com.example.musicapp.adapter.SearchAdapter;
import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchFragment extends Fragment {

    private EditText searchText;
    private RecyclerView resultsRecyclerView;
    private SearchAdapter searchAdapter;
    private MusicService musicService;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        searchText = view.findViewById(R.id.searchText);
        resultsRecyclerView = view.findViewById(R.id.resultsPager);
        resultsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Khởi tạo SearchAdapter với listener để xử lý sự kiện click vào item
        searchAdapter = new SearchAdapter(new ArrayList<>(), new SearchAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Song song) {
                showPlaybackFragment(song);
            }
        });
        resultsRecyclerView.setAdapter(searchAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        musicService = retrofit.create(MusicService.class);

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    searchSongs(s.toString());
                } else {
                    searchAdapter.updateSongs(new ArrayList<>());
                }
            }
        });

        return view;
    }

    private void searchSongs(String query) {
        musicService.searchSongs(query).enqueue(new Callback<List<Song>>() {
            @Override
            public void onResponse(Call<List<Song>> call, Response<List<Song>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    searchAdapter.updateSongs(response.body());
                } else {
                    Toast.makeText(getContext(), "No results found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Song>> call, Throwable t) {
                Toast.makeText(getContext(), "Search failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showPlaybackFragment(Song song) {
        PlaybackFragment playbackFragment = new PlaybackFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("song", song);
        playbackFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, playbackFragment)
                .addToBackStack(null)
                .commit();
    }
}
