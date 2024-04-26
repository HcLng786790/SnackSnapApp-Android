package com.huuduc.giuaky.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.DTO.CartDetailsDTO;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.retrofit.CartApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailActivity extends AppCompatActivity {

    private Button btnAddToCart;
    private TextView txtName, txtPrice, txtNumberOrders;
    private ImageView imvProduct, imbtnMinus, imgbtnAdd, iconBack;
    private Product product;
    private int numberOrders = 1;
    public Product thisProduct;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetails);
        setControl();
        setEvent();
        getBunble();

    }

    public void getBunble() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        Product product = (Product) bundle.get("obj_product");

        thisProduct = product;

        txtName.setText(product.getName());
        txtPrice.setText(product.getPriceFormat() + " đ");
//        imvProduct.setImageResource(product.getResoureId(this));
        Glide.with(this)
                .load(product.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imvProduct);
    }

    private void setControl() {
        btnAddToCart = findViewById(R.id.btnAddToCart);
        txtName = findViewById(R.id.txtName);
        txtPrice = findViewById(R.id.txtPrice);
        txtNumberOrders = findViewById(R.id.txtNumberOrders);
        imvProduct = findViewById(R.id.imvProduct);
        imbtnMinus = findViewById(R.id.imbtnMinus);
        imgbtnAdd = findViewById(R.id.imbtnAdd);
        iconBack = findViewById(R.id.iconBack);
    }

    private void setEvent() {

        //Cho dữ liệu mặc định ở ô số lượng bằng 1
        txtNumberOrders.setText(String.valueOf(numberOrders));

        //Xử lý nút cộng ở chi tiết sp
        imgbtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                numberOrders += 1;
                txtNumberOrders.setText(String.valueOf(numberOrders));
            }
        });

        //Xử lý nút trừ ở chi tiết sp
        imbtnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (numberOrders > 1) {
                    numberOrders = numberOrders - 1;
                }
                txtNumberOrders.setText(String.valueOf(numberOrders));
            }
        });

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        btnAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart();
            }
        });
    }

    private void addToCart() {
        CartDetailsDTO cartDetailsDTO = new CartDetailsDTO(
                thisProduct.getId(),
                Integer.parseInt(txtNumberOrders.getText().toString())

        );
        RetrofitService retrofitService = new RetrofitService();
        CartApi cartApi = retrofitService.getRetrofit().create(CartApi.class);
        cartApi.addToCart(MainActivity.user.getCartId(), cartDetailsDTO)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        Intent intent = new Intent(getApplicationContext(), CartAvtivity.class);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(ShowDetailActivity.this, "Update error", Toast.LENGTH_SHORT).show();
                    }
                });
    }


//    public void updateCart(Cart cart, int sl,double price){
//
//        cart.setSoluongSP(cart.getSoluongSP()+sl);
//        cart.setPriceSP(cart.getSoluongSP()* price);
//
//    }
//
//    public void createNewCart(int sl,Product product,List<Cart> cartList){
//
//        long newPrice = (long) (sl*product.getPrice());
//        cartList.add(new Cart(product.getId(),product.getName(),newPrice,product.getImg(),sl));
//    }

}
