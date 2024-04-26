package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.DTO.ChangePassDTO;
import com.huuduc.giuaky.data.DTO.RegisterDTORequest;
import com.huuduc.giuaky.data.User.User;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserApi {

    @GET("/user/{cartId}")
    Call<User> getByCartId(@Path("cartId") long cartId);

    @GET("/user/get/{id}")
    Call<User> getById(@Path("id") long id);

    @POST("/user/register")
    Call<User> register(@Body RegisterDTORequest registerDTORequest);

    @Multipart
    @PUT("/user/{id}")
    Call<User> updateById(@Path("id") long id,
                          @Part("registerDTORequest") RequestBody registerDTORequest,
                          @Part MultipartBody.Part fileAvatar);

    @Multipart
    @PUT("/user/{id}")
    Call<User> updateById(@Path("id") long id,
                          @Part("registerDTORequest") RequestBody registerDTORequest);

    @PUT("/user/verify")
    Call<User> verify(@Query("email") String email,
                        @Query("otp") String otp);

    @PUT("/user/change-password-by-token")
    Call<User> changePassByToken(@Header("Authorization") String token,
            @Body ChangePassDTO changePassDTO);

    @GET("/user/all")
    Call<List<User>> getAll(@Header("Authorization") String token);

}
