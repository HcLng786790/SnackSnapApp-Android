package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.ProductApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminProductAdapter extends RecyclerView.Adapter<AdminProductAdapter.AdminProductViewHolder> {

    private Context context;
    private List<Product> mListProduct;
    private ItemClickListener itemClickListener;

    public AdminProductAdapter(Context context, List<Product> dsProduct, ItemClickListener itemClickListener) {
        this.context = context;
        this.mListProduct = dsProduct;
        this.itemClickListener = itemClickListener;
    }

    public void setData(List<Product> list) {
        this.mListProduct = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.admin_product_items, parent, false);
        return new AdminProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminProductViewHolder holder, int position) {

        final Product product = mListProduct.get(position);

        if (product == null) {
            return;
        }
        holder.txtType.setText(product.getType());
        holder.txtFoodName.setText(product.getName());
        holder.txtPrice.setText(String.format("%s đ", product.getPriceFormat()));
        Glide.with(context)
                .load(product.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);
        holder.layoutProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(product);
            }
        });


        holder.iconTrash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Product deletedProduct = mListProduct.get(adapterPosition);
                    if (deletedProduct != null) {

                        delete(deletedProduct.getId());
                        mListProduct.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mListProduct.size());
                    }
                }

            }
        });
    }

    private void delete(long id) {
        RetrofitService retrofitService = new RetrofitService();
        ProductApi productApi = retrofitService.getRetrofit().create(ProductApi.class);
        productApi.delete(id)
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Delete success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        Toast.makeText(context, "Delete error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (mListProduct != null) {
            return mListProduct.size();
        }
        return 0;
    }

    public class AdminProductViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProduct, iconTrash;
        private TextView txtFoodName, txtType;
        private TextView txtPrice;
        private CardView layoutProduct;

        public AdminProductViewHolder(@NonNull View itemView) {
            super(itemView);

            txtType = itemView.findViewById(R.id.txtType);
            imgProduct = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtPrice = itemView.findViewById(R.id.txtFoodPrice);
            iconTrash = itemView.findViewById(R.id.iconTrash);
            layoutProduct = itemView.findViewById(R.id.layoutProduct);
        }
    }
}
