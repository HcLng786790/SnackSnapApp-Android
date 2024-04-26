package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.DTO.CartDetailsDTO;
import com.huuduc.giuaky.data.cart.Cart;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CartApi {

    @GET("/cart/all")
    Call<List<Cart>> getAll();

    @GET("/cartDetails/{id}")
    Call<List<Cart>> findByCart(@Path("id") long id);

    @POST("/cartDetails/add/{cartId}")
    Call<Cart> addToCart(@Path("cartId") long cartId,
                         @Body CartDetailsDTO cartDetailsDTO);

    @PUT("/cartDetails/update")
    Call<Cart> updateCartDetails(@Query("cartDetailsId") long cartDetailsId,
                                   @Query("soLuong") int soLuong);

}
