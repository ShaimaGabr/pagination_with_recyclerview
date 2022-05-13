package com.example.pagination_with_recyclerview;

import com.example.pagination_with_recyclerview.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MovieService {
    @GET("posts")
    Call<List<Users>> getMovies();
}
