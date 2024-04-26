package com.huuduc.giuaky.activity.fragmentMain;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.huuduc.giuaky.activity.OtherActivity;
import com.huuduc.giuaky.activity.CartAvtivity;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.fragmentCategory.DrinkFragment;
import com.huuduc.giuaky.activity.fragmentCategory.SnackFragment;
import com.huuduc.giuaky.activity.fragmentCategory.FoodFragment;
import com.huuduc.giuaky.adapter.HomeViewPagerAdapter;
import com.huuduc.giuaky.activity.fragmentCategory.SauceFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private List<Fragment> fragmentList = new ArrayList<>();
    private HomeViewPagerAdapter homeViewPagerAdapter;

    private ImageView iconCart,iconLeft;

    private View mView;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        mView =  inflater.inflate(R.layout.fragment_home, container, false);


        //Ánh xạ
        tabLayout = mView.findViewById(R.id.category_layout);
        viewPager2=mView.findViewById(R.id.home_viewpage);
        iconCart=mView.findViewById(R.id.iconCart);
        iconLeft=mView.findViewById(R.id.iconLeft);

        fragmentList.add(new FoodFragment());
        fragmentList.add(new DrinkFragment());
        fragmentList.add(new SnackFragment());
        fragmentList.add(new SauceFragment());

        homeViewPagerAdapter = new HomeViewPagerAdapter(this,fragmentList);
        viewPager2.setAdapter(homeViewPagerAdapter);


        new TabLayoutMediator(tabLayout, viewPager2, (tab, position) -> {
            switch (position){
                case 0:
                    tab.setText("Food");
                    break;
                case 1:
                    tab.setText("Drink");
                    break;
                case 2:
                    tab.setText("Snack");
                    break;
                case 3:
                    tab.setText("Sauce");
                    break;
            }
        }).attach();

        iconCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CartAvtivity.class);
                startActivity(intent);
            }
        });

        iconLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OtherActivity.class);
                startActivity(intent);
            }
        });

        return mView;
    }
}