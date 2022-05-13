package com.example.pagination_with_recyclerview;

import com.example.pagination_with_recyclerview.model.Users;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientApi {

private static final String BASE_URL = "https://jsonplaceholder.typicode.com/";
    private MovieService postInterface;
    private static ClientApi INSTANCE;
    public ClientApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        postInterface = retrofit.create(MovieService.class);
    }

    public static ClientApi getINSTANCE() {
        if (null == INSTANCE){
            INSTANCE = new ClientApi();
        }
        return INSTANCE;
    }
    public Call<List<Users>> getMovies(){
        return postInterface.getMovies();
    }

}
