package com.example.pagination_with_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
     PaginationAdapter  paginationAdapter;
     RecyclerView recyclerView;

    ProgressBar progressBar;
    Boolean isLoading;
    Boolean isLastPage;
    int currentPage;
    int TOTAL_PAGES=10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerview);
        progressBar=  findViewById(R.id.progressbar) ;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        paginationAdapter = new PaginationAdapter(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(paginationAdapter);

        recyclerView.addOnScrollListener(new PaginationScrollListener(linearLayoutManager) {
            @Override
            protected void loadMoreItems() {
                isLoading = true;
                currentPage += 1;
                loadNextPage();
            }

            @Override
            public boolean isLastPage() {
                return isLastPage;
            }

            @Override
            public boolean isLoading() {
                return true;
            }
        });

        loadFirstPage();
    }

    private void loadNextPage() {
ClientApi.getINSTANCE().getMovies().enqueue(new Callback<List<Movie>>() {
    @Override
    public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
        paginationAdapter.removeLoadingFooter();
        isLoading = false;

        List<Movie> results = response.body();
        paginationAdapter.addAll(results);

        if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
        else isLastPage = true;
    }

    @Override
    public void onFailure(Call<List<Movie>> call, Throwable t) {

    }
});
//        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
//            @Override
//            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
//                paginationAdapter.removeLoadingFooter();
//                isLoading = false;
//
//                List<Movie> results = response.body();
//                paginationAdapter.addAll(results);
//
//                if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<Movie>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }


    private void loadFirstPage() {
        ClientApi.getINSTANCE().getMovies().enqueue(new Callback<List<Movie>>() {
            @Override
            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
                List<Movie> results = response.body();
                progressBar.setVisibility(View.GONE);

                    paginationAdapter.addAll(results);
                    
                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Movie>> call, Throwable t) {

            }
        });

//        movieService.getMovies().enqueue(new Callback<List<Movie>>() {
//            @Override
//            public void onResponse(Call<List<Movie>> call, Response<List<Movie>> response) {
//                List<Movie> results = response.body();
//                progressBar.setVisibility(View.GONE);
//                paginationAdapter.addAll(results);
//
//                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
//                else isLastPage = true;
//            }
//
//            @Override
//            public void onFailure(Call<List<Movie>> call, Throwable t) {
//
//            }
//
//        });
    }}