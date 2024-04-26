package com.huuduc.giuaky.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.ThongKeActivity;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardActivity extends AppCompatActivity {

    private ImageView iconAdd;
    private LinearLayout lnPending,lnCooking,lnDeli,lnUsers,lnGetAll,lnLogOut,lnThongKe;

    private TextView txtPendingCount,txtCookingCount,txtDeliCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        setControl();
        setEvent();
    }

    private void setEvent() {
        lnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AllProductActivity.class);
                startActivity(intent);
            }
        });

        iconAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this,AddProductActivity.class);
                startActivity(intent);
            }
        });

        lnPending.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, PendingActivity.class);
                startActivity(intent);
            }
        });

        lnCooking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, CookingActivity.class);
                startActivity(intent);
            }
        });

        lnDeli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, DeliActivity.class);
                startActivity(intent);
            }
        });

        lnUsers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ListUserActivity.class);
                startActivity(intent);
            }
        });

        lnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, AdminLoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lnThongKe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DashboardActivity.this, ThongKeActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setControl() {
        lnGetAll=findViewById(R.id.lnGetAll);
        lnUsers=findViewById(R.id.lnUsers);
        iconAdd = findViewById(R.id.iconAdd);
        lnPending = findViewById(R.id.lnPending);
        lnCooking = findViewById(R.id.lnCook);
        lnDeli = findViewById(R.id.lnDeli);
        txtPendingCount = findViewById(R.id.txtPendingCount);
        txtCookingCount = findViewById(R.id.txtCookingCount);
        txtDeliCount = findViewById(R.id.txtDeliCount);
        lnLogOut=findViewById(R.id.lnLogOut);
        lnThongKe=findViewById(R.id.lnThongKe);

        loadCount();
    }

    private void loadCount() {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getCount(AdminLoginActivity.token,Orders.CONFIRM)
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()) {
                            txtPendingCount.setText(response.body().toString());
                        }else{
                            Toast.makeText(DashboardActivity.this,"Không có quyền",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });

        ordersApi.getCount(AdminLoginActivity.token,Orders.DELIVERING)
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()) {
                            txtDeliCount.setText(response.body().toString());
                        }else{
                            Toast.makeText(DashboardActivity.this,"Không có quyền",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });

        ordersApi.getCount(AdminLoginActivity.token,Orders.COOKING)
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()) {
                            txtCookingCount.setText(response.body().toString());
                        }else{
                            Toast.makeText(DashboardActivity.this,"Không có quyền",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {

                    }
                });
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadCount();
    }
}