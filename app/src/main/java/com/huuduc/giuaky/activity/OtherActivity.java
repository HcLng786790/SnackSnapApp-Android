package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.sharedreferent.TokenManager;

public class OtherActivity extends AppCompatActivity {

    private ImageView iconBack;
    private TextView txtProfile, txtCart, txtChange, txtSignOut;
    private TokenManager tokenManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other);
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

        txtProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, ProfileActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, CartAvtivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tokenManager.clearToken();
                tokenManager.clearUserId();

                Intent intent = new Intent(OtherActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        txtChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherActivity.this, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });


    }

    private void setControl() {
        iconBack = findViewById(R.id.iconBack);
        txtProfile = findViewById(R.id.txtProfile);
        txtCart = findViewById(R.id.txtCart);
        txtChange = findViewById(R.id.txtChange);
        txtSignOut = findViewById(R.id.txtSignOut);
        tokenManager = new TokenManager(this);
    }
}