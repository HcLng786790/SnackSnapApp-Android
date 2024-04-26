package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.adapter.CartAdapter;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.cart.CartWr;
import com.huuduc.giuaky.repo.DSProduct;
import com.huuduc.giuaky.retrofit.CartApi;
import com.huuduc.giuaky.retrofit.CartWrApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAvtivity extends AppCompatActivity {

    private ImageView iconBack;
    private RecyclerView rcvCart;
    private CartAdapter cartAdapter;
    private Button btnOrder;
    static TextView txtTotal, txt_quantity;
    public CartWr cartWr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        setControl();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvCart.setLayoutManager(linearLayoutManager);

        cartAdapter = new CartAdapter(CartAvtivity.this);

        loadCart();

        //Xử lý lắng nghe khi có thay đổi từ adapter sẽ tiến hành call api lại 1 lần nữa
        cartAdapter.setOnQuantityChangedListener(new CartAdapter.OnQuantityChangedListener() {
            @Override
            public void onQuantityChanged() {
                loadCart();
            }
        });

        setEvent();
    }

    public void loadCart() {
        RetrofitService retrofitService = new RetrofitService();
        CartWrApi cartWrApi = retrofitService.getRetrofit().create(CartWrApi.class);
        cartWrApi.getById(MainActivity.user.getCartId())
                .enqueue(new Callback<CartWr>() {
                    @Override
                    public void onResponse(Call<CartWr> call, Response<CartWr> response) {

                        if (response.isSuccessful()) {
                            cartWr = response.body();
                            if (cartWr != null) {
                                cartAdapter.setData(cartWr.getCartDetailsDTOResponseList());
                            }
                            rcvCart.setAdapter(cartAdapter);
                            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                            txtTotal.setText(decimalFormat.format(cartWr.getTotalPrice()) + "Đ");

                            if(cartWr.getCartDetailsDTOResponseList().size()==0){
                                btnOrder.setBackgroundColor(Color.GRAY);
                            }else{
                                gotoCheckOut();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<CartWr> call, Throwable t) {
                        Toast.makeText(CartAvtivity.this, "Save error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void gotoCheckOut() {
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CartAvtivity.this, CheckOutActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("obj_cartWr", cartWr);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }


    public void setControl() {
        iconBack = findViewById(R.id.iconBack);
        rcvCart = findViewById(R.id.rcv_cart);
        txtTotal = findViewById(R.id.txtTotal);
        txt_quantity = findViewById(R.id.txt_quantity);
        btnOrder = findViewById(R.id.btnOrder);
    }

    public void setEvent() {
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCart();
    }
}