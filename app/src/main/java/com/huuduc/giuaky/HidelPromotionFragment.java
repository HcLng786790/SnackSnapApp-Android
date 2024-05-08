package com.huuduc.giuaky;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.huuduc.giuaky.activity.admin.AdminLoginActivity;
import com.huuduc.giuaky.adapter.PromotionAdapter;
import com.huuduc.giuaky.data.Promotion;
import com.huuduc.giuaky.retrofit.PromotionApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HidelPromotionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HidelPromotionFragment extends Fragment {

    private RecyclerView recyclerView;
    private View mView;

    private PromotionAdapter promotionAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public HidelPromotionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HidelPromotionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HidelPromotionFragment newInstance(String param1, String param2) {
        HidelPromotionFragment fragment = new HidelPromotionFragment();
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
        mView=  inflater.inflate(R.layout.fragment_hidel_promotion, container, false);

        recyclerView = mView.findViewById(R.id.rcv_promotion_hide);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        loadPromotion(AdminLoginActivity.token);

        return mView;
    }

    private void loadPromotion(String token) {
        RetrofitService retrofitService = new RetrofitService();
        PromotionApi promotionApi = retrofitService.getRetrofit().create(PromotionApi.class);
        promotionApi.getHide(token)
                .enqueue(new Callback<List<Promotion>>() {
                    @Override
                    public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
                        populateView(response.body());
                    }

                    @Override
                    public void onFailure(Call<List<Promotion>> call, Throwable t) {
                        Toast.makeText(getContext(), "Authorization", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void populateView(List<Promotion> body) {
        promotionAdapter = new PromotionAdapter(getContext(), body);
        recyclerView.setAdapter(promotionAdapter);
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        loadPromotion(AdminLoginActivity.token);
//    }
}