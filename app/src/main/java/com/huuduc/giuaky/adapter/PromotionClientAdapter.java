package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.Promotion;

import java.util.List;

public class PromotionClientAdapter extends ArrayAdapter {
    private Context context;

    private int resource;
    private List<Promotion> promotionList;

    private Promotion selectedPromotion;

    public void setSelectedPromotion(Promotion selectedPromotion) {
        this.selectedPromotion = selectedPromotion;
    }


    public PromotionClientAdapter(@NonNull Context context, int resource , List<Promotion> promotionList) {
        super(context, resource,promotionList);
        this.context=context;
        this.resource=resource;
        this.promotionList=promotionList;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(resource,null);


        TextView txtPromotion = convertView.findViewById(R.id.txtPromotion);
        ImageView imvChosePromotion = convertView.findViewById(R.id.imvChosePromotion);

        Promotion promotion = promotionList.get(position);

        txtPromotion.setText("Giảm "+promotion.getDiscount()+"K");

        if(selectedPromotion!=null){
            if(promotion.getId().equals(selectedPromotion.getId())){
                imvChosePromotion.setVisibility(View.VISIBLE);
            }else{
                imvChosePromotion.setVisibility(View.INVISIBLE);
            }
        }

        return convertView;
    }
}
