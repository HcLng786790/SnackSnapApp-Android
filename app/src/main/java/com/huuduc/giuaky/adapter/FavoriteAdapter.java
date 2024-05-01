package com.huuduc.giuaky.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.huuduc.giuaky.R;
import com.huuduc.giuaky.activity.CartAvtivity;
import com.huuduc.giuaky.activity.MainActivity;
import com.huuduc.giuaky.activity.ShowDetailActivity;
import com.huuduc.giuaky.data.DTO.CartDetailsDTO;
import com.huuduc.giuaky.data.cart.Cart;
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.repo.DSProduct;
import com.huuduc.giuaky.retrofit.CartApi;
import com.huuduc.giuaky.retrofit.RetrofitService;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>implements Filterable {

    private Context context;
    private List<Product> productsFavorite;

    private List<Product> mListProductOld;


    private ItemClickListener itemClickListener;

//    public FavoriteAdapter(Context context, DSProduct dsProduct,ItemClickListener itemClickListener){
//        this.context=context;
//        this.productsFavorite=dsProduct.getProducts();
//        this.itemClickListener=itemClickListener;
//    }

    public FavoriteAdapter(Context context, List<Product> products, ItemClickListener itemClickListener) {
        this.context = context;
        this.productsFavorite = products;
        this.itemClickListener = itemClickListener;
        this.mListProductOld=products;
    }

    public void setData(List<Product> list) {
        this.productsFavorite = list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite, parent, false);
        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteViewHolder holder, int position) {

        Product product = productsFavorite.get(position);

        if (product == null) {
            return;
        }

//        holder.imgProductFavo.setImageResource(product.getResoureId(context));
        Glide.with(context)
                .load(product.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProductFavo);
        holder.txtFoodNameFavo.setText(product.getName());
        holder.txtPriceFavo.setText(product.getPriceFormat() + " đ");
        holder.layoutDetailFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(product);
            }
        });

        holder.btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addToCart(product);
            }
        });
    }

    private void addToCart(Product product) {
        CartDetailsDTO cartDetailsDTO = new CartDetailsDTO(product.getId(), 1);

        RetrofitService retrofitService = new RetrofitService();
        CartApi cartApi = retrofitService.getRetrofit().create(CartApi.class);
        cartApi.addToCart(MainActivity.user.getCartId(), cartDetailsDTO)
                .enqueue(new Callback<Cart>() {
                    @Override
                    public void onResponse(Call<Cart> call, Response<Cart> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(context, "Success, Please check cart", Toast.LENGTH_SHORT).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<Cart> call, Throwable t) {
                        Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public int getItemCount() {
        if (productsFavorite != null) {
            return productsFavorite.size();
        }
        return 0;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String seacrh = constraint.toString();
                if(seacrh.isEmpty()){
                    productsFavorite=mListProductOld;
                }else{
                    List<Product> list = new ArrayList<>();
                    for (Product product:mListProductOld){
                        if(product.getName().toLowerCase().contains(seacrh.toLowerCase())){
                            list.add(product);
                        }
                    }

                    productsFavorite=list;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=productsFavorite;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                productsFavorite = (List<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    public class FavoriteViewHolder extends RecyclerView.ViewHolder {

        private ImageView imgProductFavo;
        private TextView txtFoodNameFavo;
        private TextView txtPriceFavo;

        private CardView layoutDetailFavorite;
        private ImageButton btnFavorite;

        public FavoriteViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProductFavo = itemView.findViewById(R.id.imgFoodFavo);
            txtFoodNameFavo = itemView.findViewById(R.id.txtFoodNameFavo);
            txtPriceFavo = itemView.findViewById(R.id.txtPriceFavo);
            layoutDetailFavorite = itemView.findViewById(R.id.layoutDetailFavorite);
            btnFavorite = itemView.findViewById(R.id.btnFavorite);
        }
    }

//    public List<Product> getListFavorite(List<Product> list){
//
//        List<Product> newList= new ArrayList<>();
//
//        for (Product product:list){
//            if (product.isFavorite()){
//                newList.add(product);
//            }
//        }
//
//        return newList;
//    }

//    public List<Product> getListData(){
//
//        List<Product> list = new ArrayList<>();
//
//        list.add(new Product("Salad",30,Product.FOOD, R.drawable.food,true));
//        list.add(new Product("Salad cá ngừ",30,Product.DRINK,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.SAUCE,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.FOOD,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.SNACK,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.SAUCE,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.FOOD,R.drawable.food,false));
//        list.add(new Product("Salad cá ngừ",30,Product.SAUCE,R.drawable.food,true));
//        list.add(new Product("Salad cá ngừ",30,Product.SNACK,R.drawable.food,true));
//        return list;
//    }
}
