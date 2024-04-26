package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.orders.Orders;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface OrdersApi {

    @GET("/orders/all/{userId}")
    Call<List<Orders>> getAllByUser(@Path("userId") long userId);

    @POST("/orders/addNew")
    Call<Orders> orders(@Query("userId") long userId,
                        @Query("type") int type,
                        @Query("addressId") long addressId);


    @GET("/orders/status")
    Call<List<Orders>> getAllByStatus(@Query("statusId") long statusId,
                                      @Query("userId") long userId);

    @PUT("/orders/cancel/{ordersId}")
    Call<Orders> cancelOrders(@Path("ordersId") long ordersId);

    @PUT("/orders/again/{ordersId}")
    Call<Orders> againOrders(@Path("ordersId") long ordersId);

    @GET("/orders/status/{statusId}")
    Call<List<Orders>> getAll(@Path("statusId") long statusId);

    @PUT("/orders/update")
    Call<Orders> update(@Query("ordersId") long ordersId,
                        @Query("statusId") long statusId);

    @GET("/orders/count/{statusId}")
    Call<Long> getCount(@Header("Authorization") String token,
                        @Path("statusId") long statusId);

    @PUT("/orders/admin/cancel/{ordersId}")
    Call<Orders> cancelOrdersByAdmin(@Header("Authorization") String token
            ,@Path("ordersId") long ordersId);
}
