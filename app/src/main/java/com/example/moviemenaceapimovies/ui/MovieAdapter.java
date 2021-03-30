package com.example.moviemenaceapimovies.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemenaceapimovies.R;
import com.example.moviemenaceapimovies.domain.Movie;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ActorViewHolder> {

    private List<Movie> movies;

    public MovieAdapter(List<Movie> movies) {
        this.movies = movies;
    }

    @NonNull
    @Override
    public ActorViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.movieid_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ActorViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActorViewHolder holder, int position) {
        Movie movie = movies.get(position);

        holder.mMovieID.setText(movie.getId() + " " + position);
        holder.mMovieTitle.setText(movie.toString());
    }

    @Override
    public int getItemCount() {
        if (null == movies) {
            return 0;
        }
        return movies.size();
    }

    public class ActorViewHolder extends RecyclerView.ViewHolder {

        private TextView mMovieTitle;
        private TextView mMovieID;

        public ActorViewHolder(@NonNull View itemView) {
            super(itemView);
            mMovieID = (TextView) itemView.findViewById(R.id.tv_movie_id);
            mMovieTitle = (TextView) itemView.findViewById(R.id.tv_movie_title);
        }

    }
}
