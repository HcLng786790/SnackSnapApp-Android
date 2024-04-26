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
import com.huuduc.giuaky.data.product.Product;
import com.huuduc.giuaky.interfaces.ItemClickListener;
import com.huuduc.giuaky.repo.DSProduct;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    private Context context;
    private List<Product> mListProduct;
    private ItemClickListener itemClickListener;

    public ProductAdapter(Context context, List<Product> dsProduct,ItemClickListener itemClickListener) {
        this.context = context;
        this.mListProduct=dsProduct;
        this.itemClickListener=itemClickListener;
    }

    public void setData(List<Product> list){
        this.mListProduct=list;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

//        if(PRODUCT_IS_NOT_FAVORITE == viewType) {
//            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
//            return new ProductViewHolder(view);
//        }
////        } else if (PRODUCT_IS_FAVORITE==viewType) {
////            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_favorite,parent,false);
////            return new ProductFavoriteViewHoler(view);
////        }
//        return null;

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {

        final Product product = mListProduct.get(position);

        if(product==null) {
            return;
        }
//        holder.imgProduct.setImageResource(product.getResoureId(context));
        Glide.with(context)
                .load(product.getImg())
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_background)
                .into(holder.imgProduct);

        holder.txtFoodName.setText(product.getName());
        holder.txtPrice.setText(String.format("%s đ", product.getPriceFormat()));
        holder.layoutDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.onClickItem(product);
            }
        });

    }

//
////    @Override
////    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
////        Product product = mListProduct.get(position);
////        if(product==null){
////            return;
////        }
//
//
//
////        if(PRODUCT_IS_NOT_FAVORITE==holder.getItemViewType()){
////            ProductViewHolder productViewHolder = (ProductViewHolder) holder;
////
////            productViewHolder.imgProduct.setImageResource(product.getImg());
////            productViewHolder.txtFoodName.setText(product.getName());
////            productViewHolder.txtPrice.setText(product.getPriceFormat()+" đ");
////        } else if (PRODUCT_IS_FAVORITE==holder.getItemViewType()) {
////
////            ProductFavoriteViewHoler productFavoriteViewHoler = (ProductFavoriteViewHoler) holder;
////
////            productFavoriteViewHoler.imgProductFavo.setImageResource(product.getImg());
////            productFavoriteViewHoler.txtFoodNameFavo.setText(product.getName());
////            productFavoriteViewHoler.txtPriceFavo.setText(product.getPriceFormat()+" đ");
////        }
//
//
//    }
//
//
    @Override
    public int getItemCount() {
        if(mListProduct!=null){
            return mListProduct.size();
        }
        return 0;
    }
//
////    @Override
////    public int getItemViewType(int position) {
////        Product product = mListProduct.get(position);
////        if(product.isFavorite()){
////            return PRODUCT_IS_FAVORITE;
////        }else {
////            return PRODUCT_IS_NOT_FAVORITE;
////        }
////    }
//
    public class ProductViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgProduct;
        private TextView txtFoodName;
        private TextView txtPrice;
        private CardView layoutDetail;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);

            imgProduct=itemView.findViewById(R.id.imgFood);
            txtFoodName=itemView.findViewById(R.id.txtFoodName);
            txtPrice=itemView.findViewById(R.id.txtPrice);
            layoutDetail=itemView.findViewById(R.id.layoutDetail);

        }

}


//
////    public class ProductFavoriteViewHoler extends RecyclerView.ViewHolder{
////
////
////        private ImageView imgProductFavo;
////        private TextView txtFoodNameFavo;
////        private TextView txtPriceFavo;
////
////
////        public ProductFavoriteViewHoler(@NonNull View itemView) {
////            super(itemView);
////
////            imgProductFavo=itemView.findViewById(R.id.imgFoodFavo);
////            txtFoodNameFavo=itemView.findViewById(R.id.txtFoodNameFavo);
////            txtPriceFavo=itemView.findViewById(R.id.txtPriceFavo);
////        }
////    }

//    public List<Product> getListByType(int type,List<Product> list){
//
//        List<Product> newList = new ArrayList<>();
//
//        for (Product product : list){
//            if(product.getType()==type){
//                newList.add(product);
//            }
//        }
//        return newList;
//
//
//    }
//
//
//    public List<Product> getListData(){
//
//        List<Product> list = new ArrayList<>();
//
//        list.add(new Product("Salad",30,Product.FOOD, R.drawable.food,true));
//        list.add(new Product("Nước ngọt",30,Product.DRINK,R.drawable.food,true));
//        list.add(new Product("Canh cá",30,Product.SAUCE,R.drawable.food,true));
//        list.add(new Product("Salad cá ngừ",30,Product.FOOD,R.drawable.food,false));
//        list.add(new Product("Bánh tráng",30,Product.SNACK,R.drawable.food,true));
//        list.add(new Product("Sacue",30,Product.SAUCE,R.drawable.food,false));
//        list.add(new Product("Mì trộn",30,Product.FOOD,R.drawable.food,true));
//        list.add(new Product("Tokboki",30,Product.SAUCE,R.drawable.food,false));
//        list.add(new Product("Bánh mì",30,Product.SNACK,R.drawable.food,true));
//        return list;
//    }
}
