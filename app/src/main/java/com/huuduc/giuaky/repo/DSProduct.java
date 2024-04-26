package com.huuduc.giuaky.repo;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.adapter.ProductAdapter;
import com.huuduc.giuaky.data.product.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DSProduct {

    private List<Product> products;
    private int size;

    public DSProduct() {
//        this.products = this.getProducts();
        this.size=this.products.size();
//        this.products= new ArrayList<>();
    }


//    public List<Product> getListByType(int type,List<Product> list){
//
//        List<Product> newList = new ArrayList<>();
//
//        for (Product product : list){
//            if(product.getType()==type){
//                newList.add(product);
//            }
//        }
//        return newList;
//
//
//    }

    public List<Product> getListByFavorite(List<Product> list){
        List<Product> newList = new ArrayList<>();

        for (Product product : list){
            if(product.isFavorite()){
                newList.add(product);
            }
        }
        return newList;
    }


    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

//    public List<Product> getProducts() {
//
//        List<Product> list = new ArrayList<>();
//
//        list.add(new Product("Salad", 30, Product.FOOD, "food", true));
//        list.add(new Product("Nước ngọt", 40, Product.DRINK, "food", true));
//        list.add(new Product("Canh cá", 50, Product.SAUCE, "food", true));
//        list.add(new Product("Salad cá ngừ", 60, Product.FOOD, "food", false));
//        list.add(new Product("Bánh tráng", 10, Product.SNACK, "food", true));
//        list.add(new Product("Sacue", 15, Product.SAUCE, "food", false));
//        list.add(new Product("Mì trộn", 30, Product.FOOD, "food", true));
//        list.add(new Product("Tokboki", 30, Product.SAUCE, "food", false));
//        list.add(new Product("Bánh mì", 30, Product.SNACK, "food", true));
//        return list;
//    }

//    public List<Product> getProducts(){
//
//    }
//
//    public void getData(Context context, ProductAdapter productAdapter) {
//        String url = "http://localhost:8080/product/all"; // Thay đổi đường dẫn URL của API tại đây
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//
//        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
//                new Response.Listener<JSONArray>() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        products.clear();
//                        for (int i = 0; i < response.length(); i++) {
//                            try {
//                                JSONObject jsonObject = response.getJSONObject(i);
//                                Product product = new Product();
//                                product.setName(jsonObject.getString("name"));
//                                product.setPrice(jsonObject.getDouble("price"));
//                                product.setType(jsonObject.getInt("type"));
//                                product.setImg(jsonObject.getInt("img"));
//                                product.setFavorite(jsonObject.getBoolean("isFavorite"));
//                                // Tiếp tục lấy các thuộc tính khác của sản phẩm từ JSON và thiết lập cho sản phẩm
//                                products.add(product);
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                            productAdapter.notifyDataSetChanged();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // Xử lý lỗi khi gọi API
//                        Log.d("getData", error.toString());
//
//                    }
//                });
//
//        requestQueue.add(jsonArrayRequest);
//    }



}
