package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.adapter.OrderDetailsAdapter;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.orders.Orders;

import java.util.ArrayList;
import java.util.List;

public class OrdersDetailsActivity extends AppCompatActivity {

    private ImageView iconBack;
    private ListView lvOrderDetails;
    private List<Cart> cartList = new ArrayList<>();

    private OrderDetailsAdapter orderDetailsAdapter;

    private TextView txtNameReceiver,txtPhoneReceiver,txtAddressReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_details);
        setControl();
        getBundle();
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

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle==null){
            return;
        }

        Orders orders = (Orders) bundle.get("obj_orders");

        txtNameReceiver.setText(orders.getAddress().getNameReceiver());
        txtPhoneReceiver.setText(orders.getAddress().getPhoneReceiver());
        txtAddressReceiver.setText(orders.getAddress().getLocation());
        cartList=orders.getCartList();

        orderDetailsAdapter = new OrderDetailsAdapter(this,R.layout.layout_orders_details,cartList);
        lvOrderDetails.setAdapter(orderDetailsAdapter);
    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        lvOrderDetails=findViewById(R.id.lv_ordersDetails);
        txtNameReceiver=findViewById(R.id.txtNameReceiver);
        txtPhoneReceiver=findViewById(R.id.txtPhoneReceiver);
        txtAddressReceiver=findViewById(R.id.txtAddressReceiver);
    }
}