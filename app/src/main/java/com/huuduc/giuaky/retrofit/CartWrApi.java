package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.cart.CartWr;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CartWrApi {

    @GET("/cart/{id}")
    Call<CartWr> getById(@Path("id") long id);
}
