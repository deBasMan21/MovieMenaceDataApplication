package com.example.moviemenaceapimovies.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.moviemenaceapimovies.R;
import com.example.moviemenaceapimovies.domain.Movie;
import com.example.moviemenaceapimovies.domain.Viewing;
import com.example.moviemenaceapimovies.logic.ViewingAsyncTask;

import java.util.ArrayList;

public class MovieActivity extends AppCompatActivity implements ViewingAsyncTask.ViewingListener {

    private static final String INTENT_EXTRA_MOVIE_KEY = "movie";

    private ArrayList<Viewing> viewings = new ArrayList<>();
    private Movie movie;

    private TextView mMovieTitle;
    private RecyclerView mRecyclerView;
    private ViewingAdapter mViewingAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent originalIntent = getIntent();
        if (originalIntent.getParcelableExtra(INTENT_EXTRA_MOVIE_KEY) != null) {
            movie = (Movie) originalIntent.getParcelableExtra(INTENT_EXTRA_MOVIE_KEY);
        }

        mMovieTitle = findViewById(R.id.tv_movie_activity_title);
        mMovieTitle.setText(movie.getTitle());

        mRecyclerView = findViewById(R.id.viewing_recyclerview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);
        mViewingAdapter = new ViewingAdapter(this.viewings);
        mRecyclerView.setAdapter(mViewingAdapter);

        new ViewingAsyncTask(this).execute(movie);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewingsAvailable(ArrayList<Viewing> viewings) {
        this.viewings.clear();
        this.viewings.addAll(viewings);
        this.mViewingAdapter.notifyDataSetChanged();
    }
}