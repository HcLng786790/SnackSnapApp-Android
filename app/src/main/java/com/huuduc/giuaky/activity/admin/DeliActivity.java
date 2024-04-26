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

public class DeliActivity extends AppCompatActivity {

    private RecyclerView rcvDeli;

    private AdminOrdersAdapter adapter;
    private ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deli);
        setControl();
        seEvent();
    }

    private void seEvent() {
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        rcvDeli = findViewById(R.id.rcv_deli);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvDeli.setLayoutManager(linearLayoutManager);

        loadOrders();

    }

    private void loadOrders() {

        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getAll(Orders.DELIVERING)
                .enqueue(new Callback<List<Orders>>() {
                    @Override
                    public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Orders>> call, Throwable t) {
                        Toast.makeText(DeliActivity.this, "Save error", Toast.LENGTH_SHORT).show();
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
        rcvDeli.setAdapter(adapter);
    }

    private void onClickToDetail(Orders orders) {
        Intent intent = new Intent(DeliActivity.this, OrdersDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_orders", orders);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}