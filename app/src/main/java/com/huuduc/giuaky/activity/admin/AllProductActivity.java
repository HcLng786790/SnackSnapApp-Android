package com.huuduc.giuaky.activity.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.adapter.AdminProductAdapter;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllProductActivity extends AppCompatActivity {

    private ImageView iconBack;
    private RecyclerView rcvProduct;
    private AdminProductAdapter productAdapter;
    private List<Product> mProductLis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        setControl();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvProduct.setLayoutManager(linearLayoutManager);
//        cartAdapter = new CartAdapter(CartAvtivity.this);
        loadProduct();
        setEvent();
    }

    private void loadProduct() {
        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        productApi.getAll()
                .enqueue(new Callback<List<Product>>() {
                    @Override
                    public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Product>> call, Throwable t) {
                        Toast.makeText(AllProductActivity.this, "Load error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Product> body) {
        productAdapter = new AdminProductAdapter(this, body, new ItemClickListener() {
            @Override
            public void onClickItem(Product product) {
                onClickToDetail(product);
            }
        });
        rcvProduct.setAdapter(productAdapter);
    }

    private void onClickToDetail(Product product) {
        Intent intent= new Intent(AllProductActivity.this, AddProductActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_product",product);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setEvent() {
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        rcvProduct=findViewById(R.id.rcv_product);
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadProduct();
    }
}