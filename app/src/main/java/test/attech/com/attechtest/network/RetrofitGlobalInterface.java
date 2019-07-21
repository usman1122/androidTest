package test.attech.com.attechtest.network;


import android.arch.lifecycle.LiveData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;
import test.attech.com.attechtest.model.MyPojo;
import test.attech.com.attechtest.model.resultsModel;
public interface RetrofitGlobalInterface {
//    https://api.themoviedb.org/3/discover/movie?primary_release_year=2010&sort_by=vote_average.desc&api_key=e95c5c33d38a6be5f25c91d64c8d80e0&page=1
//    @Headers({
//            "Content-type: application/json"
//    })
    @GET("/3/discover/movie?primary_release_year=2010&sort_by=vote_average.desc")
    Call<resultsModel>  createTask(@Query("page") int pageNum, @Query("api_key") String myKey);


//    @GET("3/discover/movie?primary_release_year=2010&sort_by=vote_average.desc&api_key=e95c5c33d38a6be5f25c91d64c8d80e0&page=1")
//    Call<resultsModel> createTask();
}
