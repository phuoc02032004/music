package com.example.musicapp.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.musicapp.R;
import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;

import java.io.IOException;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SongViewHolder> {

    public interface OnItemClickListener {
        void onItemClick(Song song);
    }

    private List<Song> songs;
    private MusicService musicService;
    private static final String TAG = "SearchAdapter";
    private OnItemClickListener listener; // Listener để gọi khi click vào item

    public SearchAdapter(List<Song> songs, OnItemClickListener listener) {
        this.songs = songs;
        this.listener = listener;

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        musicService = retrofit.create(MusicService.class);
    }

    @NonNull
    @Override
    public SongViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_song, parent, false);
        return new SongViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SongViewHolder holder, int position) {
        Song song = songs.get(position);
        holder.songTitle.setText(song.getTitle());
        holder.songArtist.setText(song.getArtist().getName());

        Call<ResponseBody> call = musicService.getImage(song.getId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    if (responseBody != null) {
                        try {
                            // Chuyển đổi dữ liệu từ response thành Bitmap
                            byte[] imageData = responseBody.bytes();
                            Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                            // Hiển thị ảnh bằng Glide
                            Glide.with(holder.itemView.getContext())
                                    .load(bitmap)
                                    .placeholder(R.drawable.placeholder_image)
                                    .error(R.drawable.error_image)
                                    .into(holder.songImage);
                        } catch (IOException e) {
                            Log.e(TAG, "Error decoding image data", e);
                            Toast.makeText(holder.itemView.getContext(), "Lỗi khi giải mã dữ liệu ảnh", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code() + ", " + response.message());
                    Toast.makeText(holder.itemView.getContext(), "Lỗi khi lấy ảnh bài hát", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "Error fetching image", t);
                Toast.makeText(holder.itemView.getContext(), "Lỗi khi gọi API lấy ảnh", Toast.LENGTH_SHORT).show();
            }
        });

        // Xử lý sự kiện khi click vào item
        holder.itemView.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_POSITION) {
                listener.onItemClick(songs.get(pos));
            }
        });
    }

    @Override
    public int getItemCount() {
        return songs.size();
    }

    public static class SongViewHolder extends RecyclerView.ViewHolder {
        ImageView songImage;
        TextView songTitle;
        TextView songArtist;

        public SongViewHolder(@NonNull View itemView) {
            super(itemView);
            songImage = itemView.findViewById(R.id.songImage);
            songTitle = itemView.findViewById(R.id.songTitle);
            songArtist = itemView.findViewById(R.id.songArtist);
        }
    }

    public void updateSongs(List<Song> newSongs) {
        songs.clear();
        songs.addAll(newSongs);
        notifyDataSetChanged();
    }
}
