package com.huuduc.giuaky.activity.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.DoanhThu;
import com.huuduc.giuaky.data.ThongKe;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongKeActivity extends AppCompatActivity {

    final static int REQUEST_CDOE=1232;

    private Toolbar toolbar;
    private PieChart pieChart;
    private TextView txtRevenue;
    private BarChart barChart;

    private Button btnPdf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_ke);
        setControl();
        actionToolBar();
        getRevenue();
        getDataChart();
        getBarChar();
        setEvent();
    }

    private void getBarChar() {
        barChart.getDescription().setEnabled(false);
        barChart.setDrawValueAboveBar(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setAxisMinimum(1);
        xAxis.setAxisMaximum(12);
        YAxis yAxisRight = barChart.getAxisRight();
        yAxisRight.setAxisMinimum(0);
        YAxis yAxisLeft = barChart.getAxisLeft();
        yAxisLeft.setAxisMinimum(0);

    }

    private void setEvent() {
        btnPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createPDF();
                } catch (BadElementException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_thongke,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.menuMonth){
            getTkThang();
        }else if (id==R.id.menuProduct){
            getDataChart();
        }
        else{
            return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    private void getTkThang() {
        barChart.setVisibility(View.VISIBLE);
        pieChart.setVisibility(View.GONE);
        btnPdf.setVisibility(View.GONE);

        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getDoanhThu()
                .enqueue(new Callback<List<DoanhThu>>() {
                    @Override
                    public void onResponse(Call<List<DoanhThu>> call, Response<List<DoanhThu>> response) {
                        if(response.isSuccessful()){
                            List<BarEntry> list = new ArrayList<>();
                            for (int i=0;i<response.body().size();i++){
                                double doanhThu = response.body().get(i).getDoanhthu();
                                int thang = response.body().get(i).getThang();
                                list.add(new BarEntry(thang, (float) doanhThu));
                            }
                            BarDataSet barDataSet = new BarDataSet(list,"Thống kê");
                            barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
                            barDataSet.setValueTextSize(14f);
                            barDataSet.setValueTextColor(Color.RED);

                            BarData barData = new BarData(barDataSet);
                            barChart.animateXY(2000,2000);
                            barChart.setData(barData);
                            barChart.invalidate();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DoanhThu>> call, Throwable t) {
                        Toast.makeText(ThongKeActivity.this,"Thong Ke lỗi",Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void createPDF() throws BadElementException, IOException {
        PdfDocument pdfDocument = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(1080,1920,1).create();
        PdfDocument.Page page = pdfDocument.startPage(pageInfo);

        Canvas canvas = page.getCanvas();
        Paint paint = new Paint();
        paint.setColor(Color.RED);
        paint.setTextSize(42);

        float x=500;
        float y =900;

        // Chuyển đổi PieChart thành hình ảnh
        Bitmap bitmap = pieChart.getChartBitmap();
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
//        Image chartImage = Image.getInstance(stream.toByteArray());

        canvas.drawBitmap(bitmap, 0, 0, paint);
        pdfDocument.finishPage(page);

        File downloadDir= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        String fileName="thongke.pdf";
        File file = new File(downloadDir,fileName);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            pdfDocument.writeTo(fos);
            pdfDocument.close();
            fos.close();
            Toast.makeText(this,"Export success",Toast.LENGTH_SHORT).show();
        }catch (FileNotFoundException e){
            throw new RuntimeException(e);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }



    private void getRevenue() {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi =retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.getRevenue(AdminLoginActivity.token)
                .enqueue(new Callback<Long>() {
                    @Override
                    public void onResponse(Call<Long> call, Response<Long> response) {
                        if (response.isSuccessful()){
                            txtRevenue.setText(response.body()+" Đ");
                        }
                    }

                    @Override
                    public void onFailure(Call<Long> call, Throwable t) {
                        Toast.makeText(ThongKeActivity.this,"API error",Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void getDataChart() {
        barChart.setVisibility(View.GONE);
        pieChart.setVisibility(View.VISIBLE);

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

    private void askPermisson(){
        ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQUEST_CDOE);

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
        txtRevenue=findViewById(R.id.txtRevenue);
        btnPdf=findViewById(R.id.btnPdf);
        barChart=findViewById(R.id.barChart);
    }
}