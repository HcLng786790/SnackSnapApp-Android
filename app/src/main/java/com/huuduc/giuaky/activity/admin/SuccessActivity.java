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
import com.huuduc.giuaky.activity.OrdersDetailsActivity;
import com.huuduc.giuaky.adapter.AdminOrdersAdapter;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.interfaces.OrdersClickListener;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuccessActivity extends AppCompatActivity {

    private RecyclerView rcvSuccess;
    private ImageView iconBack;
    private AdminOrdersAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        setControl();
        setEvent();
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
        rcvSuccess=findViewById(R.id.rcv_success);
        iconBack=findViewById(R.id.iconBack);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvSuccess.setLayoutManager(linearLayoutManager);

        loadOrders();
    }

    private void loadOrders() {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getAll(Orders.DELIVERED)
                .enqueue(new Callback<List<Orders>>() {
                    @Override
                    public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Orders>> call, Throwable t) {
                        Toast.makeText(SuccessActivity.this, "Save error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateListView(List<Orders> body) {
        adapter = new AdminOrdersAdapter(this, body, new OrdersClickListener() {
            @Override
            public void onClickOrders(Orders orders) {
                onClickToDetail(orders);
            }
        });
        rcvSuccess.setAdapter(adapter);
    }

    private void onClickToDetail(Orders orders) {
        Intent intent = new Intent(SuccessActivity.this, OrdersDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_orders", orders);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}