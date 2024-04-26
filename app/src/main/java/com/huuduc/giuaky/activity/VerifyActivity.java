package com.huuduc.giuaky.activity;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyActivity extends AppCompatActivity {

    private EditText edtOTP;
    private User user;
    private Button btnVerify;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify);
        setControl();
        getBundle();
        setEvent();
    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            return;
        }

        user = (User) bundle.get("obj_user");
    }

    private void setEvent() {

        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verify();
            }
        });
    }

    private void verify() {

        if (edtOTP.getText().toString().equals("")) {
            edtOTP.setError("Please enter otp");
            edtOTP.requestFocus();
            return;
        }

        String otp = edtOTP.getText().toString();
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.verify(user.getEmail(), otp)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(VerifyActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(VerifyActivity.this, "Wrong", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("e", t.toString());
                        Toast.makeText(VerifyActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setControl() {
        edtOTP = findViewById(R.id.edtOTP);
        btnVerify = findViewById(R.id.btnVerify);
    }
}