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
import com.huuduc.giuaky.data.DTO.ChangePassDTO;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.retrofit.UserApi;
import com.huuduc.giuaky.sharedreferent.TokenManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    private EditText edtNewPassword, edtOldPassword, edtConfirmNew;
    private Button btnChange;
    private TextView txtBack;

    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setControl();
        setEvent();
    }

    private void setEvent() {
        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changePassword();
            }
        });
    }

    private void changePassword() {

        if (edtOldPassword.getText().toString().equals("")) {
            edtOldPassword.setError("Please enter old password");
            edtOldPassword.requestFocus();
            return;
        }

        if (edtNewPassword.getText().toString().equals("")) {
            edtNewPassword.setError("Please enter new password");
            edtNewPassword.requestFocus();
            return;
        }

        if (edtConfirmNew.getText().toString().equals("")) {
            edtConfirmNew.setError("Please enter confirm password");
            edtConfirmNew.requestFocus();
            return;
        }

        if (!edtNewPassword.getText().toString().equals(edtConfirmNew.getText().toString())) {
            edtNewPassword.setError("Wrong confirm");
            edtNewPassword.setText("");
            edtConfirmNew.setText("");
            edtNewPassword.requestFocus();
            return;
        }

        String newPassword = edtNewPassword.getText().toString();
        String oldPassword = edtOldPassword.getText().toString();

        ChangePassDTO changePassDTO = new ChangePassDTO(newPassword, oldPassword);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        String token="Bearer "+tokenManager.getToken();

        userApi.changePassByToken(token, changePassDTO)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(ChangePasswordActivity.this, "Update success", Toast.LENGTH_SHORT).show();

                            tokenManager.clearUserId();
                            tokenManager.clearToken();
                            Intent intent = new Intent(ChangePasswordActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();

                        }else{
                            Toast.makeText(ChangePasswordActivity.this, "Incorrec old password", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setControl() {
        edtConfirmNew = findViewById(R.id.edtConfirmNew);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtOldPassword = findViewById(R.id.edtOldPassword);
        txtBack = findViewById(R.id.txtBack);
        btnChange = findViewById(R.id.btnChange);
        tokenManager = new TokenManager(this);
    }
}