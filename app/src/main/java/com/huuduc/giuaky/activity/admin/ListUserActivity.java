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
import com.huuduc.giuaky.adapter.UserAdapter;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.interfaces.UserClickListener;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.retrofit.UserApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListUserActivity extends AppCompatActivity {

    private ImageView iconBack;
    private RecyclerView rcvUser;
    private UserAdapter userAdapter;

    private List<User> users;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        setControl();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvUser.setLayoutManager(linearLayoutManager);

        loadUsers();
        setEvent();
    }

    private void loadUsers() {
        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);
        userApi.getAll(AdminLoginActivity.token)
                .enqueue(new Callback<List<User>>() {
                    @Override
                    public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                        if(response.isSuccessful()){
                            populatedListView(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<User>> call, Throwable t) {
                        Toast.makeText(ListUserActivity.this, "Save error", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void populatedListView(List<User> body) {
        userAdapter = new UserAdapter(ListUserActivity.this, body, new UserClickListener() {
            @Override
            public void onClickOrders(User user) {
                onClickToDetail(user);
            }
        });

        rcvUser.setAdapter(userAdapter);
    }

    private void onClickToDetail(User user) {
        Intent intent = new Intent(this, UserDetailsActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("obj_user", user);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private void setEvent() {
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        rcvUser=findViewById(R.id.rcv_user);

    }
}