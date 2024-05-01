package com.huuduc.giuaky.activity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.widget.RealPathUtil;
import com.huuduc.giuaky.data.DTO.RegisterDTORequest;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.retrofit.UserApi;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileActivity extends AppCompatActivity {

    public static final String TAG = ProfileActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;
    private EditText edtName, edtEmail, edtPhone;
    private Button btnUpdate;
    private ImageView iconBack;

    private Uri uri;

    private CircleImageView cimvAvatar;

    private ProgressDialog progressDialog;


    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult o) {
                    Log.e(TAG, "onActivityResult");
                    if (o.getResultCode() == Activity.RESULT_OK) {
                        Intent data = o.getData();
                        if (data == null) {
                            return;
                        }
                        uri = data.getData();
                        cimvAvatar.setImageURI(uri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        setControl();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setEvent();
    }

    private void setEvent() {

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); //
            }
        });

        cimvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermisson();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callApiUpdateProfile();
            }
        });

    }

    private void onClickRequestPermisson() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openGallery();
        } else {
            String[] permisson = {Manifest.permission.READ_EXTERNAL_STORAGE};
            requestPermissions(permisson, MY_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Select Picture"));
    }


    private void callApiUpdateProfile() {
        progressDialog.show();

        RegisterDTORequest registerDTORequest = new RegisterDTORequest();
        registerDTORequest.setEmail(edtEmail.getText().toString());
        registerDTORequest.setName(edtName.getText().toString());
        registerDTORequest.setPhoneNumber(edtPhone.getText().toString());

        // Chuyển đổi đối tượng thành chuỗi JSON
        String jsonData = new Gson().toJson(registerDTORequest);

        // Tạo RequestBody từ chuỗi JSON
        RequestBody requestBodyData = RequestBody.create(MediaType.parse("application/json"), jsonData);

        RetrofitService retrofitService = new RetrofitService();
        UserApi userApi = retrofitService.getRetrofit().create(UserApi.class);

        if (uri != null) {
            String realPath = RealPathUtil.getRealPath(ProfileActivity.this, uri);

            File file = new File(realPath);

            RequestBody requestBodyAvatar = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mPartAvatar = MultipartBody.Part.createFormData("fileAvatar", file.getName(), requestBodyAvatar);

            userApi.updateById(MainActivity.user.getId(), requestBodyData, mPartAvatar)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            progressDialog.dismiss();
                            if (response != null) {
                                MainActivity.user = response.body();
                                Toast.makeText(ProfileActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Update error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Call error", Toast.LENGTH_SHORT).show();
                            Log.e("loi", "loi", t);
                        }
                    });
        } else {
            userApi.updateById(MainActivity.user.getId(), requestBodyData)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            progressDialog.dismiss();
                            if (response != null) {
                                MainActivity.user = response.body();
                                Toast.makeText(ProfileActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(ProfileActivity.this, "Update error", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(ProfileActivity.this, "Call error", Toast.LENGTH_SHORT).show();
                            Log.e("loi", "loi", t);
                        }
                    });
        }


    }

    private void setControl() {
        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        btnUpdate = findViewById(R.id.btnUpdate);
        iconBack = findViewById(R.id.iconBack);
        cimvAvatar = findViewById(R.id.cimvAvatar);

        edtName.setText(MainActivity.user.getName());
        edtEmail.setText(MainActivity.user.getEmail());
        edtPhone.setText(MainActivity.user.getPhoneNumber());
        if(MainActivity.user.getImg()==null||MainActivity.user.getImg().isEmpty()){
            cimvAvatar.setImageResource(R.drawable.avatar);
        }else {
            Glide.with(this)
                    .load(MainActivity.user.getImg())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(cimvAvatar);
        }
    }
}