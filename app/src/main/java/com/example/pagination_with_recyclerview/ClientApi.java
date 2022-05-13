package com.example.pagination_with_recyclerview;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
// .baseUrl("https://velmm.com/apis/")
public class ClientApi {
//    private static Retrofit retrofit = null;
//
//    public static Retrofit getClient() {
//        if (retrofit == null) {
//            retrofit = new Retrofit.Builder()
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .baseUrl("https://velmm.com/apis/")
//                    .build();
//        }
//        return retrofit;
//    }
private static final String BASE_URL = "http://jsonplaceholder.typicode.com/";
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
    public Call<List<Movie>> getMovies(){
        return postInterface.getMovies();
    }

}
