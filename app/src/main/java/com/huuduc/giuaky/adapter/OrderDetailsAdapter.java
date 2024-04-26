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

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.data.cart.Cart;

import java.util.List;

public class OrderDetailsAdapter extends ArrayAdapter {

    private Context context;

    private int resource;
    private List<Cart> cartList;


    public OrderDetailsAdapter(@NonNull Context context, int resource , List<Cart> cartList) {
        super(context, resource,cartList);
        this.context=context;
        this.resource=resource;
        this.cartList=cartList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(context).inflate(resource,null);

        ImageView imgFood = convertView.findViewById(R.id.imgFood);
        TextView txtFoodName = convertView.findViewById(R.id.txtFoodName);
        TextView txtPrice =convertView.findViewById(R.id.txtPrice);
        TextView txtQuantity = convertView.findViewById(R.id.txtQuantity);

        Cart cart = cartList.get(position);
//        imgFood.setImageResource(cart.getResoureId(context));

        Glide.with(context)
                .load(cart.getImgSP())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(imgFood);
        txtFoodName.setText(cart.getNameSP());
        txtPrice.setText(cart.getPriceFormat()+"Đ");
        txtQuantity.setText("x"+cart.getSoluongSP());

        return convertView;

    }
}
