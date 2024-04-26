package com.huuduc.giuaky;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.huuduc.giuaky.data.ThongKe;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private PieChart pieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        actionToolBar();
        getDataChart();
    }

    private void getDataChart() {
        List<PieEntry> list = new ArrayList<>();
        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        productApi.thongKe()
                .enqueue(new Callback<List<ThongKe>>() {
                    @Override
                    public void onResponse(Call<List<ThongKe>> call, Response<List<ThongKe>> response) {
                        if (response.isSuccessful()) {
                            List<ThongKe> thongKeList = response.body();
                            for (ThongKe thongKe: thongKeList){
                                String name = thongKe.getProductName();
                                long quantity = thongKe.getSum();
                                list.add(new PieEntry(quantity,name));
                            }
                            PieDataSet pieDataSet =new PieDataSet(list,"Thống kê sản phẩm đã bán");
                            pieDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            PieData data = new PieData(pieDataSet);
                            data.setValueTextSize(30f);
                            data.setValueFormatter(new PercentFormatter(pieChart));

                            pieChart.setData(data);
                            pieChart.animateXY(2000,2000);
                            pieChart.setUsePercentValues(true);
                            pieChart.getDescription().setEnabled(false);
                            pieChart.invalidate();


                        }else{
                            Toast.makeText(ThongKeActivity.this,"Thong Ke lỗi",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<ThongKe>> call, Throwable t) {
                        Toast.makeText(ThongKeActivity.this,"API error",Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void actionToolBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void setControl() {
        toolbar = findViewById(R.id.toolBar);
        pieChart = findViewById(R.id.pieChart);
    }
}