package com.huuduc.giuaky.adapter;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.activity.admin.AdminLoginActivity;
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

public class AdminOrdersAdapter extends RecyclerView.Adapter<AdminOrdersAdapter.AdminOrdersViewHolder> {

    private Context context;

    private List<Orders> mOrdersList;

    private OrdersClickListener ordersClickListener;

    private ProgressDialog progressDialog;


    public AdminOrdersAdapter(Context context, List<Orders> dsOrders, OrdersClickListener ordersClickListener) {
        this.context = context;
        this.mOrdersList = dsOrders;
        this.ordersClickListener = ordersClickListener;
    }

    public void setData(List<Orders> list) {
        this.mOrdersList = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AdminOrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_orders, parent, false);
        return new AdminOrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminOrdersViewHolder holder, int position) {

        Orders orders = mOrdersList.get(position);

        if (orders == null) {
            return;
        }


        Cart cart = mOrdersList.get(position).getCartList().get(0);

        holder.imgFood.setImageResource(cart.getResoureId(context));

        Glide.with(context)
                .load(cart.getImgSP())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgFood);

        holder.txtFoodName.setText(cart.getNameSP());
        holder.txtPrice.setText(cart.getPriceFormat() + "Đ");
        holder.txtQuantity.setText("x" + cart.getSoluongSP());
        holder.txtDate.setText(orders.getDateFormat());
        holder.txtStatus.setText(orders.getStatus());
        holder.txtTotalOrders.setText(orders.getTotalPrice() + "Đ");

        if(mOrdersList.get(0).getStatus().equals("Chờ xác nhận")){
            holder.btnConfirm.setVisibility(View.VISIBLE);
            holder.btnCancle.setVisibility(View.VISIBLE);
        }
        if (mOrdersList.get(0).getStatus().equals("Chờ thực hiện ")){
            holder.btnGiao.setVisibility(View.VISIBLE);
        }

        if (mOrdersList.get(0).getStatus().equals("Chờ vận chuyển")){
            holder.btnSuccess.setVisibility(View.VISIBLE);
        }

        if (orders.getCartList().size() > 1) {
            holder.txtShowmore.setVisibility(View.VISIBLE);
        }


        holder.layoutOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ordersClickListener.onClickOrders(orders);
            }
        });


        holder.btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        update(cancelOrders.getId(),Orders.COOKING);
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }

            }
        });

        holder.btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        cancleByAdmin(cancelOrders.getId());
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }
            }
        });

        holder.btnGiao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        update(cancelOrders.getId(),Orders.DELIVERING);
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }

            }
        });

        holder.btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int adapterPosition = holder.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    Orders cancelOrders = mOrdersList.get(adapterPosition);
                    if (cancelOrders != null) {

                        update(cancelOrders.getId(),Orders.DELIVERED);
                        mOrdersList.remove(adapterPosition);
                        notifyItemRemoved(adapterPosition); // Thông báo adapter về việc phần tử đã bị xóa
                        notifyItemRangeChanged(adapterPosition, mOrdersList.size());
                    }
                }

            }
        });
    }

    private void cancleByAdmin(Long id) {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.cancelOrdersByAdmin(AdminLoginActivity.token,id)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Cancle success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        Toast.makeText(context,  "Error", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void update(long ordersId,long statusId) {
        RetrofitService retrofitService = new RetrofitService();
        OrdersApi ordersApi = retrofitService.getRetrofit().create(OrdersApi.class);
        ordersApi.update(ordersId,statusId)
                .enqueue(new Callback<Orders>() {
                    @Override
                    public void onResponse(Call<Orders> call, Response<Orders> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(context, "Update success", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<Orders> call, Throwable t) {
                        Toast.makeText(context,  "Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (mOrdersList != null) {
            return mOrdersList.size();
        }
        return 0;
    }

    public class AdminOrdersViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgFood;
        private TextView txtFoodName, txtQuantity, txtPrice, txtShowmore, txtDate, txtStatus, txtTotalOrders;
        private Button btnCancle, btnAgain, btnConfirm,btnGiao,btnSuccess;

        private CardView layoutOrders;

        public AdminOrdersViewHolder(@NonNull View itemView) {
            super(itemView);

            imgFood = itemView.findViewById(R.id.imgFood);
            txtFoodName = itemView.findViewById(R.id.txtFoodName);
            txtQuantity = itemView.findViewById(R.id.txtQuantity);
            txtPrice = itemView.findViewById(R.id.txtPrice);
            txtShowmore = itemView.findViewById(R.id.txtShowmore);
            txtDate = itemView.findViewById(R.id.txtDate);
            txtStatus = itemView.findViewById(R.id.txtStatus);
            btnCancle = itemView.findViewById(R.id.btnCancle);
            layoutOrders = itemView.findViewById(R.id.layoutOrders);
            txtTotalOrders = itemView.findViewById(R.id.txtTotalOrders);
            btnAgain = itemView.findViewById(R.id.btnAgain);
            btnConfirm = itemView.findViewById(R.id.btnConfirm);
            btnGiao=itemView.findViewById(R.id.btnGiao);
            btnSuccess=itemView.findViewById(R.id.btnSuccess);

        }
    }
}
