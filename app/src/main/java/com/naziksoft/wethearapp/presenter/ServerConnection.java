package com.naziksoft.wethearapp.presenter;

import com.naziksoft.wethearapp.entity.ServerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by nazar on 25.03.18.
 */

public interface ServerConnection {
    @GET("/forecast/{key}/{latitude},{longitude}")
    Call<ServerResponse> getFromServer(@Path("key") String key,
                                       @Path("latitude") float latitude,
                                       @Path("longitude") float longitude,
                                       @Query("exclude") String direction);
}
