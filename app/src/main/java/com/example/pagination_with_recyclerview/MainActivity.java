package com.example.pagination_with_recyclerview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.pagination_with_recyclerview.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    PaginationAdapter paginationAdapter;
    RecyclerView recyclerView;

    ProgressBar progressBar;
    Boolean isLoading;
    Boolean isLastPage;
    int currentPage;
    int TOTAL_PAGES = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerview);
        progressBar = findViewById(R.id.progressbar);
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
        ClientApi.getINSTANCE().getMovies().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                paginationAdapter.removeLoadingFooter();
                isLoading = false;

                List<Users> results = response.body();
                paginationAdapter.addAll(results);

                if (currentPage != TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {
            }
        });
    }


    private void loadFirstPage() {
        ClientApi.getINSTANCE().getMovies().enqueue(new Callback<List<Users>>() {
            @Override
            public void onResponse(Call<List<Users>> call, Response<List<Users>> response) {
                List<Users> results = response.body();
                progressBar.setVisibility(View.GONE);

                if (results != null)
                    paginationAdapter.addAll(results);
                else
                    Toast.makeText(getApplicationContext(), "null", Toast.LENGTH_SHORT).show();


                if (currentPage <= TOTAL_PAGES) paginationAdapter.addLoadingFooter();
                else isLastPage = true;
            }

            @Override
            public void onFailure(Call<List<Users>> call, Throwable t) {

            }
        });
    }
}