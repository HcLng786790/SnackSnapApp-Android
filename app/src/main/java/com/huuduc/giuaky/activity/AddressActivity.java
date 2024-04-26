package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.adapter.AddressAdapter;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.data.cart.CartWr;
import com.huuduc.giuaky.retrofit.AddressApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressActivity extends AppCompatActivity {

    private ImageView iconBack;
    private ListView lvAddress;

    private List<Address> addresses = new ArrayList<>();

    private AddressAdapter addressAdapter;

    private Address selecAddress;

    private Button btnAddAddreess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address);
        setControl();
        loadData();
        setEvent();
    }

    private void setEvent() {

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        selecAddress = (Address) bundle.get("address");


        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                addressAdapter.setSelectedPosition(position);
                addressAdapter.setSelectedAddress(addresses.get(position));
                addressAdapter.notifyDataSetChanged();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("chose", addresses.get(position));
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        btnAddAddreess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddressActivity.this, AddAddressActivity.class);
                startActivity(intent);
            }
        });

    }

    private void loadData() {
        RetrofitService retrofitService = new RetrofitService();
        AddressApi addressApi = retrofitService.getRetrofit().create(AddressApi.class);
        addressApi.getAll(MainActivity.user.getId())
                .enqueue(new Callback<List<Address>>() {
                    @Override
                    public void onResponse(Call<List<Address>> call, Response<List<Address>> response) {
                        addresses = response.body();
                        setLisView(addresses);
                    }

                    @Override
                    public void onFailure(Call<List<Address>> call, Throwable t) {
                        Toast.makeText(AddressActivity.this, "Save error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setLisView(List<Address> addresses) {

//        if(selecAddress!=null) {
//            addressAdapter = new AddressAdapter(this, R.layout.layout_address_item, addresses);
//            addressAdapter.setSelectedAddress(selecAddress);
//        }else{
//            addressAdapter = new AddressAdapter(this, R.layout.layout_address_item, addresses);
//        }
        addressAdapter = new AddressAdapter(this, R.layout.layout_address_item, addresses);
        addressAdapter.setSelectedAddress(selecAddress);
        lvAddress.setAdapter(addressAdapter);

    }

    private void setControl() {
        iconBack = findViewById(R.id.iconBack);
        lvAddress = findViewById(R.id.lvAddress);
        btnAddAddreess = findViewById(R.id.btnAddAddreess);

    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }
}