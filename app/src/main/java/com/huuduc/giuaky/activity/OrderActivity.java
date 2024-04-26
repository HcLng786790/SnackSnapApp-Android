package com.huuduc.giuaky.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.fragmentStatus.CancelFragment;
import com.huuduc.giuaky.activity.fragmentStatus.ConfirmFragment;
import com.huuduc.giuaky.activity.fragmentStatus.CookFragment;
import com.huuduc.giuaky.activity.fragmentStatus.DeliveredFragment;
import com.huuduc.giuaky.activity.fragmentStatus.DeliveringFragment;
import com.huuduc.giuaky.adapter.OrderViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class OrderActivity extends AppCompatActivity {

    private ImageView iconBack;
    private TabLayout tabLayout;

    private ViewPager2 viewPager2;

    private OrderViewPagerAdapter orderViewPagerAdapter;

    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        setControl();
        setEvent();
    }

    private void setEvent(){
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void setControl(){
        iconBack=findViewById(R.id.iconBack);
        tabLayout=findViewById(R.id.status_orders_layout);
        viewPager2=findViewById(R.id.order_viewpage);

        fragmentList.add(new ConfirmFragment());
        fragmentList.add(new CookFragment());
        fragmentList.add(new DeliveringFragment());
        fragmentList.add(new DeliveredFragment());
        fragmentList.add(new CancelFragment());


        orderViewPagerAdapter = new OrderViewPagerAdapter(this,fragmentList);
        viewPager2.setAdapter(orderViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Chờ xác nhận");
                    break;
                case 1:
                    tab.setText("Chờ thực hiện");
                    break;
                case 2:
                    tab.setText("Chờ giao hành");
                    break;
                case 3:
                    tab.setText("Thành công");
                    break;
                case 4:
                    tab.setText("Đã hủy");
                    break;
            }
        }).attach();
    }
}