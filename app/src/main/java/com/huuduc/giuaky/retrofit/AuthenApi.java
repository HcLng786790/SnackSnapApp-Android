package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.DTO.AuthenDTORequest;
import com.huuduc.giuaky.data.DTO.AuthenDTOResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface AuthenApi {

    @POST("/login")
    Call<AuthenDTOResponse> login(@Body AuthenDTORequest authenDTORequest);
}
