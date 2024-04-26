package com.huuduc.giuaky.activity.fragmentMain;

//import static com.huuduc.giuaky.activity.MainActivity.user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.activity.OrderActivity;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.activity.ProfileActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    private TextView txtNameUser, txtEmail, txtPhone;
    private Button btnEdit;

    private ImageView iconOrders;
    private View mView;

    private CircleImageView cimvAvatar;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_account, container, false);

        iconOrders=mView.findViewById(R.id.iconOrders);
        txtEmail = mView.findViewById(R.id.txtEmail);
        txtNameUser = mView.findViewById(R.id.txtNameUser);
        txtPhone = mView.findViewById(R.id.txtPhone);
        btnEdit = mView.findViewById(R.id.btnEdit);
        cimvAvatar = mView.findViewById(R.id.cimvAvatar);



        txtEmail.setText(MainActivity.user.getEmail());
        txtNameUser.setText(MainActivity.user.getName());
        txtPhone.setText(MainActivity.user.getPhoneNumber());

        if(MainActivity.user.getImg()==null||MainActivity.user.getImg().isEmpty()){
            cimvAvatar.setImageResource(R.drawable.avatar);
        }else {
            Glide.with(getContext())
                    .load(MainActivity.user.getImg())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(cimvAvatar);
        }

        iconOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OrderActivity.class);
                startActivity(intent);
            }
        });


        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), ProfileActivity.class);
                startActivity(intent);
            }

        });


        return mView;
    }


    @Override
    public void onResume() {
        super.onResume();

        txtEmail.setText(MainActivity.user.getEmail());
        txtNameUser.setText(MainActivity.user.getName());
        txtPhone.setText(MainActivity.user.getPhoneNumber());

        if(MainActivity.user.getImg()==null||MainActivity.user.getImg().isEmpty()){
            cimvAvatar.setImageResource(R.drawable.avatar);
        }else {
            Glide.with(getContext())
                    .load(MainActivity.user.getImg())
                    .placeholder(R.drawable.ic_launcher_foreground)
                    .error(R.drawable.ic_launcher_background)
                    .into(cimvAvatar);
        }
    }
}