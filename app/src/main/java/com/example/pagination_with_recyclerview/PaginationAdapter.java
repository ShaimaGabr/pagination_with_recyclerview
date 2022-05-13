package com.example.pagination_with_recyclerview;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.pagination_with_recyclerview.model.Users;

import java.util.LinkedList;
import java.util.List;

public class PaginationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private List<Users> movieList;
    private static final int LOADING = 0;
    private static final int ITEM = 1;
    private boolean isLoadingAdded = false;

    public PaginationAdapter(Context context) {
        this.context = context;
        movieList = new LinkedList<>();
    }

    public void setMovieList(List<Users> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        switch (viewType) {
            case ITEM:
                View viewItem = inflater.inflate(R.layout.item_list, parent, false);
                viewHolder = new MovieViewHolder(viewItem);
                break;
            case LOADING:
                View viewLoading = inflater.inflate(R.layout.item_progress, parent, false);
                viewHolder = new LoadingViewHolder(viewLoading);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        Users user = movieList.get(position);
        switch (getItemViewType(position)) {
            case ITEM:
                MovieViewHolder movieViewHolder = (MovieViewHolder) holder;
                movieViewHolder.movieTitle.setText(user.getTitle());
                break;

            case LOADING:
                LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
                loadingViewHolder.progressBar.setVisibility(View.VISIBLE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return movieList == null ? 0 : movieList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (position == movieList.size() - 1 && isLoadingAdded) ? LOADING : ITEM;
    }

    public void addLoadingFooter() {
        isLoadingAdded = true;
        add(new Users());
    }

    public void removeLoadingFooter() {
        isLoadingAdded = false;

        int position = movieList.size() - 1;
        Users result = getItem(position);

        if (result != null) {
            movieList.remove(position);
            notifyItemRemoved(position);
        }
    }

    public void add(Users user) {
        movieList.add(user);
        notifyItemInserted(movieList.size() - 1);
    }

    public void addAll(List<Users> moveResults) {
        for (Users user : moveResults) {
            add(user);
        }
    }

    public Users getItem(int position) {
        return movieList.get(position);
    }


    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private TextView movieTitle;
        private ImageView movieImage;

        public MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            movieImage = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }

    public class LoadingViewHolder extends RecyclerView.ViewHolder {

        private ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.loadmore_progress);

        }
    }

}
