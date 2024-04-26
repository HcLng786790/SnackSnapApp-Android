package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.address.Address;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AddressApi {

    @GET("/address/defaults/{userId}")
    Call<Address> getDefaultsByUser(@Path("userId") long userId);

    @GET("/address/all/{userId}")
    Call<List<Address>> getAll(@Path("userId") long userId);

    @POST("/address/add/{userId}")
    Call<Address> create(@Path("userId") long userId,
                         @Body Address address);
}
