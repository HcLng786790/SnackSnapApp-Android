package com.huuduc.giuaky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.huuduc.giuaky.adapter.PromotionAdapter;
import com.huuduc.giuaky.adapter.PromotionClientAdapter;
import com.huuduc.giuaky.data.Promotion;
import com.huuduc.giuaky.data.address.Address;
import com.huuduc.giuaky.retrofit.PromotionApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowPromotionActivity extends AppCompatActivity {

    private ListView lvPromotion;
    private ImageView iconBack;

    private List<Promotion> promotionList = new ArrayList<>();

    private Promotion selectPromotion;

    private PromotionClientAdapter promotionClientAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_promotion);
        setControl();
        loadData();
        setEvent();
    }

    private void loadData() {
        RetrofitService retrofitService = new RetrofitService();
        PromotionApi promotionApi = retrofitService.getRetrofit().create(PromotionApi.class);
        promotionApi.getActive()
                .enqueue(new Callback<List<Promotion>>() {
                    @Override
                    public void onResponse(Call<List<Promotion>> call, Response<List<Promotion>> response) {
                        promotionList=response.body();
                        poppulateView(promotionList);
                    }

                    @Override
                    public void onFailure(Call<List<Promotion>> call, Throwable t) {

                    }
                });
    }

    private void poppulateView(List<Promotion> promotionList) {
        promotionClientAdapter = new PromotionClientAdapter(this,R.layout.layout_promotion_chose,promotionList);
        promotionClientAdapter.setSelectedPromotion(selectPromotion);
        lvPromotion.setAdapter(promotionClientAdapter);
    }


    private void setEvent() {
        iconBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            return;
        }

        selectPromotion = (Promotion) bundle.get("promotion");

//        lvAddress.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                addressAdapter.setSelectedPosition(position);
//                addressAdapter.setSelectedAddress(addresses.get(position));
//                addressAdapter.notifyDataSetChanged();
//
//                Intent intent = new Intent();
//                Bundle bundle = new Bundle();
//                bundle.putSerializable("chose", addresses.get(position));
//                intent.putExtras(bundle);
//                setResult(Activity.RESULT_OK, intent);
//                finish();
//            }
//        });

        lvPromotion.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                promotionClientAdapter.setSelectedPromotion(promotionList.get(position));
                promotionClientAdapter.notifyDataSetChanged();

                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putSerializable("chose", promotionList.get(position));
                intent.putExtras(bundle);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    private void setControl() {
        iconBack=findViewById(R.id.iconBack);
        lvPromotion=findViewById(R.id.lvPromotion);
    }
}