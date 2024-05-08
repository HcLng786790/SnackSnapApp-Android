package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.Promotion;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface PromotionApi {

    @PUT("/promotion/updateStatus")
    Call<Promotion> updateStatus(@Header("Authorization") String token,
                                 @Query("promotionId") Long promotionId,
                                 @Query("status") boolean status);

    @GET("/promotion/get-active")
    Call<List<Promotion>> getActive();

    @GET("/promotion/get-hide")
    Call<List<Promotion>> getHide(@Header("Authorization") String token);
}
