package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.ShowDetailActivity;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.retrofit.CartApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private Context context;
    private List<Cart> cartList = new ArrayList<>();

    public CartAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<Cart> cartList) {
        this.cartList = cartList;
    }

    // Khai báo một interface để giao tiếp với Activity
    public interface OnQuantityChangedListener {
        void onQuantityChanged();
    }

    // Thêm một trường listener vào Adapter
    private OnQuantityChangedListener listener;

    // Setter cho listener
    public void setOnQuantityChangedListener(OnQuantityChangedListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cart, parent, false);
        return new CartAdapter.CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        Cart cart = cartList.get(position);
        if (cart == null) {
            return;
        }

//        holder.imgProductCart.setImageResource(cart.getResoureId(context));
        Glide.with(context)
                .load(cart.getImgSP())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProductCart);
        holder.txtFoodNameCart.setText(cart.getNameSP());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtPriceCart.setText(decimalFormat.format(cart.getPriceSP()) + "đ");
        holder.txt_quantity.setText(cart.getSoluongSP() + "");
        int sl = Integer.parseInt(holder.txt_quantity.getText().toString());


        //Xử lý nút thêm
        holder.imv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Cart clickedCart = cartList.get(adapterPosition);
                    if (clickedCart != null) {

                        int slMoi = clickedCart.getSoluongSP() + 1;

                        updateCart(clickedCart.getId(),slMoi,clickedCart,holder);
                    }
                }

            }
        });


        //Xử lý nút giảm
        holder.imv_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Cart clickedCart = cartList.get(adapterPosition);
                    if (clickedCart != null) {

                        int slMoi = clickedCart.getSoluongSP() - 1;
                        //int slHienTai = clickedCart.getSoluongSP();
                        updateCart(clickedCart.getId(),slMoi,clickedCart,holder);

                        if(slMoi<1){
                            cartList.remove(adapterPosition);
                            notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                            notifyItemRangeChanged(adapterPosition, cartList.size());

                        }

                    }
                }

            }
        });
    }

    private void updateCart(long cartDetailsId, int soLuong,Cart clickCart,CartViewHolder holder) {

        RetrofitService retrofitService = new RetrofitService();
        CartApi cartApi = retrofitService.getRetrofit().create(CartApi.class);
        cartApi.updateCartDetails(cartDetailsId, soLuong)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {

                        if(response.isSuccessful()) {
                            Cart getCart = response.body();
                            clickCart.setSoluongSP(soLuong); // Cập nhật số lượng mới cho sản phẩm
                            holder.txt_quantity.setText(String.valueOf(soLuong)); // Cập nhật số lượng hiển thị trên giao diện
                            clickCart.setPriceSP(getCart.getPriceSP());
                            holder.txtPriceCart.setText(String.valueOf(getCart.getPriceSP()));
                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
//                            notifyDataSetChanged();
                            if (listener != null) {
                                listener.onQuantityChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, "Update error", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        if (cartList != null) {
            return cartList.size();
        }
        return 0;
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProductCart;
        private TextView txtFoodNameCart;
        private TextView txtPriceCart, txt_quantity;
        private ImageView imv_minus, imv_add;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductCart = itemView.findViewById(R.id.imgFoodCart);
            txtFoodNameCart = itemView.findViewById(R.id.txtFoodNameCart);
            txtPriceCart = itemView.findViewById(R.id.txtPriceCart);
            imv_minus = itemView.findViewById(R.id.imv_minus);
            imv_add = itemView.findViewById(R.id.imv_add);
            txt_quantity = itemView.findViewById(R.id.txt_quantity);
        }
    }
}
