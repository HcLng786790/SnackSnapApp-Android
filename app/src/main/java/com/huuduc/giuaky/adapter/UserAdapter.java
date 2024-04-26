package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.data.User.User;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.orders.Orders;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.interfaces.OrdersClickListener;
import com.huuduc.giuaky.interfaces.UserClickListener;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder>{

    private Context context;

    private List<User> mUserList;

    private UserClickListener userClickListener;

    public UserAdapter(Context context,List<User> dsUsers,UserClickListener userClickListener){
        this.context=context;
        this.mUserList=dsUsers;
        this.userClickListener=userClickListener;
    }

    public void setData(List<User> list){
        this.mUserList=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_user,parent,false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        User user= mUserList.get(position);

        if (user==null){
            return;
        }

        holder.txtUserName.setText(user.getName());

        Glide.with(context)
                .load(user.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgUser);

        holder.item_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userClickListener.onClickOrders(user);
            }
        });

    }

    @Override
    public int getItemCount() {
        if(mUserList!=null){
            return mUserList.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgUser;
        private TextView txtUserName;
        private CardView item_user;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);

            imgUser=itemView.findViewById(R.id.imgUser);
            txtUserName=itemView.findViewById(R.id.txtUserName);
            item_user=itemView.findViewById(R.id.item_user);

        }
    }
}
