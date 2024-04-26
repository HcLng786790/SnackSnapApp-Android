package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.admin.AdminLoginActivity;
import com.huuduc.giuaky.sharedreferent.TokenManager;
import com.huuduc.giuaky.data.DTO.AuthenDTORequest;
import com.huuduc.giuaky.data.DTO.AuthenDTOResponse;
import com.huuduc.giuaky.retrofit.AuthenApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private TokenManager tokenManager;

    private Intent intent;

    private TextView txtSignUp,txtAdminPage,txtActive;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setControl();


        tokenManager = new TokenManager(this);
//        tokenManager.clearToken();
//        tokenManager.clearUserId();
        checkLogin();
        setEvent();
    }

    private void checkLogin() {

        String token = tokenManager.getToken();
        if (token != null && !token.isEmpty()) {
            intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private void setEvent() {

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });

        txtAdminPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(LoginActivity.this, AdminLoginActivity.class);
                startActivity(intent);
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

                            if(!authenDTOResponse.isActive()){
                                edtEmail.setText("");
                                edtEmail.setError("Please active");
                                edtPassword.setText("");
                                edtEmail.requestFocus();
                                txtActive.setVisibility(View.VISIBLE);
                                txtActive.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent1 = new Intent(LoginActivity.this, VerifyActivity.class);
                                        startActivity(intent1);
                                    }
                                });
                            }else{
                                if(authenDTOResponse.getRoleId().equals(2L)) {

                                    String token = authenDTOResponse.getToken();
                                    tokenManager.saveToken(token);
                                    tokenManager.saveUserId(authenDTOResponse.getUserId());
                                    intent = new Intent(LoginActivity.this, MainActivity.class);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(LoginActivity.this, "Sai thong tin", Toast.LENGTH_SHORT).show();
                                }
                            }

                        } else {
                            Toast.makeText(LoginActivity.this, "Sai thong tin", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<AuthenDTOResponse> call, Throwable t) {
                        Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void setControl() {
        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtSignUp = findViewById(R.id.txtSignUp);
        txtAdminPage=findViewById(R.id.txtAdminPage);
        txtActive=findViewById(R.id.txtActive);

    }
}