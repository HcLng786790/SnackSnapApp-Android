package com.huuduc.giuaky.activity.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.User.User;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserDetailsActivity extends AppCompatActivity {

    private TextView txtName,txtPhone,txtEmail;
    private CircleImageView circleImageView;
    private ImageView iconBack;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_details);

        txtEmail=findViewById(R.id.txtEmail);
        txtName=findViewById(R.id.txtName);
        txtPhone=findViewById(R.id.txtPhone);
        iconBack=findViewById(R.id.iconBack);
        circleImageView=findViewById(R.id.cimvAvatar);

        getBundle();

        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getBundle() {
        Bundle bundle = getIntent().getExtras();
        if(bundle==null){
            return;
        }

        User user = (User) bundle.get("obj_user");

        txtName.setText(user.getName());
        txtPhone.setText(user.getPhoneNumber());
        txtEmail.setText(user.getEmail());

        Glide.with(this)
                .load(user.getImg())
                .into(circleImageView);

    }
}