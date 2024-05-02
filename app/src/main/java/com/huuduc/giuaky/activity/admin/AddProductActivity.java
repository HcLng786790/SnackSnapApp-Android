package com.huuduc.giuaky.activity.admin;


import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

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
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.ProfileActivity;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.huuduc.giuaky.widget.RealPathUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddProductActivity extends AppCompatActivity {

    public static final String TAG = AddProductActivity.class.getName();
    private static final int MY_REQUEST_CODE = 10;

    private Spinner spType;
    private List<String> type = new ArrayList<>();

    private ArrayAdapter adapterType;

    public Product thisProduct;

    private EditText edtNameProduct, edtPriceProduct;
    private ImageView imvProduct, iconBack, iconLoadImg;

    private SwitchCompat swSeller;

    private Uri uri;

    private Button btnAddProduct, btnUpdateProduct;
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
                        imvProduct.setImageURI(uri);
                    }
                }
            }
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);
        setControl();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait ...");
        setEvent();
        getBundle();
    }

    private void setEvent() {
        KhoiTao();
        adapterType = new ArrayAdapter(this, android.R.layout.simple_list_item_1, type);
        spType.setAdapter(adapterType);

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        iconLoadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickRequestPermisson();
            }
        });

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createProduct();
            }
        });

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProduct();
            }
        });
    }

    private void updateProduct() {
        progressDialog.show();
        if (edtNameProduct.getText().toString().equals("")) {
            edtNameProduct.setError("Please enter name product");
            edtNameProduct.requestFocus();
            return;
        }
        if (edtPriceProduct.getText().toString().equals("")) {
            edtPriceProduct.setError("Please enter price");
            edtPriceProduct.requestFocus();
            return;
        }

        Product newProduct = new Product();
        newProduct.setName(edtNameProduct.getText().toString());
        newProduct.setPrice(Double.parseDouble(edtPriceProduct.getText().toString()));
        newProduct.setType(spType.getSelectedItemPosition() + 1);
        newProduct.setFavorite(swSeller.isChecked());

        // Chuyển đổi đối tượng thành chuỗi JSON
        String jsonData = new Gson().toJson(newProduct);

        // Tạo RequestBody từ chuỗi JSON
        RequestBody requestBodyData = RequestBody.create(MediaType.parse("application/json"), jsonData);

        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);

        if (uri != null) {
            String realPath = RealPathUtil.getRealPath(AddProductActivity.this, uri);
            File file = new File(realPath);

            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mPartImg = MultipartBody.Part.createFormData("fileImage", file.getName(), requestBodyImg);

            productApi.updateById(thisProduct.getId(),requestBodyData, mPartImg)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(AddProductActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        } else {
            productApi.updateById(thisProduct.getId(),requestBodyData)
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                progressDialog.dismiss();
                                Toast.makeText(AddProductActivity.this, "Update success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }




    private void createProduct() {


        if (edtNameProduct.getText().toString().equals("")) {
            edtNameProduct.setError("Please enter name product");
            edtNameProduct.requestFocus();
            return;
        }
        if (edtPriceProduct.getText().toString().equals("")) {
            edtPriceProduct.setError("Please enter price");
            edtPriceProduct.requestFocus();
            return;
        }
        progressDialog.show();

        Product newProduct = new Product();
        newProduct.setName(edtNameProduct.getText().toString());
        newProduct.setPrice(Double.parseDouble(edtPriceProduct.getText().toString()));
        newProduct.setType(spType.getSelectedItemPosition() + 1);
        newProduct.setFavorite(swSeller.isChecked());

        // Chuyển đổi đối tượng thành chuỗi JSON
        String jsonData = new Gson().toJson(newProduct);

        // Tạo RequestBody từ chuỗi JSON
        RequestBody requestBodyData = RequestBody.create(MediaType.parse("application/json"), jsonData);

        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);

        if (uri != null) {
            String realPath = RealPathUtil.getRealPath(AddProductActivity.this, uri);
            File file = new File(realPath);

            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part mPartImg = MultipartBody.Part.createFormData("fileImages", file.getName(), requestBodyImg);

            productApi.create2(requestBodyData, mPartImg)
                    .enqueue(new Callback<Product>() {
                        @Override
                        public void onResponse(Call<Product> call, Response<Product> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                Toast.makeText(AddProductActivity.this, "Create success", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Product> call, Throwable t) {
                            Toast.makeText(AddProductActivity.this, "Error", Toast.LENGTH_SHORT).show();
                        }
                    });
        }else{
            Toast.makeText(AddProductActivity.this, "Please chose image", Toast.LENGTH_SHORT).show();
        }

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

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if (bundle == null) {
            btnAddProduct.setVisibility(View.VISIBLE);
            return;
        } else {
            btnUpdateProduct.setVisibility(View.VISIBLE);
        }
        Product product = (Product) bundle.get("obj_product");

        thisProduct = product;

        edtNameProduct.setText(product.getName());
        edtPriceProduct.setText(product.getPrice() + "");

        Glide.with(this)
                .load(product.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imvProduct);
        if (product.getType().equals("Food")) {
            spType.setSelection(0);
        }
        if (product.getType().equals("Drink")) {
            spType.setSelection(1);
        }
        if (product.getType().equals("Snack")) {
            spType.setSelection(2);
        }
        if (product.getType().equals("Sauce")) {
            spType.setSelection(3);
        }
        boolean isFavo = product.isFavorite();

        swSeller.setChecked(isFavo);
//        swSeller.setChecked(true);
    }


    private void setControl() {
        spType = findViewById(R.id.spType);
        edtNameProduct = findViewById(R.id.edtNameProduct);
        edtPriceProduct = findViewById(R.id.edtPriceProduct);
        imvProduct = findViewById(R.id.imvProduct);
        swSeller = findViewById(R.id.swSeller);
        iconBack = findViewById(R.id.iconBack);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        iconLoadImg = findViewById(R.id.iconLoadImg);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);

    }

    private void KhoiTao() {
        type.add("Food");
        type.add("Drink");
        type.add("Snack");
        type.add("Sauce");
    }

}