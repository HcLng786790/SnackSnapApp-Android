package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.DTO.RegisterDTORequest;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.retrofit.UserApi;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity {

    private EditText edtEmail, edtPassword, edtRepeat,edtPhoneNumber,edtName;
    private Button btnRegister;
    private TextView txtLogin;

    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setControl();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setEvent();
    }

    private void setEvent() {

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });

        txtLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void register() {

        // Kiểm tra xem các trường có trống không
        if (edtEmail.getText().toString().equals("")) {
            edtEmail.setError("Please enter your email");
            edtEmail.requestFocus();
            return;
        }
        if (edtName.getText().toString().equals("")) {
            edtName.setError("Please enter your name");
            edtName.requestFocus();
            return;
        }
        if (edtPhoneNumber.getText().toString().equals("")) {
            edtPhoneNumber.setError("Please enter your phone number");
            edtPhoneNumber.requestFocus();
            return;
        }

        if (edtPassword.getText().toString().equals("")) {
            edtPassword.setError("Please enter your password");
            edtPassword.requestFocus();
            return;
        }
        if (edtRepeat.getText().toString().equals("")) {
            edtRepeat.setError("Please enter your password");
            edtRepeat.requestFocus();
            return;
        }

        if(!edtPassword.getText().toString().equals(edtRepeat.getText().toString())){
            Toast.makeText(RegisterActivity.this,"MK không khớp",Toast.LENGTH_SHORT).show();
            edtPassword.setText("");
            edtRepeat.setText("");
            edtPassword.requestFocus();

            return; // Dừng xử lý tiếp theo
        }

        progressDialog.show();
        RegisterDTORequest registerDTORequest = new RegisterDTORequest(
                edtEmail.getText().toString(),
                edtPassword.getText().toString(),
                edtPhoneNumber.getText().toString(),
                edtName.getText().toString());

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.register(registerDTORequest)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        progressDialog.dismiss();
                        if(response.isSuccessful()) {
                            Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, VerifyActivity.class);
                            User user = response.body();
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("obj_user", user);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }else{
                            Toast.makeText(RegisterActivity.this,"Email đã tồn tại",Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Log.e("error",t.toString());
                        Toast.makeText(RegisterActivity.this,"Đăng ký thất bại",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void setControl() {

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepeat = findViewById(R.id.edtRepeat);
        btnRegister=findViewById(R.id.btnRegister);
        edtPhoneNumber=findViewById(R.id.edtPhone);
        edtName=findViewById(R.id.edtName);
        txtLogin=findViewById(R.id.txtLogin);
    }
}