package com.huuduc.giuaky.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.data.cart.CartWr;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.retrofit.AddressApi;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckOutActivity extends AppCompatActivity {

    public static final int RADIO_BUTTON_1 = R.id.radioButton1;
    public static final int RADIO_BUTTON_2 = R.id.radioButton2;

    private int chose = 0;

    private ImageView iconBack;
    private TextView txtTotal, txtNameUser, txtLocation, txtPhone, noneAddress;
    public CartWr cartWr;
    private LinearLayout AddressLayout, layout_address;

    private Address address;

    private Button btnCheckout;

    private RadioGroup rdGroup;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        setControl();
        getBunble();

        loadAddress();
        setEvent();
    }

    private void loadAddress() {
        RetrofitService retrofitService = new RetrofitService();
        AddressApi addressApi = retrofitService.getRetrofit().create(AddressApi.class);
        addressApi.getDefaultsByUser(MainActivity.user.getId())
                .enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        if (response.isSuccessful()) {
                            layout_address.setVisibility(View.VISIBLE);
                            Address getAddress = response.body();
                            txtNameUser.setText(getAddress.getNameReceiver());
                            txtLocation.setText(getAddress.getLocation());
                            txtPhone.setText(getAddress.getPhoneReceiver());
                            address = getAddress;
                        } else {
                            noneAddress.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(CheckOutActivity.this, "Load address error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getBunble() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }
        cartWr = (CartWr) bundle.get("obj_cartWr");

        if (cartWr != null) {
            txtTotal.setText(String.format("%s", cartWr.getTotalPrice()));
        }

    }

    private void setEvent() {

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        AddressLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CheckOutActivity.this, AddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("address", address);
                intent.putExtras(bundle);
                startActivityForResult(intent, 99);
            }
        });

        // Sự kiện lắng nghe khi có RadioButton thay đổi
        rdGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                double total = cartWr.getTotalPrice();

                if (checkedId == RADIO_BUTTON_1) {
                    txtTotal.setText(total + 25 + "");
                    chose = 1;
                } else {
                    txtTotal.setText(total + "");
                    chose = 2;
                }
            }
        });


        btnCheckout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createOrder();
            }
        });


    }

    private void createOrder() {

        if(address==null){
            Toast.makeText(CheckOutActivity.this,"Please chose address",Toast.LENGTH_SHORT).show();
            return;
        }

        if (chose == 0) {
            Toast.makeText(CheckOutActivity.this, "Please chose delivery type", Toast.LENGTH_SHORT).show();
        }else{
            RetrofitService retrofitService = new RetrofitService();
            OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
            ordersApi.orders(MainActivity.user.getId(),chose,address.getId())
                    .enqueue(new Callback<Orders>() {
                        @Override
                        public void onResponse(Call<Orders> call, Response<Orders> response) {
                            Toast.makeText(CheckOutActivity.this, "Orders sucess and check in profile", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFailure(Call<Orders> call, Throwable t) {
                            Toast.makeText(CheckOutActivity.this, "Call api error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    private void setControl() {
        iconBack = findViewById(R.id.iconBack);
        txtTotal = findViewById(R.id.txtTotal);
        txtNameUser = findViewById(R.id.txtNameUser);
        txtLocation = findViewById(R.id.txtLocation);
        txtPhone = findViewById(R.id.txtPhone);
        AddressLayout = findViewById(R.id.AddressLayout);
        btnCheckout = findViewById(R.id.btnCheckout);
        layout_address = findViewById(R.id.layout_address);
        noneAddress = findViewById(R.id.noneAddress);
        rdGroup = findViewById(R.id.rdGroup);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99 && resultCode == Activity.RESULT_OK && data != null) {
            Bundle bundle = data.getExtras();
            if (bundle == null) {
                return;
            }
            address = (Address) bundle.get("chose");
            txtNameUser.setText(address.getNameReceiver());
            txtLocation.setText(address.getLocation());
            txtPhone.setText(address.getPhoneReceiver());
        }
    }
}