package com.huuduc.giuaky.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.LoginActivity;
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.data.DTO.AuthenDTORequest;
import com.huuduc.giuaky.data.DTO.AuthenDTOResponse;
import com.huuduc.giuaky.retrofit.AuthenApi;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.sharedreferent.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity {

    private Button btnLogin;
    private EditText edtEmail,edtPassword;
    private TextView txtUser;

    private Intent intent;

    private TokenManager tokenManager;

    public static String token ="Bearer ";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        tokenManager = new TokenManager(this);
        setControl();
        setEvent();
    }

    private void setEvent() {

        txtUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
    }

    private void login() {

        if(edtEmail.getText().toString().equals("")){
            edtEmail.setError("Please enter your email");
            edtEmail.requestFocus();
            return;
        }
        if(edtPassword.getText().toString().equals("")){
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }

        AuthenDTORequest authenDTORequest = new AuthenDTORequest(
                edtEmail.getText().toString(),
                edtPassword.getText().toString());
        RetrofitService retrofitService = new RetrofitService();
        AuthenApi authenApi = retrofitService.getRetrofit().create(AuthenApi.class);
        authenApi.login(authenDTORequest)
                .enqueue(new Callback<AuthenDTOResponse>() {
                    @Override
                    public void onResponse(Call<AuthenDTOResponse> call, Response<AuthenDTOResponse> response) {
                        if (response.isSuccessful()) {
                            AuthenDTOResponse authenDTOResponse = response.body();

                            if(authenDTOResponse.getRoleId().equals(1L)) {

                                 token += authenDTOResponse.getToken();
//                                tokenManager.saveToken(token);
//                                tokenManager.saveUserId(authenDTOResponse.getUserId());
                                intent = new Intent(AdminLoginActivity.this, DashboardActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(AdminLoginActivity.this, "Sai thong tin", Toast.LENGTH_SHORT).show();
                            }

                        } else {
                            Toast.makeText(AdminLoginActivity.this, "Sai thong tin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenDTOResponse> call, Throwable t) {
                        Toast.makeText(AdminLoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void setControl() {
        txtUser=findViewById(R.id.txtUser);
        edtEmail=findViewById(R.id.edtEmail);
        edtPassword=findViewById(R.id.edtPassword);
        btnLogin=findViewById(R.id.btnLogin);
    }
}