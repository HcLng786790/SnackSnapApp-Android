package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.fragmentStatus.ConfirmFragment;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.interfaces.OrdersClickListener;
import com.huuduc.giuaky.retrofit.OrdersApi;
import com.huuduc.giuaky.retrofit.RetrofitService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

    private Context context;

    private List<Orders> mOrdersList;

    private OrdersClickListener ordersClickListener;

    public OrdersAdapter(Context context,List<Orders> dsOrders,OrdersClickListener ordersClickListener){
        this.context=context;
        this.mOrdersList=dsOrders;
        this.ordersClickListener=ordersClickListener;
    }

    public void setData(List<Orders> list){
        this.mOrdersList=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {

        Orders orders= mOrdersList.get(position);

        if (orders==null){
            return;
        }

        Cart cart = mOrdersList.get(position).getCartList().get(0);

//        holder.imgFood.setImageResource(cart.getResoureId(context));

        Glide.with(context)
                .load(cart.getImgSP())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgFood);

        holder.txtFoodName.setText(cart.getNameSP());
        holder.txtPrice.setText(cart.getPriceFormat()+"Đ");
        holder.txtQuantity.setText("x"+cart.getSoluongSP());
        holder.txtDate.setText(orders.getDateFormat());
        holder.txtStatus.setText(orders.getStatus());
        holder.txtTotalOrders.setText(orders.getTotalPrice()+"Đ");
        if (orders.getCartList().size()>1){
            holder.txtShowmore.setVisibility(View.VISIBLE);
        }

        holder.layoutOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersClickListener.onClickOrders(orders);
            }
        });

        if(mOrdersList.get(0).getStatus().equals("Chờ xác nhận")){
            holder.btnHuy.setVisibility(View.VISIBLE);
        }else{
            holder.btnHuy.setVisibility(View.GONE);
        }

        if (mOrdersList.get(0).getStatus().equals("Đã hủy")){
            holder.btnAgain.setVisibility(View.VISIBLE);
        }else{
            holder.btnAgain.setVisibility(View.GONE);
        }

        holder.btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        cancelOrder(cancelOrders.getId());
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }

            }
        });

        holder.btnAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        againOrders(cancelOrders.getId());
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }
            }
        });

    }

    private void againOrders(Long ordersId) {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.againOrders(ordersId)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        Toast.makeText(context, "Mua lại thành công, Vui lòng kiểm tra đơn hàng", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        Toast.makeText(context,  "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void cancelOrder(long ordersId) {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.cancelOrders(ordersId)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        Toast.makeText(context, "Cancel success", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        Toast.makeText(context,  "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {

        if(mOrdersList!=null){
            return mOrdersList.size();
        }
        return 0;
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgFood;
        private TextView txtFoodName,txtQuantity,txtPrice,txtShowmore,txtDate,txtStatus,txtTotalOrders;
        private Button btnHuy,btnAgain;

        private CardView layoutOrders;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood=itemView.findViewById(R.id.imgFood);
            txtFoodName=itemView.findViewById(R.id.txtFoodName);
            txtQuantity=itemView.findViewById(R.id.txtQuantity);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            txtShowmore=itemView.findViewById(R.id.txtShowmore);
            txtDate=itemView.findViewById(R.id.txtDate);
            txtStatus=itemView.findViewById(R.id.txtStatus);
            btnHuy=itemView.findViewById(R.id.btnHuy);
            layoutOrders=itemView.findViewById(R.id.layoutOrders);
            txtTotalOrders=itemView.findViewById(R.id.txtTotalOrders);
            btnAgain=itemView.findViewById(R.id.btnAgain);
        }
    }
}
