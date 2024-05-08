package com.huuduc.giuaky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.huuduc.giuaky.activity.fragmentStatus.CancelFragment;
import com.huuduc.giuaky.activity.fragmentStatus.ConfirmFragment;
import com.huuduc.giuaky.activity.fragmentStatus.CookFragment;
import com.huuduc.giuaky.activity.fragmentStatus.DeliveredFragment;
import com.huuduc.giuaky.activity.fragmentStatus.DeliveringFragment;
import com.huuduc.giuaky.adapter.OrderViewPagerAdapter;
import com.huuduc.giuaky.adapter.PromotionViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PromotionActivity extends AppCompatActivity {

    private ImageView iconBack;
    private TabLayout tabLayout;

    private ViewPager2 viewPager2;

    private PromotionViewPagerAdapter promotionViewPagerAdapter;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);
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
    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        tabLayout=findViewById(R.id.tab_promotion);
        viewPager2=findViewById(R.id.promotion_viewpager);

        fragmentList.add(new ActivePromotionFragemnt());
        fragmentList.add(new HidelPromotionFragment());

        promotionViewPagerAdapter = new PromotionViewPagerAdapter(this,fragmentList);
        viewPager2.setAdapter(promotionViewPagerAdapter);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Active");
                    break;
                case 1:
                    tab.setText("Hide");
                    break;
            }
        }).attach();

    }
}