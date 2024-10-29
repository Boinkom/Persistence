package com.example.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private final String API_KEY = "..."; // Замените на ваш ключ TMDb

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sharedPref = getSharedPreferences("app_preferences", MODE_PRIVATE);
        int launchCount = sharedPref.getInt("launch_count", 0);

        if ((launchCount + 1) % 3 == 0) {
            startActivity(new Intent(this, IntroActivity.class));
        }

        sharedPref.edit().putInt("launch_count", launchCount + 1).apply();

        loadTopRatedMovies();
    }

    private void loadTopRatedMovies() {
        TMDbApi apiService = RetrofitClient.getClient();
        Call<MovieResponse> call = apiService.getTopRatedMovies(API_KEY, "en-US");

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<Movie> movies = response.body().getResults();
                    for (Movie movie : movies) {
                        Toast.makeText(MainActivity.this, "Movie: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Failed to retrieve movies", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
