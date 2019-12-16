package com.bynn.marketll.module_home.network;

import okhttp3.FormBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface HomeApi {
    @GET("")
    void mockGet(@Query("name") String name);

    @POST("")
    void mockPost(@Body FormBody body);
}
