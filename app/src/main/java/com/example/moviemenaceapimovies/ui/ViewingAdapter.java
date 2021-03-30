package com.example.moviemenaceapimovies.ui;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewingAdapter {

    public class ViewingViewHolder extends RecyclerView.ViewHolder {

        private TextView mViewingId;
        private TextView mViewingTime;
        private TextView mViewingDate;

        public ViewingViewHolder(@NonNull View itemView) {
            super(itemView);

        }
    }
}
