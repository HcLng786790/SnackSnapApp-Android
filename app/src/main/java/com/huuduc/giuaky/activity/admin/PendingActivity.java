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

public class PendingActivity extends AppCompatActivity {

    private RecyclerView rcvPending;

    private AdminOrdersAdapter adapter;
    private ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending);
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
        rcvPending = findViewById(R.id.rcv_pending);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvPending.setLayoutManager(linearLayoutManager);

        loadOrders();

    }

    private void loadOrders() {

        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getAll(Orders.CONFIRM)
                .enqueue(new Callback<List<Orders>>() {
                    @Override
                    public void onResponse(Call<List<Orders>> call, Response<List<Orders>> response) {
                        populateListView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Orders>> call, Throwable t) {
                        Toast.makeText(PendingActivity.this, "Save error", Toast.LENGTH_SHORT).show();
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
        rcvPending.setAdapter(adapter);
    }

    private void onClickToDetail(Orders orders) {
        Intent intent = new Intent(PendingActivity.this, OrdersDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_orders", orders);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}