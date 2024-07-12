package com.example.musicapp.fragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.musicapp.R;
import com.example.musicapp.model.Artist;
import com.example.musicapp.model.Song;
import com.example.musicapp.service.MusicService;
import com.example.musicapp.utils.APIClient;
import com.squareup.picasso.Picasso;
import com.example.musicapp.repository.SongRepository;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaybackFragment extends Fragment {

    private static final String TAG = "PlaybackFragment";

    private ImageView albumArt;
    private TextView songTitle, songArtist;
    private SeekBar playbackSeekBar;
    private ImageButton previousButton, nextButton;
    private ViewSwitcher playPauseButton;
    private Button sectionBackButton;

    private MediaPlayer mediaPlayer;
    private boolean isPlaying = false;
    private Song currentSong;

    private SongRepository songRepository;

    // Khởi tạo biến để lưu trữ vị trí phát lại
    private int currentPosition = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_playback, container, false);

        albumArt = root.findViewById(R.id.albumArt);
        songTitle = root.findViewById(R.id.songTitle);
        songArtist = root.findViewById(R.id.songArtist);
        playbackSeekBar = root.findViewById(R.id.playbackSeekBar);
        previousButton = root.findViewById(R.id.previousButton);
        nextButton = root.findViewById(R.id.nextButton);
        playPauseButton = root.findViewById(R.id.playPauseButton);
        sectionBackButton = root.findViewById(R.id.sectionBackButton);

        songRepository = new SongRepository();

        if (getArguments() != null) {
            currentSong = (Song) getArguments().getSerializable("song");
            loadSongDetails(currentSong);
        }

        playPauseButton.setOnClickListener(v -> {
            if (isPlaying) {
                pauseAudio();
            } else {
                resumeAudio(); // Sửa lại thành resumeAudio()
            }
        });

        sectionBackButton.setOnClickListener(v -> getParentFragmentManager().popBackStack());

        previousButton.setOnClickListener(v -> {
            loadPreviousSong();
        });

        nextButton.setOnClickListener(v -> {
            loadNextSong();
        });

        return root;
    }

    public void loadSongDetails(Song song) {git
        Artist artist = song.getArtist();
        if (artist != null) {
            songTitle.setText(song.getTitle());
            songArtist.setText(artist.getName());
        } else {
            songTitle.setText(song.getTitle());
            songArtist.setText("Unknown Artist");
        }

        songRepository.getSongImage(song.getId(), new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    ResponseBody responseBody = response.body();
                    try {
                        byte[] imageData = responseBody.bytes();
                        Bitmap bitmap = BitmapFactory.decodeByteArray(imageData, 0, imageData.length);
                        albumArt.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e(TAG, "Error getting image data", e);
                        Toast.makeText(getContext(), "Lỗi khi lấy ảnh bài hát", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code() + ", " + response.message());
                    Toast.makeText(getContext(), "Lỗi khi lấy ảnh bài hát", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Log.e(TAG, "API call failed", t);
                Toast.makeText(getContext(), "Kết nối API thất bại", Toast.LENGTH_SHORT).show();
            }
        });

        // Lưu vị trí phát lại hiện tại và tải lại bài hát
        currentPosition = 0; // Đặt lại vị trí phát lại về 0 khi tải bài hát mới
        playAudio();
    }

    private void playAudio() {
        if (currentSong != null) {
            MusicService musicService = APIClient.getMusicservice();
            Call<ResponseBody> call = musicService.getAudioUrl(currentSong.getId());
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                    if (response.isSuccessful()) {
                        ResponseBody responseBody = response.body();
                        try {
                            byte[] audioData = responseBody.bytes();
                            initializeMediaPlayer(audioData);
                        } catch (IOException e) {
                            Log.e(TAG, "Error getting audio data", e);
                            Toast.makeText(getContext(), "Lỗi khi lấy dữ liệu audio", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.e(TAG, "Response unsuccessful: " + response.code() + ", " + response.message());
                        Toast.makeText(getContext(), "Lỗi khi lấy URL audio", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                    Log.e(TAG, "API call failed", t);
                    Toast.makeText(getContext(), "Kết nối API thất bại", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void initializeMediaPlayer(byte[] audioData) {
        // Khởi tạo lại MediaPlayer mỗi khi tải bài hát mới
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnCompletionListener(mp -> {
            isPlaying = false;
            playPauseButton.setDisplayedChild(0); // Show play button
        });

        try {
            // Tạo Uri từ dữ liệu mảng byte
            File tempFile = File.createTempFile("audio", ".mp3", getContext().getCacheDir());
            FileOutputStream outputStream = new FileOutputStream(tempFile);
            outputStream.write(audioData);
            outputStream.close();

            Uri audioUri = Uri.fromFile(tempFile);

            // Sử dụng setDataSource(Context, Uri)
            mediaPlayer.setDataSource(getContext(), audioUri);
            mediaPlayer.prepare();

            // Khởi động phát lại từ đầu
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setDisplayedChild(1); // Show pause button
            playbackSeekBar.setMax(mediaPlayer.getDuration());
            updateSeekBar();
        } catch (IOException e) {
            Log.e(TAG, "Error initializing media player", e);
            Toast.makeText(getContext(), "Lỗi khi phát audio", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null && isPlaying) {
            playbackSeekBar.setProgress(mediaPlayer.getCurrentPosition());
            playbackSeekBar.postDelayed(this::updateSeekBar, 1000);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (currentSong != null) {
            loadSongDetails(currentSong);
        }
    }

    private void pauseAudio() {
        if (mediaPlayer != null && isPlaying) {
            currentPosition = mediaPlayer.getCurrentPosition(); // Lưu trữ vị trí hiện tại
            mediaPlayer.pause();
            isPlaying = false;
            playPauseButton.setDisplayedChild(0); // Show play button
        }
    }

    private void resumeAudio() { // Thêm hàm resumeAudio
        if (mediaPlayer != null && !isPlaying) {
            mediaPlayer.seekTo(currentPosition); // Đặt lại vị trí phát lại
            mediaPlayer.start();
            isPlaying = true;
            playPauseButton.setDisplayedChild(1); // Show pause button
            updateSeekBar();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    // Load bài hát trước
    private void loadPreviousSong() {
        MusicService musicService = APIClient.getMusicservice();
        Call<Song> call = musicService.getPreviousSong(currentSong.getId());
        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(@NonNull Call<Song> call, @NonNull Response<Song> response) {
                if (response.isSuccessful()) {
                    Song previousSong = response.body();
                    if (previousSong != null) {
                        currentSong = previousSong;
                        loadSongDetails(currentSong);
                    } else {
                        // Xử lý trường hợp không có bài hát trước
                        Toast.makeText(getContext(), "Không có bài hát trước", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code() + ", " + response.message());
                    Toast.makeText(getContext(), "Lỗi khi lấy bài hát trước", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Song> call, @NonNull Throwable t) {
                Log.e(TAG, "API call failed", t);
                Toast.makeText(getContext(), "Kết nối API thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Load bài hát tiếp theo
    private void loadNextSong() {
        MusicService musicService = APIClient.getMusicservice();
        Call<Song> call = musicService.getNextSong(currentSong.getId());
        call.enqueue(new Callback<Song>() {
            @Override
            public void onResponse(@NonNull Call<Song> call, @NonNull Response<Song> response) {
                if (response.isSuccessful()) {
                    Song nextSong = response.body();
                    if (nextSong != null) {
                        currentSong = nextSong;
                        loadSongDetails(currentSong);
                    } else {
                        // Xử lý trường hợp không có bài hát tiếp theo
                        Toast.makeText(getContext(), "Không có bài hát tiếp theo", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.e(TAG, "Response unsuccessful: " + response.code() + ", " + response.message());
                    Toast.makeText(getContext(), "Lỗi khi lấy bài hát tiếp theo", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<Song> call, @NonNull Throwable t) {
                Log.e(TAG, "API call failed", t);
                Toast.makeText(getContext(), "Kết nối API thất bại", Toast.LENGTH_SHORT).show();
            }
        });
    }
}