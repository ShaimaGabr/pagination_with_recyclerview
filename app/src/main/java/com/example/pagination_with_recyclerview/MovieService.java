package com.example.pagination_with_recyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("volley_array.json")
   public Call<List<Movie>> getMovies();
}
