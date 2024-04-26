package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.retrofit.AddressApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.PropertyPermission;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAddressActivity extends AppCompatActivity {


    private SwitchCompat switchCompat;
    private EditText edtLocation, edtNameReceiver, edtPhoneReceiver;
    private Button btnCreate;
    private ImageView iconBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
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


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    Toast.makeText(AddAddressActivity.this, "ON", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(AddAddressActivity.this, "OFF", Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAddress();
            }
        });
    }

    private void createAddress() {

        if (edtLocation.getText().toString().equals("")) {
            edtLocation.setError("Please enter your location");
            edtLocation.requestFocus();
            return;
        }
        if (edtNameReceiver.getText().toString().equals("")) {
            edtNameReceiver.setError("Please enter your name");
            edtNameReceiver.requestFocus();
            return;
        }
        if (edtPhoneReceiver.getText().toString().equals("")) {
            edtPhoneReceiver.setError("Please enter your phone number");
            edtPhoneReceiver.requestFocus();
            return;
        }

        Address newAddress = new Address();
        newAddress.setLocation(edtLocation.getText().toString());
        newAddress.setNameReceiver(edtNameReceiver.getText().toString());
        newAddress.setPhoneReceiver(edtPhoneReceiver.getText().toString());
        newAddress.setDefaults(switchCompat.isChecked());

        RetrofitService retrofitService = new RetrofitService();
        AddressApi addressApi = retrofitService.getRetrofit().create(AddressApi.class);
        addressApi.create(MainActivity.user.getId(), newAddress)
                .enqueue(new Callback<Address>() {
                    @Override
                    public void onResponse(Call<Address> call, Response<Address> response) {
                        if(response.isSuccessful()) {
                            Toast.makeText(AddAddressActivity.this, "Create success", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(AddAddressActivity.this,"Create error",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Address> call, Throwable t) {
                        Toast.makeText(AddAddressActivity.this,"Call Api Error",Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void setControl() {

        switchCompat = findViewById(R.id.swCompat);
        edtLocation = findViewById(R.id.edtLocation);
        edtNameReceiver = findViewById(R.id.edtNameReceiver);
        edtPhoneReceiver = findViewById(R.id.edtPhoneReveiver);
        btnCreate = findViewById(R.id.btnCreate);
        iconBack=findViewById(R.id.iconBack);
    }
}