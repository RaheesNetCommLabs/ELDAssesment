package com.example.eldassesment.api;

import com.example.eldassesment.helper.Image;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Api {

    @Headers({"x-api-key: jvmNAyPNr1JhiCeUlYmB2ae517p3Th0aGG6syqMb"})
    @GET("/dev/getNormalVideoFiles")
    Call<List<Image>> getImage(@Query("offset") int offset, @Query("limit") int limit);

}
