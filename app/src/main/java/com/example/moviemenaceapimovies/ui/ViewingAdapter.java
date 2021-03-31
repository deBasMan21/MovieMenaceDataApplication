package com.example.moviemenaceapimovies.ui;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviemenaceapimovies.R;
import com.example.moviemenaceapimovies.domain.Viewing;

import java.util.ArrayList;

public class ViewingAdapter extends RecyclerView.Adapter<ViewingAdapter.ViewingViewHolder> {

    private ArrayList<Viewing> viewings;

    public  ViewingAdapter(ArrayList<Viewing> viewings) {
        this.viewings = viewings;
    }

    @NonNull
    @Override
    public ViewingViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        int layoutIdForListItem = R.layout.viewing_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(layoutIdForListItem, viewGroup, false);
        return new ViewingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewingViewHolder holder, int position) {
        Viewing viewing = viewings.get(position);

        holder.mViewingId.setText(viewing.getId() + "");
        holder.mViewingTime.setText(viewing.getTimeFormatted());
        holder.mViewingDate.setText(viewing.getDateFormatted());
    }

    @Override
    public int getItemCount() {
        if (null == viewings) {
            return 0;
        }
        return viewings.size();
    }


    public class ViewingViewHolder extends RecyclerView.ViewHolder {

        private TextView mViewingId;
        private TextView mViewingTime;
        private TextView mViewingDate;

        public ViewingViewHolder(@NonNull View itemView) {
            super(itemView);
            mViewingId = itemView.findViewById(R.id.tv_viewing_id);
            mViewingTime = itemView.findViewById(R.id.tv_viewing_time);
            mViewingDate = itemView.findViewById(R.id.tv_viewing_date);
        }
    }
}
