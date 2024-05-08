package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.admin.AdminLoginActivity;
import com.huuduc.giuaky.data.Promotion;
import com.huuduc.giuaky.retrofit.PromotionApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PromotionAdapter extends RecyclerView.Adapter<PromotionAdapter.PromotionViewHolder> {

    private Context context;

    private List<Promotion> promotionList;

    public PromotionAdapter(Context context, List<Promotion> promotionList) {
        this.context = context;
        this.promotionList = promotionList;
    }

    public void setData(List<Promotion> list) {
        this.promotionList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PromotionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_promotion, parent, false);
        return new PromotionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PromotionViewHolder holder, int position) {

        Promotion promotion = promotionList.get(position);

        if (promotion == null) {
            return;
        }

        holder.txtPromotion.setText("Giảm " + promotion.getDiscount() + "K");
        if (promotion.isStatus()) {
            holder.btnHide.setVisibility(View.VISIBLE);
        } else {
            holder.btnActive.setVisibility(View.VISIBLE);
        }

        holder.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Promotion hidePromotion = promotionList.get(adapterPosition);
                    if (hidePromotion != null) {

                        updateStatus(AdminLoginActivity.token, hidePromotion.getId(), false);
                        promotionList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, promotionList.size());
                    }
                }

            }
        });

        holder.btnActive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Promotion hidePromotion = promotionList.get(adapterPosition);
                    if (hidePromotion != null) {

                        updateStatus(AdminLoginActivity.token,hidePromotion.getId(),true);
                        promotionList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, promotionList.size());
                    }
                }

            }
        });


    }

    private void updateStatus(String token, Long id, boolean status) {
        RetrofitService retrofitService = new RetrofitService();
        PromotionApi promotionApi = retrofitService.getRetrofit().create(PromotionApi.class);
        promotionApi.updateStatus(token, id, status)
                .enqueue(new Callback<Promotion>() {
                    @Override
                    public void onResponse(Call<Promotion> call, Response<Promotion> response) {
                        if(response.isSuccessful()){
                            if(response.body().isStatus()){
                                Toast.makeText(context,  "Active success", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(context,  "Hide success", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Promotion> call, Throwable t) {
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (promotionList != null) {
            return promotionList.size();
        }
        return 0;
    }

    public class PromotionViewHolder extends RecyclerView.ViewHolder {

        private TextView txtPromotion;
        private Button btnActive, btnHide;


        public PromotionViewHolder(@NonNull View itemView) {
            super(itemView);

            txtPromotion = itemView.findViewById(R.id.txtPromotion);
            btnActive = itemView.findViewById(R.id.btnActive);
            btnHide = itemView.findViewById(R.id.btnHide);

        }
    }
}
