package com.huuduc.giuaky.retrofit;

import com.huuduc.giuaky.data.ThongKe;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.data.product.Product;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ProductApi {

    @GET("/product/favorite")
    Call<List<Product>> getAllByFavorite();

    @GET("/product/{type}")
    Call<List<Product>> getAllByType(@Path("type") int type);

    @GET("/product/all")
    Call<List<Product>> getAll();

    @Multipart
    @POST("/product/create2")
    Call<Product> create2(@Part("product") RequestBody product,
                          @Part MultipartBody.Part fileImg);

    @Multipart
    @PUT("/product/update/{productId}")
    Call<User> updateById(@Path("productId") long productId,
                          @Part("productDTORequest") RequestBody product,
                          @Part MultipartBody.Part fileImage);

    @Multipart
    @PUT("/product/update/{productId}")
    Call<User> updateById(@Path("productId") long productId,
                          @Part("productDTORequest") RequestBody product);

    @DELETE("/product/delete/{productId}")
    Call<User> delete(@Path("productId") long productId);

    @GET("/product/thong-ke")
    Call<List<ThongKe>> thongKe();
}
